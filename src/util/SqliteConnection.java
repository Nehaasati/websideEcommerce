package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnection {
    private static final String URL = "jdbc:sqlite:./webbutiken.db";
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL);
            } catch (SQLException e) {
                System.err.println("Connection failed" + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }
}

