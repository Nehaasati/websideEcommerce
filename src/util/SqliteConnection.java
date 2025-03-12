package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnection {
    private static final String URL = "jdbc:sqlite:./webbutiken.db";
    private static Connection connection = null;

    private SqliteConnection() { //Make constructor private to make class singleton
    }

    public static Connection getConnection() throws SQLException {
        try {
                if (connection == null || connection.isClosed()) {
                    //Class.forName("org.sqlite.JDBC"); //Ensure the SQLite JDBC driver is loaded (use appropriate driver)
                    connection = DriverManager.getConnection(URL);
                }
                return connection;
            } catch (SQLException e) {
                System.err.println("Connection failed" + e.getMessage());
                throw e; // Re-throw the exception for handling in the calling method
            }
        }
    }

        /*if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL);
            } catch (SQLException e) {
                System.err.println("Connection failed" + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }*/


