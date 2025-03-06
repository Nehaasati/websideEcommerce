package controller;

import model.Product;
import service.ProductService;
import java.util.List;
import java.util.Scanner;

public class ProductController {
    private ProductService service;
    private Scanner scanner = new Scanner(System.in);

    public ProductController(ProductService service) {
        this.service = service;
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

        if(results.isEmpty()) {
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

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Admin Menu
    public void showAdminMenu() {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. Restock Product");
            System.out.println("2. Update Stock After Order");
            System.out.println("3. View Low Stock Alerts");
            System.out.println("4. Check Stock Status");
            System.out.println("5. Check Product Price");
            System.out.println("6. Return to Main Menu");
            System.out.print("Enter choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> handleRestock();
                    case 2 -> handleOrderUpdate();
                    case 3 -> displayLowStockAlerts();
                    case 4 -> handleStockCheck();
                    case 5 -> handlePriceCheck();
                    case 6 -> {
                        return;
                    }
                    default -> System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    // Admin operations
    private void handleRestock() {
        try {
            System.out.print("Enter product ID to restock: ");
            int productId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter restock quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            service.restockProduct(productId, quantity);
            System.out.println("Restocked successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleOrderUpdate() {
        try {
            System.out.print("Enter product ID: ");
            int productId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter quantity to deduct: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            service.updateStockAfterOrder(productId, quantity);
            System.out.println("Stock updated successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void displayLowStockAlerts() {
        List<Product> lowStock = service.getLowStockProducts();
        if (lowStock.isEmpty()) {
            System.out.println("No low stock alerts");
            return;
        }
        System.out.println("\n=== LOW STOCK ALERTS ===");
        displayProducts(lowStock);
    }

    private void handleStockCheck() {
        try {
            System.out.print("Enter product ID: ");
            int productId = Integer.parseInt(scanner.nextLine());
            int stock = service.getStockStatus(productId);
            System.out.println("\n\nCurrent stock: " + stock);
            System.out.println("In stock: " + service.isProductInStock(productId));
        } catch (NumberFormatException e) {
            System.out.println("Invalid product ID!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handlePriceCheck() {
        try {
            System.out.print("Enter product ID: ");
            int productId = Integer.parseInt(scanner.nextLine());
            double price = service.getProductPrice(productId);
            System.out.printf("Product Price: \n\nsek%.2f", price);
        } catch (NumberFormatException e) {
            System.out.println("Invalid product ID!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}













