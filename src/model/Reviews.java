package model;

import java.sql.ResultSet;

public class Reviews {
    private int reviewId;
    private Product product;
    private Customer customer;
    private int rating;
    private String comment;

    //Construtor
    public Reviews(int reviewId, Product product, Customer customer, int rating, String comment) {
        this.reviewId = reviewId;
        this.product = product;
        this.customer = customer;
        this.rating = rating;
        this.comment = comment;
    }


    // Getters and Setters
    public int getReviewId() {return reviewId;}
    public void setReviewId(int reviewId) {this.reviewId = reviewId;}
    public Product getProduct() {return product;}
    public void setProduct(Product product) {this.product = product;}

    public Customer getCustomer() {return customer;}
    public void setCustomer(Customer customer) {this.customer = customer;}

    public int getRating() {return rating;}
    public void setRating(int rating) {this.rating = rating;}

    public String getComment() {return comment;}
    public void setComment(String comment) {this.comment = comment;}

    // formatted output with symbols and colors
    public String getFormattedReviews(){
        String red = "\u001B[31m";
        String reset = "\u001B[0m";
        StringBuilder stars = new StringBuilder();

        // Create star rating visualization
        for (int i = 0; i < 5; i++) {
            stars.append(i < rating ? red + "★" + reset : "☆");
        }

        return String.format("✎ Review #%d [%s]\n%s\n- %s (%s)\n",
                reviewId,
                rating,
                stars,
                comment,
                customer.getName());
    }
    }


