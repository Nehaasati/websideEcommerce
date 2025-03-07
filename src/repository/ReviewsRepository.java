package repository;

import model.Customer;
import model.Product;
import model.Reviews;
import util.SqliteConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ReviewsRepository {
    private static final String SELECT_BASE =
            "SELECT r.review_id, r.rating, r.comment, " +
                    "p.product_id, p.name AS product_name, " +
                    "c.customer_id, c.name AS customer_name " +
                    "FROM reviews r " +
                    "JOIN products p ON r.product_id = p.product_id " +
                    "JOIN customers c ON r.customer_id = c.customer_id ";

    public List<Reviews> getReviewsByProduct(int product_id) throws SQLException {
        List<Reviews> reviews = new ArrayList<>();
        String sql = SELECT_BASE + " WHERE r.product_id = ?";

        try(Connection conn = SqliteConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, product_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
            reviews.add(mapReviews(rs));
            }
        }
        return reviews;
    }

    private Reviews mapReviews(ResultSet rs) throws SQLException {
        Product product = new Product(
                rs.getInt("product_id"),
                rs.getString("product_name")
        );

        Customer customer = new Customer(
                rs.getInt("customer_id"),
                rs.getString("customer_name")
        );

        return new Reviews(
                rs.getInt("review_id"),
                product,
                customer,
                rs.getInt("rating"),
                rs.getString("comment")
        );
    }
}
