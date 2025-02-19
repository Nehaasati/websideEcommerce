package controller;

import model.Product;
import service.ProductService;

import java.sql.SQLException;
import java.util.List;

public class ProductController {
    private ProductService productService;


    public ProductController() {
        this.productService = productService;
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
            e.printStackTrace();
        }
    }
}