package service.impl;

import model.CartItem;
import repository.IOrderRepository;
import repository.ProductRepository;
import repository.impl.OrderProductRepository;
import repository.impl.OrderRepository;
import service.CartService;
import service.IOrderService;
import service.ProductService;
import util.SqliteConnectionManger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class OrderService implements IOrderService {
   // private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


   // private final CartService cartService;
   // private final ProductService productService;
   // private static final Logger logger = Logger.getLogger(OrderService.class.getName());

   /* public OrderService(OrderRepository orderRepository, OrderProductRepository orderProductRepository, ProductService productService, CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.productService = productService;
        this.cartService = cartService;
    }*/
/*
    public int placeOrder(int customerId) {
        // Step 1: Check if the cart has items and get total price
        double totalAmount;
        try {
            totalAmount = cartService.getTotalCartPrice(customerId);
            if (totalAmount <= 0) {
                logger.warning("❌ Cannot place order: Cart is empty for Customer ID: " + customerId);
                return -1;
            }
        } catch (SQLException e) {
            logger.severe("❌ Database error while retrieving cart total: " + e.getMessage());
            return -1;
        }

        // Step 2: Retrieve cart items
        List<CartItem> cartItems = cartService.getCartItems(customerId);
        if (cartItems.isEmpty()) {
            logger.warning("❌ No items in the cart to process order.");
            return -1;
        }

        // Step 3: Validate stock availability before proceeding
        for (CartItem item : cartItems) {
            int availableStock = productService.getStockStatus(item.getProductId());
            if (availableStock < item.getQuantity()) {
                logger.warning("❌ Not enough stock for Product ID: " + item.getProductId());
                return -1; // Prevent checkout if any item is out of stock
            }
        }

        // Step 4: Begin transaction to create order and move cart items
        Connection conn = null;
        int orderId = -1;
        try {
            conn = SqliteConnectionManger.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Step 5: Create order in database
            orderId = orderRepository.createOrder(customerId);
            if (orderId <= 0) {
                logger.warning("❌ Failed to create order.");
                return -1;
            }

            // Step 6: Add cart items to order & deduct stock
            boolean allItemsAdded = true;
            for (CartItem item : cartItems) {
                boolean added = orderProductRepository.addOrderProduct(orderId, item.getProductId(), item.getQuantity(), item.getunit_price());
                if (!added) {
                    logger.warning("❌ Failed to add Product ID " + item.getProductId() + " to order.");
                    allItemsAdded = false;
                    break; // Stop if any item fails
                } else {
                    // Deduct stock only if item is added successfully
                    productService.reduceStock(item.getProductId(), item.getQuantity());
                }
            }

            // Step 7: Commit transaction if everything is successful
            if (allItemsAdded) {
                cartService.clearCart(customerId); // Clear cart only if order is successful
                conn.commit(); // Commit transaction
                logger.info("✅ Order placed successfully! Order ID: " + orderId);
            } else {
                conn.rollback(); // Rollback transaction if an error occurred
                logger.warning("⚠ Transaction rolled back due to failure in adding items.");
                return -1;
            }
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); // Rollback on failure
            } catch (SQLException rollbackEx) {
                logger.severe("❌ Error rolling back transaction: " + rollbackEx.getMessage());
            }
            logger.severe("❌ Error placing order: " + e.getMessage());
            return -1;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                logger.severe("❌ Error resetting auto-commit: " + e.getMessage());
            }
        }

        return orderId;
    }





    public double applyDiscount(int orderId, double discountPercentage) {
        return orderRepository.applyDiscount(orderId, discountPercentage);
    }

    public boolean processPayment(int orderId, String paymentMethod, double amount) {
        return orderRepository.processPayment(orderId, paymentMethod, amount);
    }
*/
    @Override
    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    @Override
    public boolean cancelOrder(int orderId) {
        return orderRepository.cancelOrder(orderId);
    }

    @Override
    public boolean updateOrder(int orderId, int customerId) {
        return orderRepository.updateOrder(orderId, customerId);
    }

    @Override
    public List<String> getOrderHistory(int customerId) {
        return orderRepository.getOrderHistory(customerId);
    }
}
