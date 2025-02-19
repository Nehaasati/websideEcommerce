package controller;

import model.Product;
import service.ProductService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductController {
    private ProductService productService;


    public ProductController(Connection connection) {
        if (connection != null) {
            this.productService = new ProductService(connection);
        } else {
            throw new IllegalArgumentException("Database connection cannot be null");
        }
    }

    public void getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            if (products.isEmpty()) {
                System.out.println("No products found");
            } else {
                for (Product product : products) {
                    System.out.println(product);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting products: " + e.getMessage());
        }
    }
}