package controller;

import model.Customer;
import model.Product;
import model.Reviews;
import service.CustomerService;
import service.ProductService;
import service.ReviewService;

import java.util.List;
import java.util.Scanner;

public class ReviewsController {
    private final ReviewService reviewService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final Scanner scanner;

    public ReviewsController(ReviewService reviewService, ProductService productService, CustomerService customerService) {
        this.reviewService = reviewService;
        this.productService = productService;
        this.customerService = customerService;
        this.scanner = new Scanner(System.in);
    }

    public void displayProductReviews() {
        try {
            System.out.print("Enter Product ID: ");
            int productId = Integer.parseInt(scanner.nextLine());

            Product product = productService.getProductDetails(productId);
            if (product == null) {
                System.out.println("⚠️ Error: Product not found");
                return;
            }

            System.out.println("\nReviews for: " + product.getName());
            List<Reviews> reviews = reviewService.getProductReviews(productId);

            if (reviews.isEmpty()) {
                System.out.println("No reviews available for this product.");
            } else {
                for (Reviews review : reviews) {
                    System.out.println(review.getFormattedReviews());
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid input. Please enter a numeric product ID.");
        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
        }
    }

    public void displayCustomerReviews() {
        try{
            System.out.println("Enter Customer ID: ");
            int customerId = Integer.parseInt(scanner.nextLine());

            Customer customer = customerService.getCustomer(customerId);
            if (customer == null) {
                System.out.println("⚠️ Error: Customer not found");
                return;
            }

            System.out.println("\nReviews for: " + customer.getName());
            List<Reviews> reviews = reviewService.getReviewsByCustomer(customerId);
            if (reviews.isEmpty()) {
                System.out.println("No reviews available for this product.");
            } else {
                for (Reviews review : reviews) {
                    System.out.println(review.getFormattedReviews());
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("⚠️ Invalid input. Please enter a numeric product ID.");
        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
        }
    }
}










