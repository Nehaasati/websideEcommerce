package repository.impl;

import model.Manufacturer;
import model.Product;
import model.ProductCategory;
import repository.ProductRepository;

import java.sql.*;
import util.SqliteConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    private final Connection connection = SqliteConnection.getConnection();


    @Override
    public Optional<Product> searchProductById(int productId) {
        String sql = "SELECT p.*, m.name AS manufacturer_name " +
                "FROM products p " +
                "JOIN manufacturers m ON p.manufacturer_id = m.manufacturer_id " +
                "WHERE p.product_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


           /* Product product = null;
            while (rs.next()) {
                if (product == null) {
                    product = mapRowToProduct(rs);
                }
                int categoryId = rs.getInt("category_id");
                if (categoryId != 0) {
                    ProductCategory pc = new ProductCategory(
                            productId,
                            categoryId
                    );
                    product.getCategories().add(pc);
                }
            }
            return Optional.ofNullable(product);
            } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }*/

    @Override
    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> searchProductByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        String sql = "SELECT * FROM products WHERE name LIKE ?";

        List<Product> products = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%"); // Partial match
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> searchProductByCategory(String categoryName) {
        String sql = "SELECT p.* " +
                "FROM products p " +
                "JOIN products_categories pc ON p.product_id = pc.product_id " +
                "JOIN categories c ON pc.category_id = c.category_id " +
                "WHERE c.name = ?";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoryName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> searchProductByPriceRange(double minPrice, double maxPrice) {
        String sql = "SELECT * FROM products WHERE price BETWEEN ? AND ?";
        List<Product> products = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, minPrice);
            stmt.setDouble(2, maxPrice);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                products.add(mapRowToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product save(Product product) {
        String sql = "INSERT INTO products (name, description, price, stock_quantity, manufacturer_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setProductStatement(stmt, product);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setProductId(generatedKeys.getInt(1));
                }
            }
            saveProductCategories(product); //save categories
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, price = ?, " +
                "stock_quantity = ?, manufacturer_id = ? WHERE product_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            setProductStatement(stmt, product);
            stmt.setInt(6, product.getProductId());
            stmt.executeUpdate();

            updateProductCategories(product);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int productId) {
        String sql = "DELETE FROM products WHERE product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveProductCategories(Product product) throws SQLException {
        if (product.getCategories() == null || product.getCategories().isEmpty()) {
            return;
        }

        String sql = "INSERT INTO products_categories (product_id, category_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (ProductCategory pc : product.getCategories()) {
                stmt.setInt(1, product.getProductId());
                stmt.setInt(2, pc.getCategoryId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private void updateProductCategories(Product product) throws SQLException {
        deleteProductCategories(product.getProductId());
        saveProductCategories(product);
    }

    private void deleteProductCategories(int productId) throws SQLException {
        String sql = "DELETE FROM products_categories WHERE product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.executeUpdate();
        }
    }

    // Helper Methods
    private Product mapRowToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getDouble("price"));
        product.setStockQuantity(rs.getInt("stock_quantity"));

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerId(rs.getInt("manufacturer_id"));
        manufacturer.setName(rs.getString("name"));
        product.setManufacturers(manufacturer);

        return product;
    }

    private void setProductStatement(PreparedStatement stmt, Product product) throws SQLException {
        stmt.setString(1, product.getName());
        stmt.setString(2, product.getDescription());
        stmt.setDouble(3, product.getPrice());
        stmt.setInt(4, product.getStockQuantity());
        stmt.setInt(5, product.getManufacturers().getManufacturerId());
    }

    private void validateProduct(Product product) {
        if (product == null || product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
    }

}




