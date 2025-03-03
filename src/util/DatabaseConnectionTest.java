package util;

import java.sql.Connection;

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

            }
        }
    }
