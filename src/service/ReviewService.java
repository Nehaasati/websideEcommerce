package service;

import model.Reviews;
import repository.ReviewsRepository;

import java.sql.SQLException;
import java.util.List;

public class ReviewService {
    private final ReviewsRepository reviewsRepository;

    public ReviewService(ReviewsRepository reviewsRepository) {
        this.reviewsRepository = reviewsRepository;
    }




    // Get all reviews for a specific reviews
    public List<Reviews> getProductReviews(int productId) throws SQLException {
        return reviewsRepository.getReviewsByProduct(productId);
    }

    public List<Reviews> getReviewsByCustomer(int customerId) throws SQLException {
        return reviewsRepository.getReviewsByCustomer(customerId);
    }


    public void validateReview(Reviews review) {
        // Ensure rating is within valid range
        if(review.getRating() < 1 || review.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1-5");
        }

        // Ensure relationships exist
        if(review.getProduct() == null || review.getCustomer() == null) {
            throw new IllegalArgumentException("Review must be associated with a product and customer");
        }
    }
}
