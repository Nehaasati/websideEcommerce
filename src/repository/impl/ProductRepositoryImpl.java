package repository.impl;

import model.Manufacturer;
import model.Product;
import model.ProductCategory;
import repository.ProductRepository;

import java.util.Map;
import java.util.HashMap;
import util.SqliteConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    private final Connection connection = SqliteConnection.getConnection();

    @Override
    public Optional<Product> searchProductById(int productId) {
        String sql =  "SELECT p.*, m.name AS manufacturer_name, pc.category_id, c.name AS category_name " +
                "FROM products p " +
                "JOIN manufacturers m ON p.manufacturer_id = m.manufacturer_id " +
                "LEFT JOIN products_categories pc ON p.product_id = pc.product_id " +
                "LEFT JOIN categories c ON pc.category_id = c.category_id " +
                "WHERE p.product_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            Product product = null;
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
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, m.name AS manufacturer_name, pc.category_id, c.name AS category_name " +
                "FROM products p " +
                "JOIN manufacturers m ON p.manufacturer_id = m.manufacturer_id " +
                "LEFT JOIN products_categories pc ON p.product_id = pc.product_id " +
                "LEFT JOIN categories c ON pc.category_id = c.category_id";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            Map<Integer, Product> productMap = new HashMap<>();
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                Product product = productMap.get(productId);

                if (product == null) {
                    product = mapRowToProduct(rs);
                    productMap.put(productId, product);
                    products.add(product);
                }

                int categoryId = rs.getInt("category_id");
                if (categoryId != 0) {
                    ProductCategory pc = new ProductCategory(productId, categoryId);
                    product.getCategories().add(pc);
                }
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
        manufacturer.setName(rs.getString("manufacturer_name"));
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
}




