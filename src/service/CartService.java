package service;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import repository.CartRepository;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CartItem;
import repository.CartRepository;
import repository.ProductRepository;
import java.util.List;
import model.Product;

public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;

    private static final Logger LOGGER = Logger.getLogger(CartService.class.getName());
    public CartService(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }


    // Add product to cart
    public String addProductToCart(int customerId, int productId, int quantity) {
        if (quantity <= 0) return "Error: Quantity must be greater than zero.";

        if (!productService.checkStockAvailability(productId, quantity)) return "Error: Not enough stock available.";

        // Deduct stock and add to cart
        if (cartRepository.addProductToCart(customerId, productId, quantity)) {
            productService.reduceStock(productId, quantity);
            return "✅ Product added to cart successfully!";
        }
        return "❌ Error: Failed to add product to cart.";
    }

    // Remove product from cart
    public String removeProductFromCart(int customerId, int productId) {
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
    }

    // Get all cart items
    public List<CartItem> getCartItems(int customerId) {
        return cartRepository.getCartItems(customerId);
    }

    // Clear entire cart
    public String clearCart(int customerId) {
        if (cartRepository.clearCart(customerId)) {
            return "🛒 Cart cleared successfully!";
        }
        return "❌ Error: Cart is already empty.";
    }
    public double getTotalCartPrice(int customerId) throws SQLException {
        List<CartItem> cartItems = cartRepository.getCartItems(customerId);
        double totalPrice = 0.0;



        System.out.println("Number of Items in cart for CustomerID " + customerId + ": " + cartItems.size());

        for (CartItem item : cartItems) {
            double price = productService.getProductPrice(item.getProductId());
            String Name = productService.getProductNameById(item.getProductId());
            int quantity = item.getQuantity();

            // Print debug info
            System.out.println("Product ID: " + item.getProductId() + ", Price: " + price + ", Quantity: " + quantity+",Name: "+Name);

            totalPrice += (price * quantity);
        }

        LOGGER.log(Level.INFO, "Total Cart Price for Customer ID {0}: KR{1}", new Object[]{customerId, totalPrice});
        return totalPrice;
    }
    public String updateProductQuantity(int customerId, int productId, int newQuantity) {
        if (newQuantity < 0) return "Error: Quantity cannot be negative.";

        // Check if product exists in cart
        boolean productExists = cartRepository.getCartItems(customerId)
                .stream().anyMatch(item -> item.getProductId() == productId);

        if (!productExists) {
            return "Error: Product not found in cart.";
        }

        // Check if stock is sufficient
        if (!productService.checkStockAvailability(productId, newQuantity)) {
            return "Error: Not enough stock available.";
        }

        // Update quantity
        if (cartRepository.updateProductQuantity(customerId, productId, newQuantity)) {
            return "✅ Product quantity updated successfully!";
        }

        return "❌ Error: Failed to update product quantity.";
    }
    // Update product (replace product in the cart)
    public String updateProductInCart(int customerId, int oldProductId, int newProductId, int newQuantity) {
        if (newQuantity <= 0) return "Error: Quantity must be greater than zero.";

        if (!cartRepository.getCartItems(customerId).stream().anyMatch(item -> item.getProductId() == oldProductId)) {
            return "Error: Old product not found in cart.";
        }

        if (!productService.checkStockAvailability(newProductId, newQuantity)) return "Error: Not enough stock available for new product.";

        if (cartRepository.updateProductInCart(customerId, oldProductId, newProductId, newQuantity)) {
            return "✅ Product updated successfully!";
        }

        return "❌ Error: Failed to update product.";
    }

}
