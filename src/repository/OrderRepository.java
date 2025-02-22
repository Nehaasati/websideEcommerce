package repository;
import util.SqliteConnection;
import model.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    public int createOrder(Order order) {
        int generatedId = -1;
        String sql = "INSERT INTO orders (customer_id, order_date) VALUES (?, ?)";
        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, order.getCustomerId());
            pstmt.setDate(2, order.getOrderDate());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                System.err.println("Creating order failed, no rows affected."); // no row affected print error
                return -1;
            }
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                } else {
                    System.err.println("Creating order failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while creating order: " + e.getMessage());
        }
        return generatedId;
    }

    public boolean deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            int affected = pstmt.executeUpdate();
            return affected > 0;                        // no row affected error on delete
        } catch (SQLException e) {
            System.err.println("Error while deleting order: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves a joined view of the order with its products.
     * Returns a list of strings summarizing each row.
     */
    public List<String> getOrderWithProducts(int orderId) {
        List<String> results = new ArrayList<>();
        String sql = "SELECT o.order_id, o.customer_id, o.order_date, " +
                "op.order_product_id, op.product_id, op.quantity, op.price " +
                "FROM orders o JOIN order_product op ON o.order_id = op.order_id " +
                "WHERE o.order_id = ?";
        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add("OrderID: " + rs.getInt("order_id") +
                            ", CustomerID: " + rs.getInt("customer_id") +
                            ", OrderDate: " + rs.getDate("order_date") +
                            ", OrderProductID: " + rs.getInt("order_product_id") +
                            ", ProductID: " + rs.getInt("product_id") +
                            ", Quantity: " + rs.getInt("quantity") +
                            ", Price: " + rs.getDouble("price"));
                }
            }                                //lambda method reference (System.out::println) is used to output each row.
        } catch (SQLException e) {
            System.err.println("Error while retrieving order details: " + e.getMessage());
        }
        return results;
    }

}