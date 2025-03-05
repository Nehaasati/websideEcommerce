package test;

//import util.SqliteConnection;
import util.SqliteConnectionManger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerTest {
    public static void main(String[] args) {
        System.out.println("\n🔍 **Checking Customer Data in Database** 🔍");
        fetchCustomers();
    }

    public static void fetchCustomers() {
        String sql = "SELECT email, phone, address, password FROM customers";

        try (Connection conn = SqliteConnectionManger.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n+--------------------------------+-------------+--------------------------+----------------------+");
            System.out.println("|           Email               |    Phone    |          Address         |       Password       |");
            System.out.println("+--------------------------------+-------------+--------------------------+----------------------+");

            while (rs.next()) {
                System.out.printf("| %-30s | %-11s | %-24s | %-20s |\n",
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("password"));
            }

            System.out.println("+--------------------------------+-------------+--------------------------+----------------------+");

        } catch (SQLException e) {
            System.out.println("❌ Error fetching customer data: " + e.getMessage());
        }
    }
}

