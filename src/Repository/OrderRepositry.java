package Repository;
import java.sql.*;
import util.SqliteConnection;

public class OrderRepositry {
    public boolean addOrder(int product_id, int customer_id) {
        String sql = "insert into orders(product_id,Customer_id)values(?,?)";
        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, product_id);
            pstmt.setInt(2, customer_id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("❌ Database error while adding order: " + e.getMessage());//database layer with validation
            return false;
        }
    }

    public boolean deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE order_id = ?";

        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Database error while deleting order: " + e.getMessage());
            return false;
        }
    }
    public boolean updateOrder(int orderid,int product_id,int quantity){
        String sql ="UPDATE orders SET product_id=?,quantity=? where order_id=?";
        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, product_id);
            stmt.setInt(2,quantity);
            stmt.setInt(3,orderid);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Database error while update order: " + e.getMessage());
            return false;
        }
    }
    public boolean viewOrder(int orderId) {
        String sql = "select * FROM orders WHERE order_id = ?";

        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Database error while view order: " + e.getMessage());
            return false;
        }
    }
    public boolean orderExists(int orderId) {
        String sql = "SELECT 1 FROM orders WHERE order_id = ?";

        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("❌ Database error while checking order existence: " + e.getMessage());
            return false;
        }
    }

}
