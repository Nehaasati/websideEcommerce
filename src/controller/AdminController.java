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
    private final Scanner scanner;

    private AdminRole currentAdminRole = AdminRole.BASIC;

    public AdminController(ProductService productService, ProductController productController, Scanner scanner) {
        this.productService = productService;
        this.productController = productController;
        this.scanner = new Scanner(System.in);
    }

    //  Hardcoded admin credentials
    private static final Map<String, String> ADMIN_CREDENTIALS = new HashMap<>();

    static {
        ADMIN_CREDENTIALS.put("admin", "hashed_admin_password");  // BASIC admin
        ADMIN_CREDENTIALS.put("superadmin", "hashed_superadmin_password");  //SUPER admin
    }

    public void start() {
        System.out.print("🔑 Enter username: ");
        String username = scanner.nextLine();

        System.out.print("🔑 Enter admin password: ");
        String password = scanner.nextLine();

        if (authenticate(username, password)) {
            logger.info("Admin login successful");
            this.currentAdminRole = AdminRole.BASIC;  // ✅ BASIC admin role
            showAdminMenu();
        } else if (authenticateSuperAdmin(username, password)) {
            logger.info("Super Admin login successful");
            this.currentAdminRole = AdminRole.SUPER;  // ✅ SUPER admin role
            showAdminMenu();
        } else {
            System.out.println("❌ Access denied! Invalid credentials");
            logger.warning("Failed admin login attempt");
        }
    }

    /*public boolean authenticate(String password) {
        return ADMIN_CREDENTIALS.equals(password);
    }

    public boolean authenticateSuperAdmin(String password){
        return ADMIN_CREDENTIALS.equals(password);
    }*/

    // Authenticate a regular admin
    public boolean authenticate(String username, String password) {
        String storedPassword = ADMIN_CREDENTIALS.get(username);
        return username.equals("admin") && storedPassword != null && storedPassword.equals(password); // Replace plain text password check
    }

    // Authenticate a super admin
    public boolean authenticateSuperAdmin(String username, String password) {
        String storedPassword = ADMIN_CREDENTIALS.get(username);
        return username.equals("superadmin") && storedPassword != null && storedPassword.equals(password);  //Replace plain text password check
    }


    public void showAdminMenu() {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. Restock Product");
            System.out.println("2. Update Stock After Order");
            System.out.println("3. View Low Stock Alerts");
            System.out.println("4. Check Stock Status");
            System.out.println("5. Check Product Price");

            /*if (authenticate(password)) {
                logger.info("Admin login successful");
                showAdminMenu();
            } else if(authenticateSuperAdmin(password)) {
                logger.info("Super Admin login successful");
                this.currentAdminRole = AdminRole.SUPER;
                showAdminMenu();
            }
            else {
                System.out.println("❌ Access denied! Invalid credentials");
                logger.warning("Failed admin login attempt");
            }
        }*/

            if (this.currentAdminRole == AdminRole.SUPER) {
                System.out.println("6. Special Admin Action (Super Admin Only)");
                System.out.println("7. Return to Main.Main Menu");
            } else {
                System.out.println("6. Return to Main.Main Menu");
            }
            System.out.print("Enter choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> handleRestock();
                    case 2 -> handleOrderUpdate();
                    case 3 -> displayLowStockAlerts();
                    case 4 -> handleStockCheck();
                    case 5 -> handlePriceCheck();
                    case 6 -> handleSuperAdminAction();
                    case 7 -> {
                        System.out.println("Returning to Main.Main Menu...");
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

    private void handleOrderUpdate() {
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
    }

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

    private void handleSuperAdminAction() {
        System.out.println("Performing super admin action");
    }

    enum AdminRole {
        BASIC,
        SUPER
    }
}



