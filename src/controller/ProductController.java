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

        System.out.println("\n| ID  | Name                 | Price     | Stock | Status      |");
        System.out.println("|-----|----------------------|-----------|-------|-------------|");
        products.forEach(p -> {
            String status = p.getStockQuantity() == 0 ? "Out of Stock" :
                    p.getStockQuantity() < 10 ? "Low Stock" : "In Stock";
            System.out.printf("| %-3d | %-20s | %-9.2f | %-5d | %-11s |\n",
                    p.getProductId(), p.getName(), p.getPrice(), p.getStockQuantity(), status);
        });
    }

    public void showCustomerMenu() {
        while (true) {
            System.out.println("\n=== CUSTOMER MENU ===");
            System.out.println("1. View All Products");
            System.out.println("2. Search Products by Name");
            System.out.println("3. Search Products by Price Range");
            System.out.println("4. View Product Details");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> displayProducts(service.getAllProducts());
                    case 2 -> handleNameSearch();
                    case 3 -> handlePriceSearch();
                    case 4 -> handleProductDetails();
                    case 5 -> System.exit(0);
                    default -> System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }

    private void handleNameSearch() {
        System.out.println("Enter product name");
        String name = scanner.nextLine();
        displayProducts(service.getAllProducts());
    }

    private void handlePriceSearch() {
        try {
            System.out.print("Enter minimum price: ");
            double min = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter maximum price: ");
            double max = Double.parseDouble(scanner.nextLine());
            displayProducts(service.searchProductsByPriceRange(min, max));
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format!");
        }
    }

    private void handleProductDetails() {
        try {
            System.out.print("Enter product ID: ");
            int productId = Integer.parseInt(scanner.nextLine());
            Product product = service.getProductDetails(productId);
            System.out.println("\nProduct Details:");
            System.out.println("Name: " + product.getName());
            System.out.println("Description: " + product.getDescription());
            System.out.printf("Price: $%.2f\n", product.getPrice());
            System.out.println("Current Stock: " + product.getStockQuantity());
        } catch (NumberFormatException e) {
            System.out.println("Invalid product ID format!");
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
            System.out.println("5. Return to Main Menu");
            System.out.print("Enter choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> handleRestock();
                    case 2 -> handleOrderUpdate();
                    case 3 -> displayLowStockAlerts();
                    case 4 -> handleStockCheck();
                    case 5 -> System.exit(0);
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
        if(lowStock.isEmpty()) {
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
            System.out.println("Current stock: " + stock);
            System.out.println("In stock: " + service.isProductInStock(productId));
        } catch (NumberFormatException e) {
            System.out.println("Invalid product ID!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}












