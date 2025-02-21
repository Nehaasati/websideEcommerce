package Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import model.Product;
import util.SqliteConnection;

public class ProductRepository {
    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();

        try (Connection conn = SqliteConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int stock_quantity = rs.getInt("Stock_Quantity");

                products.add(new Product(productId, name, description, price, stock_quantity));
                System.out.println(products.toString());
            }
        } catch (SQLException e) {
            System.out.println("❌ Database error while checking product existence:" +e.getMessage());
        }

        return products;
    }
    // ✅ Get products within a price range
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        String sql = "SELECT * FROM products WHERE price BETWEEN ? AND ?";
        List<Product> products = new ArrayList<>();

        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, minPrice);
            pstmt.setDouble(2, maxPrice);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity")
                        //rs.getInt("manufacturer_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
        }
        return products;
    }

    // ✅ Get products by Manufacturer ID
    public List<Product> getProductsByManufacturer(int manufacturerId) {
        String sql = "SELECT * FROM products WHERE manufacturer_id = ?";
        List<Product> products = new ArrayList<>();

        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, manufacturerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity")
                        //rs.getInt("manufacturer_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching products or Database error while checking product existence: : " + e.getMessage());
        }
        return products;
    }
    public boolean productExists(int productId) {
        String sql = "SELECT 1 FROM products WHERE product_id = ?";

        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("❌ Database error while checking product existence: " + e.getMessage());
            return false;
        }
    }
}
