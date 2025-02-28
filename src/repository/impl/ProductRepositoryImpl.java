package repository.impl;

import model.Category;
import model.Manufacturer;
import model.Product;
import repository.ProductRepository;
import util.SqliteConnection;

import java.sql.*;
import java.util.*;

public class ProductRepositoryImpl implements ProductRepository {
    private final Connection connection = SqliteConnection.getConnection();

    @Override
    public List<Product> getAllProducts() {
        String sql = "SELECT product_id, name, description, price FROM products";
        List<Product> products = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (!rs.isBeforeFirst()) {
                throw new SQLException("No products found in the database.");
            }

            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        } catch (SQLException e) {
            System.err.println("Database error while fetching products: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
        return products;
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public void update(Product product) {

    }

    @Override
    public void delete(int productId) {

    }

    @Override
    public Optional<Product> searchProductById(int productId) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Invalid product ID.");
        }

        String sql ="SELECT p.product_id, p.name, p.description, p.price,"+
                "m.manufacturer_id, m.name AS manufacturer_name," +
                "FROM products p," +
                "INNER JOIN manufacturers m ON p.manufacturer_id = m.manufacturer_id,"+
                "WHERE p.product_id = ?";


        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToProduct(rs));
            } else {
                throw new SQLException("Product not found with ID: " + productId);
            }
        } catch (SQLException e) {
            System.err.println("Error searching for product by ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Product> searchProductByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }

        String sql = "SELECT p.product_id, p.name, p.description, p.price, " +
                "m.manufacturer_id, m.name AS manufacturer_name " +
                "FROM products p " +
                "LEFT JOIN manufacturers m ON p.manufacturer_id = m.manufacturer_id " +
                "WHERE p.name LIKE ?";

        List<Product> products = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                throw new SQLException("No products found with name: " + name);
            }

            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching for products: " + e.getMessage());
        }
        return products;
    }

    @Override
    public List<Product> searchProductByCategory(String categoryName) {
        return List.of();
    }

    @Override
    public List<Product> searchProductByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Price range cannot be negative.");
        }
        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("Minimum price cannot be greater than maximum price.");
        }

        String sql = "SELECT * FROM products WHERE price BETWEEN ? AND ?";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, minPrice);
            stmt.setDouble(2, maxPrice);
            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                throw new SQLException("No products found in the given price range.");
            }

            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching products by price range: " + e.getMessage());
        }
        return products;
    }

    private Product mapRowToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getDouble("price"));

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerId(rs.getInt("manufacturer_id"));
        manufacturer.setName(rs.getString("manufacturer_name"));
        product.setManufacturers(manufacturer);

        return product;
    }
}
