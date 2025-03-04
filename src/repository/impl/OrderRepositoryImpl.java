package repository.impl;

import repository.OrderRepository;
import util.SqliteConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderRepositoryImpl implements OrderRepository {
    private static final Logger logger = Logger.getLogger(OrderRepository.class.getName());

    @Override
    public int placeOrder(int customerId) {
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
            logger.info("Order placed successfully. Order ID: " + orderId);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error placing order: " + e.getMessage(), e);
        }
        return orderId;
    }

    @Override
    public List<String> getAllOrders() {
        List<String> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (Connection conn = SqliteConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                orders.add("Order ID: " + rs.getInt("order_id") + ", Customer ID: " + rs.getInt("customer_id") +
                        ", Date: " + rs.getString("order_date"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching orders: " + e.getMessage(), e);
        }
        return orders;
    }

    @Override
    public boolean cancelOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error cancelling order: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean updateOrder(int orderId, int customerId) {
        String sql = "UPDATE orders SET customer_id = ? WHERE order_id = ?";
        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            stmt.setInt(2, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating order: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<String> getOrderHistory(int customerId) {
        List<String> orders = new ArrayList<>();
        String sql = "SELECT order_id, order_date FROM orders WHERE customer_id = ?";

        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add("Order ID: " + rs.getInt("order_id") + ", Date: " + rs.getTimestamp("order_date"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching order history: " + e.getMessage(), e);
        }
        return orders;
    }
}

