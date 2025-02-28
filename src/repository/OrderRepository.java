package repository;


import model.Order;
import util.SqliteConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OrderRepository {

    private final Connection connection = SqliteConnection.getConnection();



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

    public void save(Order order) {
        String sql = "INSERT INTO orders (customer_id, order_date) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getCustomerId());
            stmt.setDate(2, order.getOrder_Date());
            stmt.executeUpdate();

            // Set the auto-generated orderId
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    order.setOrderId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a joined view of the order with its products.
     * Returns a list of strings summarizing each row.
     */
    public List<String> getOrderWithProducts(int orderId) {
        List<String> results = new ArrayList<>();

       /* String sql =  "SELECT o.order_id, o.customer_id, o.order_date, " +
                "op.order_product_id, op.product_id, op.quantity, op.unit_price " +
                "FROM orders o JOIN orders_products op ON o.order_id = op.order_id " +*/

        String sql = "SELECT o.order_id, o.customer_id, o.order_date, " +
                "op.order_product_id, op.product_id, op.quantity, op.unit_price " +
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

                            ", Price: " + rs.getDouble("unit_price") +
                            ", unit_price: " + rs.getDouble("unit_price"));

                }
            }                                //lambda method reference (System.out::println) is used to output each row.
        } catch (SQLException e) {
            System.err.println("Error while retrieving order details: " + e.getMessage());
        }
        return results;
    }
}