package repository;

import util.SqliteConnectionManger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Category;


public class CategoryRepository {
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories WHERE category_id";
        //"ORDER BY name ASC";
        //"SELECT category_id, name FROM categories";

        try (Connection conn = SqliteConnectionManger.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("category_id"),
                        rs.getString("name")
                );
                categories.add(category);
            }
        } catch (SQLException e) {
            System.out.println("Error showing categories: " + e.getMessage());
            e.printStackTrace();
        }
        return categories;
    }
}
