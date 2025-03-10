package controller;

import model.Product;
import service.ProductService;
import java.util.List;
import java.util.Scanner;


public class ProductController {
    private final ProductService service;
    private final ReviewsController reviewsController;
    private Scanner scanner = new Scanner(System.in);

    public ProductController(ProductService service, ReviewsController reviewsController) {
        this.service = service;
        this.reviewsController = reviewsController;
        this.scanner = new Scanner(System.in);

    }

    public void displayProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products found");
            return;
        }

        // Print table header
        System.out.println("\n| ID  | Manufacturer ID | Name                 | Description           | Price     | Stock | Status      |");
        System.out.println("|-----|----------------|----------------------|----------------------|-----------|-------|-------------|");

        // Print each product in table format
        products.forEach(p -> {
            String status = p.getStockQuantity() == 0 ? "Out of Stock" :
                    p.getStockQuantity() < 10 ? "Low Stock" : "In Stock";
            System.out.printf("| %-3d | %-14d | %-20s | %-20s | %-9.2f | %-5d | %-11s |\n",
                    p.getProductId(), p.getManufacturerId(), p.getName(), p.getDescription(),
                    p.getPrice(), p.getStockQuantity(), status);
        });
    }

    public void showCustomerMenu() {
        while (true) {
            System.out.println("\n=== CUSTOMER MENU ===");
            System.out.println("1. View All Products");
            System.out.println("2. Search Products by Name");
            System.out.println("3. Search Products by Price Range");
            System.out.println("4. Search Products By Category");
            System.out.println("5. View Product Details");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> displayProducts(service.getAllProducts());
                    case 2 -> handleNameSearch();
                    case 3 -> handlePriceSearch();
                    case 4 -> handleCategorySearch();
                    case 5 -> handleProductDetails();
                    case 6 -> System.exit(0);
                    default -> System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private void handleNameSearch() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine().trim();
        List<Product> results = service.searchProductsByName(name);

        if (results.isEmpty()) {
            System.out.println("\nNo products found matching '" + name + "'");
        } else {
            System.out.println("\nFound " + results.size() + " matching products:");
            displayProducts(results);
        }
    }

    private void handlePriceSearch() {
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print("Enter minimum price: ");
                double min = Double.parseDouble(scanner.nextLine());

                System.out.print("Enter maximum price: ");
                double max = Double.parseDouble(scanner.nextLine());

                List<Product> results = service.searchProductsByPriceRange(min, max);
                displayProducts(results);
                validInput = true;   // Exit loop on success

            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Please enter a valid number!");
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void handleCategorySearch() {
        try {
            System.out.print("Enter category ID: ");
            int categoryId = Integer.parseInt(scanner.nextLine());
            displayProducts(service.searchProductsByCategory(categoryId));
        } catch (NumberFormatException e) {
            System.out.println("Invalid category ID!");
        }
    }


    private void handleProductDetails() {
        try {
            System.out.print("Enter product ID: ");
            int productId = Integer.parseInt(scanner.nextLine());
            Product product = service.getProductDetails(productId);

            System.out.println("\n=== PRODUCT DETAILS ===");
            System.out.println("ID: " + product.getProductId());
            System.out.println("Manufacturer: " + product.getManufacturerId());
            System.out.println("Name: " + product.getName());
            System.out.println("Description: " + product.getDescription()); // ADD THIS LINE
            System.out.printf("Price: $%.2f\n", product.getPrice());
            System.out.println("Current Stock: " + product.getStockQuantity());
            System.out.println("Categories: " + product.getCategoryIds());
            System.out.println("\nWould you like to see reviews? (y/n)");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                reviewsController.displayProductReviews(productId);
            }
        }catch(Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
    }






















