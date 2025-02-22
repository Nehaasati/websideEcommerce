package repository.impl;

import model.Manufacturer;
import repository.ProductRepository;
import model.Product;

import util.SqliteConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    private final Connection connection = SqliteConnection.getConnection();



    @Override
    public Optional<Product> searchProductById(int productId) {
        String sql = "SELECT p.*, m.name AS manufacturer_name FROM products p " +
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

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, m.name AS manufacturer_name FROM products p " +
                "JOIN manufacturers m ON p.manufacturer_id = m.manufacturer_id";

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

    }
}




