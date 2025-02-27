package repository;

import model.OrderProduct;
import util.SqliteConnection;
import java.sql.*;

public class OrderProductRepository {
     private final Connection connection = SqliteConnection.getConnection();

    public boolean addOrderProduct(OrderProduct op) {
<<<<<<< HEAD
        String sql = "INSERT INTO orders_products (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, op.getOrderId());
            pstmt.setInt(2, op.getProductId());
            pstmt.setInt(3, op.getQuantity());
            pstmt.setDouble(4, op.getunit_Price());
=======
        String sql = "INSERT INTO orders_products (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        try (
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             pstmt.setInt(1, op.getOrderId());
             pstmt.setInt(2, op.getProductId());
             pstmt.setInt(3, op.getQuantity());
             pstmt.setDouble(4, op.getUnitPrice());

>>>>>>> origin/master
            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        op.setOrderProductId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /*return affected > 0;
        } catch (SQLException e) {
            System.err.println("Error while adding order product: " + e.getMessage());
            return false;
        }
    }*/

    public boolean deleteOrderProductsByOrderId(int orderId) {
        String sql = "DELETE FROM orders_products WHERE order_id = ?";
<<<<<<< HEAD
        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
=======
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
>>>>>>> origin/master
            pstmt.setInt(1, orderId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

           /* int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.err.println("Error while deleting order products: " + e.getMessage());
            return false;
        }*/
    }

}
