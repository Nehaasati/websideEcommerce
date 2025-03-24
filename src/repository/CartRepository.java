package repository;

import model.CartItem;
import util.SqliteConnection;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.logging.Level;



public class CartRepository {
    private static final Logger LOGGER = Logger.getLogger(CartRepository.class.getName());

    private final Map<Integer, List<CartItem>> cartData = new HashMap<>();

    // Add product to cart
    public boolean addProductToCart(int customerId, int productId, int quantity) {
        cartData.putIfAbsent(customerId, new ArrayList<>());
        cartData.get(customerId).add(new CartItem(customerId,productId, quantity));
        return true;
    }

    // Remove product from cart
    public boolean removeProductFromCart(int customerId, int productId) {
        if (!cartData.containsKey(customerId)) return false;
        return cartData.get(customerId).removeIf(item -> item.getProductId() == productId);
    }

    // Get all cart items for a customer
    public List<CartItem> getCartItems(int customerId) {
        return cartData.getOrDefault(customerId,Collections.emptyList() /*new ArrayList<>()*/);
    }

    // Clear entire cart for a customer
    public boolean clearCart(int customerId) {
        if (!cartData.containsKey(customerId)) return false;
        cartData.remove(customerId);
        return true;
    }

    // Update product quantity
    public boolean updateProductQuantity(int customerId, int productId, int newQuantity) {
        if (!cartData.containsKey(customerId)) return false;

        List<CartItem> items = cartData.get(customerId);
        for (CartItem item : items) {
            if (item.getProductId() == productId) {
                if (newQuantity <= 0) {
                    items.remove(item); // Remove product if quantity is 0
                } else {
                    item.setQuantity(newQuantity); // Update quantity
                }
                return true;
            }
        }
        return false;
    }

    public int createOrder(int customerId) {
        String sql = "INSERT INTO orders (customer_id) VALUES (?)";
        int orderId = -1;

        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            conn.setAutoCommit(false);
            pstmt.setInt(1, customerId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    orderId = rs.getInt(1);
                }
            }

            conn.commit();
            LOGGER.info("Order placed successfully. Order ID: " + orderId);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error placing order: " + e.getMessage(), e);
        }
        return orderId;
    }


    // Simulate payment processing
    public boolean processPayment(int customerId, double amount, String paymentMethod) {
        LOGGER.info("Processing payment for Customer ID: " + customerId + " via " + paymentMethod + " for amount: KR" + amount);
        // Simulate a successful payment
        return true;
    }
}


