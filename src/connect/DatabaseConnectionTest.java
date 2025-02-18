package connect;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
            try (Connection conn = SqliteConnection.getConnection()) {
                if (conn != null) {
                    System.out.println("✅ Database Connection Successful!");
                } else {
                    System.out.println("❌ Failed to connect to Database.");
                }
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
                //e.printStackTrace();
            }
        }
    }
