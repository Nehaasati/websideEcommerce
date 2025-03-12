package controller;

import service.ProductService;
import model.Product;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class AdminController {
    private static final Logger logger = Logger.getLogger(AdminController.class.getName());
    private final ProductService productService;
    private final ProductController productController;
    private final ManufacturerController manufacturerController;
    private final CategoryController categoryController;
    private final Scanner scanner;

    // Representing admin roles (Basic Admin or Super Admin)
    private AdminRole currentAdminRole = AdminRole.BASIC;
    //private AdminRole currentAdminRole;

    public AdminController(ProductService productService, ProductController productController, ManufacturerController manufacturerController, CategoryController categoryController, ReviewsController reviewsController, Scanner scanner) {
        {
            this.productService = productService;
            this.productController = productController;
            this.manufacturerController = manufacturerController;
            this.categoryController = categoryController;
            this.scanner = new Scanner(System.in);
        }
    }

    //  Hardcoded admin credentials for login
    private static final Map<String, String> ADMIN_CREDENTIALS = new HashMap<>();

    static {
        ADMIN_CREDENTIALS.put("admin", "hashed_admin_password");           // Basic Admin
        ADMIN_CREDENTIALS.put("superadmin", "hashed_superadmin_password"); // Super Admin
    }


    // * Entry point for Admin authentication.
    public void start() {

        System.out.print("🔑 Enter username: ");
        String username = scanner.nextLine();

        System.out.print("🔑 Enter admin password: ");
        String password = scanner.nextLine();

        if (authenticate(username, password)) {
            logger.info("Admin login successful");
            this.currentAdminRole = AdminRole.BASIC;
            System.out.println("Login successful, Welcome" + " " + username);  // ✅ BASIC admin role
            showAdminMenu();
        } else if (authenticateSuperAdmin(username, password)) {
            logger.info("Super Admin login successful");
            this.currentAdminRole = AdminRole.SUPER;
            System.out.println("Login successful, Welcome" + " " + username); // ✅ SUPER admin role
            showSuperAdminMenu();
        } else {
            System.out.println("❌ Access denied! Invalid credentials");
            logger.warning("Failed admin login attempt");
        }
    }

    // Authenticate a BASIC admin
    public boolean authenticate(String username, String password) {
        String storedPassword = ADMIN_CREDENTIALS.get(username);
        return username.equals("admin") && storedPassword != null && storedPassword.equals(password); // Replace plain text password check
    }

    // Authenticate a SUPER admin
    public boolean authenticateSuperAdmin(String username, String password) {
        String storedPassword = ADMIN_CREDENTIALS.get(username);
        return username.equals("superadmin") && storedPassword != null && storedPassword.equals(password);  //Replace plain text password check
    }


    //Basic Admin Menu - Handles Product, Category, Manufacturer, and Review management.
    public void showAdminMenu() {
        while (true) {
            System.out.println("\n🛠️ === BASIC ADMIN MENU === 🛠️");
            System.out.println("1. Restock Product");
            System.out.println("2. View Low Stock Alerts");
            System.out.println("3. Check Stock Status");
            System.out.println("4. Check Product Price");
            System.out.println("5. Update Product Price"); // New option
            System.out.println("6. Manufacturer Management");
            System.out.println("7. Category Management");
            System.out.println("8. Manage Reviews");
            System.out.println("9.Logout & Return to Main Menu");

            System.out.print("➡️ Enter choice: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> handleRestock();
                    //case 2 -> handleStockDeductionForOrder();
                    case 2 -> displayLowStockAlerts();
                    case 3 -> handleStockCheck();
                    case 4 -> handlePriceCheck();
                    case 5 -> handlePriceUpdate();
                    case 6 -> manufacturerController.start();
                    case 7-> categoryController.start();
                    case 8 -> handleReviewManagement();
                    //case 9 -> handleSuperAdminAction();
                    case 9-> {
                        System.out.println("\uD83D\uDD1A Logging out...Returning to Main Menu...");
                        return;  // Exit the admin menu
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                logger.warning("Invalid input: " + e.getMessage());
                System.out.println("Invalid input. Please enter a number.");
            } catch (Exception e) {
                logger.severe("Error during admin menu operation: " + e.getMessage());
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    // Admin operations
    private void handleRestock() {
        try {
            System.out.print("Enter product ID to restock: ");
            int productId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter quantity to add: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            productService.restockProduct(productId, quantity);
            System.out.println("Restocked successfully!");
            logger.info("Product " + productId + " restocked with quantity " + quantity);
        } catch (NumberFormatException e) {
            logger.warning("Invalid input for product ID or quantity: " + e.getMessage());
            System.out.println("Invalid input format!");
        } catch (Exception e) {
            logger.severe("Restock error: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
    }

    /*private void handleStockDeductionForOrder() {        //Rename handleOrderUpdate() for more clarification
        try {
            System.out.print("Enter product ID: ");
            int productId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter quantity to deduct: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            boolean success = productService.reduceStock(productId, quantity);

            if (success) {
                System.out.println("Stock updated successfully!");
                logger.info("Reduced stock for product ID " + productId + " by quantity " + quantity);
            } else {
                System.out.println("Not enough stock available or product not found!");
                logger.warning("Failed to reduce stock for product ID " + productId);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input format!");
            logger.warning("Invalid input for product ID or quantity: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            logger.severe("Error during stock update: " + e.getMessage());
        }
    }*/


   /* private void handleOrderUpdate() {
        try {
            System.out.print("Enter product ID: ");
            int productId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter quantity to deduct: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            productService.updateStockAfterOrder(productId, quantity);
            System.out.println("Stock updated successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }*/

    private void displayLowStockAlerts() {
        List<Product> lowStock = productService.getLowStockProducts();
        if (lowStock.isEmpty()) {
            System.out.println("No low stock alerts");
            return;
        }
        System.out.println("\n=== LOW STOCK ALERTS ===");
        productController.displayProducts(lowStock);
    }

    private void handleStockCheck() {
        try {
            System.out.print("Enter product ID: ");
            int productId = Integer.parseInt(scanner.nextLine());
            int stock = productService.getStockStatus(productId);
            System.out.println("\n\nCurrent stock: " + stock);
            System.out.println("In stock: " + productService.isProductInStock(productId));
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
            double price = productService.getProductPrice(productId);
            System.out.printf("Product Price: \n\nsek%.2f", price);
        } catch (NumberFormatException e) {
            System.out.println("Invalid product ID!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // AdminController.java
    private void handlePriceUpdate() {
        try {
            System.out.print("Enter Product ID: ");
            int productId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter New Price: ");
            double newPrice = Double.parseDouble(scanner.nextLine());

            productService.updateProductPrice(productId, newPrice);
            System.out.println("✅ Price updated successfully!");
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid numeric input");
            logger.warning("Invalid price input: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            logger.warning("Price update validation failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Failed to update price: " + e.getMessage());
            logger.severe("Price update failed: " + e.getMessage());
        }
    }

    private void handleReviewManagement() {
        System.out.println("\n=== REVIEW MANAGEMENT ===");
        System.out.println("1. View All Product Reviews");
        System.out.println("2. Remove Inappropriate Review");
    }

    //Super Admin Menu - Manages Admins and performs Super Admin level actions.
    public void showSuperAdminMenu() {
        while (true) {
            System.out.println("\n👑 === SUPER ADMIN MENU ===");
            System.out.println("1. ➕ Add Admin");
            System.out.println("2. ➖ Remove Admin");
            System.out.println("3. 👁️ View Admin List");
            System.out.println("4. 🔒 Logout");

            System.out.print("➡️ Enter choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> handleAddAdmin();
                    case 2 -> handleRemoveAdmin();
                    case 3 -> handleViewAdminList();
                    case 4 -> {
                        System.out.println("Logging out... Returning to Gizmo Grid");
                        return;  // Exit to main menu
                    }
                    default -> System.out.println("❗ Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                logger.warning("Invalid input: " + e.getMessage());
                System.out.println("❗ Invalid input. Please enter a number.");
            }
        }
    }

    // SUPER ADMIN OPERATIONS
    private void handleAddAdmin() {
        System.out.print("Enter new admin username: ");
        String newAdmin = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        ADMIN_CREDENTIALS.put(newAdmin, password);
        System.out.println("✅ New admin added: " + newAdmin);
    }


    private void handleRemoveAdmin() {
        System.out.print("Enter admin username to remove: ");
        String removeAdmin = scanner.nextLine();

        if (ADMIN_CREDENTIALS.containsKey(removeAdmin)) {
            ADMIN_CREDENTIALS.remove(removeAdmin);
            System.out.println("❌ Admin removed: " + removeAdmin);
        } else {
            System.out.println("⚠️ Admin not found.");
        }
    }

    private void handleViewAdminList() {
        System.out.println("📋 Current Admins:");
        for (String admin : ADMIN_CREDENTIALS.keySet()) {
            System.out.println("- " + admin);
        }
    }

    /*private void handleSuperAdminAction() {
        System.out.println("Performing super admin action");
    }*/

    enum AdminRole {
        BASIC,
        SUPER
    }
}



