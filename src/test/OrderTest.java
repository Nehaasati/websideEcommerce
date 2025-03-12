package test;

import util.SqliteConnectionManger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderTest {
    public static void main(String[] args) {
        System.out.println("\n🔍 **Checking Order Data in Database** 🔍");
        fetchOrders();
    }

    public static void fetchOrders() {
        String sql = "SELECT order_id, customer_id, order_date FROM orders";

        try (Connection conn = SqliteConnectionManger.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n+------------+------------+---------------------+");
            System.out.println("| Order ID   | Customer ID | Order Date         |");
            System.out.println("+------------+------------+---------------------+");

            while (rs.next()) {
                System.out.printf("| %-10d | %-10d | %-20s |\n",
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        rs.getString("order_date"));
            }

            System.out.println("+------------+------------+---------------------+");

        } catch (SQLException e) {
            System.out.println("❌ Error fetching order data: " + e.getMessage());
        }
    }
}

