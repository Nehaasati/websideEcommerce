package repository;
import model.Product;
import util.SqliteConnectionManger;

import java.sql.PreparedStatement;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ProductRepository {
    private static final Logger LOGGER = Logger.getLogger(ProductRepository.class.getName());
    private final Map<Integer, Product> products = new HashMap<>();

    public Optional<Product> getProductById(int productId) {
        String query = "SELECT * FROM products WHERE product_id = ?";
        try (Connection conn = SqliteConnectionManger.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new Product(
                        rs.getInt("product_id"),
                        rs.getInt("manufacturer_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity")
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Database Error in getProductById");
            LOGGER.log(Level.SEVERE, "Database Error", e);
        }
        return Optional.empty();
    }

    public boolean updateStock(int productId, int newStock) {
        String query = "UPDATE products SET stock_quantity = ? WHERE product_id = ?";
        try (Connection conn = SqliteConnectionManger.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, newStock);
            stmt.setInt(2, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Database Error in updateStock");
            LOGGER.log(Level.SEVERE, "Database Error", e);
        }
        return false;
    }

    public int getStock(int productId) throws SQLException {
            String query = "SELECT stock_quantity FROM products WHERE product_id = ?";
            try (Connection conn = SqliteConnectionManger.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, productId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("stock_quantity");
                }
            }
            return 0;
        }
    public void UpdateStock(int productId, int quantity) throws SQLException {
        String query = "UPDATE products SET stock_quantity = ? WHERE product_id = ?";
        try (Connection conn = SqliteConnectionManger.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        }
    }

    public void updateProductPrice(int productId, double newPrice) throws SQLException {
        String query = "UPDATE products SET price = ? WHERE product_id = ?";
        try (Connection conn = SqliteConnectionManger.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, newPrice);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        }
}}


