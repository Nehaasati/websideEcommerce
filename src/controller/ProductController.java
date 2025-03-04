package controller;
import service.ProductService;

import java.util.Scanner;

public class ProductController {
    private final ProductService productService;
    private final Scanner scanner = new Scanner(System.in);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public void start() {
        while (true) {
            System.out.println("\n--- Product Management ---");
            System.out.println("1. Check Product Stock");
            System.out.println("2. Reduce Product Stock");
            System.out.println("3. Add Product Stock");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> checkStock();
                case 2 -> reduceStock();
                case 3 -> addStock();
                case 4 -> {
                    System.out.println("Exiting Product Management...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void checkStock() {
        System.out.print("Enter Product ID: ");
        int productId = scanner.nextInt();
        System.out.print("Enter Quantity to Check: ");
        int quantity = scanner.nextInt();

        boolean available = productService.checkStock(productId, quantity);
        if (available) {
            System.out.println("✅ Stock is available for this product.");
        } else {
            System.out.println("❌ Not enough stock available.");
        }
    }

    private void reduceStock() {
        System.out.print("Enter Product ID: ");
        int productId = scanner.nextInt();
        System.out.print("Enter Quantity to Reduce: ");
        int quantity = scanner.nextInt();

        boolean success = productService.reduceStock(productId, quantity);
        if (success) {
            System.out.println("✅ Stock reduced successfully.");
        } else {
            System.out.println("❌ Error: Unable to reduce stock.");
        }
    }

    private void addStock() {
        System.out.print("Enter Product ID: ");
        int productId = scanner.nextInt();
        System.out.print("Enter Quantity to Add: ");
        int quantity = scanner.nextInt();

        productService.addStock(productId, quantity);
        System.out.println("✅ Stock added successfully.");
    }
}
