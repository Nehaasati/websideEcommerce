package service;

import java.sql.SQLException;
import repository.CartRepository;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CartItem;
import repository.ProductRepository;
import repository.impl.OrderProductRepository;
import repository.impl.OrderRepository;

import java.util.List;


public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    private static final Logger LOGGER = Logger.getLogger(CartService.class.getName());
    private double discountedTotal = 0.0;

    public CartService(CartRepository cartRepository, ProductService productService,
                       OrderRepository orderRepository, OrderProductRepository orderProductRepository) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
    }

    // Modified addProductToCart method in CartService
    public String addProductToCart(int customerId, int productId, int quantity) {
        if (quantity <= 0) return "Error: Quantity must be greater than zero.";

        // Check if stock is available but DON'T reduce it yet
        if (!productService.checkStockAvailability(productId, quantity))
            return "Error: Not enough stock available.";

        if (cartRepository.addProductToCart(customerId, productId, quantity)) {
            // Don't reduce stock here anymore
            return "✅ Product added to cart successfully!";
        }
        return "❌ Error: Failed to add product to cart.";
    }
   /* public String addProductToCart(int customerId, int productId, int quantity) {
        if (quantity <= 0) return "Error: Quantity must be greater than zero.";

        if (!productService.checkStockAvailability(productId, quantity))
            return "Error: Not enough stock available.";

        if (cartRepository.addProductToCart(customerId, productId, quantity)) {
            productService.reduceStock(productId, quantity);
            return "✅ Product added to cart successfully!";
        }
        return "❌ Error: Failed to add product to cart.";
    }*/

    // Modified removeProductFromCart method in CartService
    public String removeProductFromCart(int customerId, int productId) {
        // No need to track quantity for stock restoration
        if (cartRepository.removeProductFromCart(customerId, productId)) {
            // Don't restore stock here anymore
            return "✅ Product removed from cart successfully!";
        }
        return "❌ Error: Product not in cart.";
    }

    /*public String removeProductFromCart(int customerId, int productId) {
        List<CartItem> cartItems = cartRepository.getCartItems(customerId);
        int quantity = cartItems.stream()
                .filter(item -> item.getProductId() == productId)
                .mapToInt(CartItem::getQuantity)
                .sum();

        if (cartRepository.removeProductFromCart(customerId, productId)) {
            productService.addStock(productId, quantity); // Restore stock
            return "✅ Product removed from cart successfully!";
        }
        return "❌ Error: Product not in cart.";
    }*/

    public List<CartItem> getCartItems(int customerId) {
        return cartRepository.getCartItems(customerId);
    }

    public String clearCart(int customerId) {
        if (cartRepository.clearCart(customerId)) {
            return "🛒 Cart cleared successfully!";
        }
        return "❌ Error: Cart is already empty.";
    }

    public double getTotalCartPrice(int customerId) throws SQLException {
        List<CartItem> cartItems = cartRepository.getCartItems(customerId);
        double totalPrice = 0.0;

        for (CartItem item : cartItems) {
            double price = productService.getProductPrice(item.getProductId());
            totalPrice += (price * item.getQuantity());
        }

        LOGGER.log(Level.INFO, "Total Cart Price for Customer ID {0}: KR{1}", new Object[]{customerId, totalPrice});
        return totalPrice;
    }

    // ✅ Apply Discount Before Order Placement
    public double applyDiscount(double totalPrice, double discountPercentage) {
        if (discountPercentage < 0 || discountPercentage > 100) {
            LOGGER.warning("⚠ Invalid discount percentage: " + discountPercentage);
            return totalPrice;
        }

        double discountAmount = (discountPercentage / 100) * totalPrice;
        double finalPrice = totalPrice - discountAmount;

        LOGGER.log(Level.INFO, "✅ Discount Applied: {0}% | New Price: KR{1}", new Object[]{discountPercentage, finalPrice});
        return finalPrice;
    }

    public String updateProductQuantity(int customerId, int productId, int newQuantity) {
        if (newQuantity < 0) return "Error: Quantity cannot be negative.";

        boolean productExists = cartRepository.getCartItems(customerId)
                .stream().anyMatch(item -> item.getProductId() == productId);

        if (!productExists) {
            return "Error: Product not found in cart.";
        }

        if (!productService.checkStockAvailability(productId, newQuantity)) {
            return "Error: Not enough stock available.";
        }

        if (cartRepository.updateProductQuantity(customerId, productId, newQuantity)) {
            return "✅ Product quantity updated successfully!";
        }

        return "❌ Error: Failed to update product quantity.";
    }

    // 🔹 Apply Discount

    public double applyDiscount(int customerId, double discountPercentage) throws SQLException {
        double totalPrice = getTotalCartPrice(customerId);
        double discountAmount = (discountPercentage / 100) * totalPrice;
        double finalPrice = totalPrice - discountAmount;

        LOGGER.log(Level.INFO, "Discount applied. New price: KR{0}", new Object[]{finalPrice});
        return finalPrice;
    }

    // Place order after applying discount
    public int placeOrder(int customerId, double discountPercentage) throws SQLException {
        double totalPrice = getTotalCartPrice(customerId);
        discountedTotal = applyDiscount(totalPrice, discountPercentage); // Store the discounted total

        List<CartItem> cartItems = getCartItems(customerId);
        if (cartItems.isEmpty()) {
            LOGGER.warning("❌ No items found in cart.");
            return -1;
        }

        // Check stock availability for all items at order time
        for (CartItem item : cartItems) {
           /* if (!productService.checkStockAvailability(item.getProductId(), item.getQuantity())) {
                LOGGER.warning("❌ Not enough stock for Product ID: " + item.getProductId());
                return -1;
            }*/
            int availableStock = productService.getStockStatus(item.getProductId());
            if (availableStock < item.getQuantity()) {
                LOGGER.warning("❌ Not enough stock for Product ID: " + item.getProductId());
                return -1;
            }
        }

        int orderId = orderRepository.createOrder(customerId);
        if (orderId <= 0) {
            LOGGER.warning("❌ Order creation failed.");
            return -1;
        }

        boolean allItemsAdded = true;
        for (CartItem item : cartItems) {
            boolean added = orderProductRepository.addOrderProduct(orderId, item.getProductId(), item.getQuantity(), item.getUnit_price());
            if (!added) {
                LOGGER.warning("❌ Failed to add Product ID " + item.getProductId() + " to order.");
                allItemsAdded = false;
            } else {
                // NOW reduce stock when order is finalized
                productService.reduceStock(item.getProductId(), item.getQuantity());
            }
        }

        if (allItemsAdded) {
            cartRepository.clearCart(customerId);
            LOGGER.info("✅ Order placed successfully! Order ID: " + orderId);
        } else {
            LOGGER.warning("⚠ Some items were not added to the order.");
        }

        return orderId;
    }

    public boolean processPayment(int customerId, String paymentMethod, double discountPercentage) throws SQLException {
        // Use the stored discounted total instead of recalculating
        LOGGER.log(Level.INFO, "Processing payment via {0} for amount KR{1}", new Object[]{paymentMethod, discountedTotal});

        boolean paymentSuccess = cartRepository.processPayment(customerId, discountedTotal, paymentMethod);

        return paymentSuccess;
    }
}