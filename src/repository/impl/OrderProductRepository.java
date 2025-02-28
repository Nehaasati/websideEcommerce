package repository.impl;
import model.OrderProduct;
import repository.IOrderProductRepository;
import util.SqliteConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class OrderProductRepository implements IOrderProductRepository {
    private static final Logger logger = Logger.getLogger(OrderProductRepository.class.getName());

    @Override
    public boolean addOrderProduct(int orderId, int productId, int quantity, double unitPrice) {
        String sql = "INSERT INTO orders_products (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            pstmt.setInt(2, productId);
            pstmt.setInt(3, quantity);
            pstmt.setDouble(4, unitPrice);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe("Error adding product to order: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<OrderProduct> getOrderProducts(int orderId) {
        String sql = "SELECT * FROM orders_products WHERE order_id = ?";
        List<OrderProduct> orderProducts = new ArrayList<>();
        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                orderProducts.add(new OrderProduct(
                        rs.getInt("order_product_id"),
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("unit_price")
                ));
            }
        } catch (SQLException e) {
            logger.severe("Error retrieving order products: " + e.getMessage());
        }
        return orderProducts;
    }

    @Override
    public boolean removeOrderProduct(int orderProductId) {
        String sql = "DELETE FROM orders_products WHERE order_product_id = ?";
        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderProductId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.severe("Error removing product from order: " + e.getMessage());
            return false;
        }
    }
}


