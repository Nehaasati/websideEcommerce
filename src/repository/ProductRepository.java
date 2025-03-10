package repository;

import model.Product;

import util.SqliteConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductRepository {
    private static final String SELECT_BASE =
                "SELECT  p.product_id, p.manufacturer_id, p.name, p.description, " +
                        "p.price, p.stock_quantity, GROUP_CONCAT(pc.category_id) AS categories " +
                        "FROM products p " +
                        "LEFT JOIN products_categories pc ON p.product_id = pc.product_id ";

    public Optional<Product> findProductById(int productId) throws SQLException {
        String sql = SELECT_BASE + "WHERE p.product_id = ? GROUP BY p.product_id";
         try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setInt(1, productId);
             ResultSet rs = stmt.executeQuery();
             return rs.next() ? Optional.of(mapProduct(rs)) : Optional.empty();
         }
    }

    public List<Product> findAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (Connection conn = SqliteConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_BASE + "GROUP BY p.product_id"))
             {

                while (rs.next()) {
                    products.add(mapProduct(rs));
                }
             }
            return products;
    }

    public List<Product> findProductByName(String name) throws SQLException {
        String sql = SELECT_BASE +
                "WHERE LOWER(p.name) LIKE LOWER(?) " +
                "GROUP BY p.product_id";  // Add proper grouping

        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");  // Add wildcards
            return executeQueryAndMapResults(stmt);
        }
    }

    public List<Product> findProductByCategory(int categoryId) throws SQLException {
        String sql = SELECT_BASE +
                " WHERE pc.category_id = ? " +
                " GROUP BY p.product_id";
        try(Connection conn = SqliteConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            return executeQueryAndMapResults(stmt);
        }
    }

    public List<Product> findProductsByPriceRange(double min, double max) throws SQLException {
        String sql = SELECT_BASE + "WHERE p.price BETWEEN ? AND ? GROUP BY p.product_id";
        try(Connection conn = SqliteConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, min);
            stmt.setDouble(2, max);
            return executeQueryAndMapResults(stmt);

        }
    }

    public void updateStock(int productId, int quantityChange) throws SQLException {
        String sql = "UPDATE products SET stock_quantity = stock_quantity + ? WHERE product_id = ?";
        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantityChange); // This is the value to add/subtract
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        }
    }

    private List<Product> executeQueryAndMapResults(PreparedStatement stmt) throws SQLException {
        List<Product> products = new ArrayList<>();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        }
        return products;
    }

        private Product mapProduct(ResultSet rs) throws SQLException {
            Product product = new Product(
                    rs.getInt("product_id"),
                    rs.getInt("manufacturer_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("stock_quantity")
            );

            String categories = rs.getString("categories");
            if (categories != null) {
                Arrays.stream(categories.split(","))
                        .map(Integer::parseInt)
                        .forEach(product::addCategoryId);
            }

            return product;
        }
    }




