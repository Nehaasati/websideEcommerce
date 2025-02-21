package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnection {
    private static final String URL = "jdbc:sqlite:C:/Users/nehaa/IdeaProjects/ecomerce_webshop1/webbutiken.db";
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.sqlite.JDBC"); // Ensure the driver is loaded
                connection = DriverManager.getConnection(URL);
            } catch (ClassNotFoundException e) {
                System.err.println("JDBC Driver not found");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Connection failed");
                e.printStackTrace();
            }
        }
        return connection;
    }
}

