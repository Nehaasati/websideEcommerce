package repository;
import util.SqliteConnection;
import model.OrderProduct;
import java.sql.*;

public class OrderProductRepository {
    public boolean addOrderProduct(OrderProduct op) {
        String sql = "INSERT INTO orders_products (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, op.getOrderId());
            pstmt.setInt(2, op.getProductId());
            pstmt.setInt(3, op.getQuantity());
            pstmt.setDouble(4, op.getunit_Price());
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.err.println("Error while adding order product: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteOrderProductsByOrderId(int orderId) {
        String sql = "DELETE FROM orders_products WHERE order_id = ?";
        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.err.println("Error while deleting order products: " + e.getMessage());
            return false;
        }
    }

}
