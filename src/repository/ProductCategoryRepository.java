/*package repository;

import model.ProductCategory;
import util.SqliteConnection;
import java.sql.*;

public class ProductCategoryRepository {
    private final Connection connection = SqliteConnection.getConnection();

    //to add a product-category relationship
    public boolean addProductCategory(ProductCategory pc) {
        String sql = "INSERT INTO products_categories (product_id, category_id) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pc.getProductId());
            stmt.setInt(2, pc.getCategoryId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete all categories for a product
    public boolean deleteCategoriesByProductId(int productId) {
        String sql = "DELETE FROM products_categories WHERE product_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}*/

