package controller;

import model.Customer;
import service.CustomerService;
import java.util.Scanner;
import java.util.logging.*;

public class CustomerController {
    private static final Logger logger = Logger.getLogger(CustomerController.class.getName());
    private final CustomerService customerService;
    private final ProductController productController;
    private final CartController cartController;
    private final OrderController orderController;
    private final Scanner scanner;
    private int loggedInCustomerId = -1;   //Track Logged-in user

    public CustomerController(CustomerService customerService, ProductController productController, CartController cartController, OrderController orderController) {
        this.customerService = customerService;
        this.productController = productController;
        this.cartController = cartController;
        this.orderController = orderController;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n👤 === CUSTOMER PORTAL ===");
            System.out.println("1. 📝 Register");
            System.out.println("2. 🔑 Login");
            System.out.println("3. 👀 Continue as Guest");
            System.out.println("4. ↩️ Return to Main Menu");
            System.out.print(" Choose option: ");

            int choice = getIntInput();
            switch (choice) {
                case 1 -> handleRegistration();
                case 2-> handleLogin();
                case 3 -> handleGuestBrowse();
                case 4-> { return;}
                default ->  System.out.println("Returning to main menu...");
                }
            }
        }


    private void handleRegistration() {
        System.out.println("\n📝 === NEW REGISTRATION ===");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Basic input validation
        if (password.isEmpty()) {
            System.out.println("Error: Password cannot be empty");
            return;
        }

        try {
            Customer newCustomer = customerService.registerCustomer(name, email, phone, address, password);
            System.out.println("\nRegistration successful!");
            printCustomerDetails(newCustomer);
        } catch (Exception e) {
            System.out.println("Registration error: " + e.getMessage());
        }

        System.out.println("Do you want to login now? (yes/no)");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("yes")) {
            handleLogin();
        }
    }

    private void handleLogin() {
        System.out.println("\n🔑 === CUSTOMER LOGIN ===");
        System.out.print("\n📧 Email: ");
        String email = scanner.nextLine();
        System.out.print("🔒 Password: ");
        String password = scanner.nextLine();

        try {
            Customer customer = customerService.login(email, password);
            loggedInCustomerId = customer.getCustomerId();
            System.out.println("\n✅ Login successful! Welcome, " + customer.getName() + "!");
            showCustomerPortal();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Login failed", e);
            System.out.println("❌ Login failed: " + e.getMessage());
        }
    }
            /*if (customer != null) {
                loggedInCustomerId = customer.getCustomerId();
                System.out.println("\n✅ Login successful! Welcome, " + customer.getName() + "!");
                showCustomerPortal();
            }
        } catch (Exception e) {
            logger.severe("Login error: " + e.getMessage());
            System.out.println("Login failed: " + e.getMessage());
        }
    }*/

    private void showCustomerPortal() {
        while (loggedInCustomerId != -1) {
            System.out.println("\n🏠 === CUSTOMER PORTAL ===");
            System.out.println("1. 🛍️ Browse Products");
            System.out.println("2. 🛒 Cart Management");
            System.out.println("3. 📦 Order Management");
            System.out.println("4. 🔐 Account Management");
            System.out.println("5. 🚪 Logout");
            System.out.print("🔀 Choose option: ");

            int choice = getIntInput();
            switch (choice) {
                case 1-> productController.showCustomerMenu();
                case 2-> cartController.start(loggedInCustomerId);
                case 3-> orderController.displayMenu(loggedInCustomerId);
                case 4-> showAccountManagement();
                case 5-> {
                    loggedInCustomerId = -1;
                    System.out.println("👋 Logged out successfully!");
                }
                default -> System.out.println("❌ Invalid choice");
            }
        }
    }

            private void showAccountManagement () {
                while (true) {
                    System.out.println("\n🔐 === ACCOUNT MANAGEMENT ===");
                    System.out.println("1. 👁️ View Details");
                    System.out.println("2. ✏️ Update Details");
                    System.out.println("3. 🗑️ Delete Account");
                    System.out.println("4. ↩️ Back");
                    System.out.print("🔀 Choose option: ");

                    int choice = getIntInput();

                    switch (choice) {
                        case 1-> handleViewDetails();
                        case 2-> handleUpdateDetails();
                        case 3-> handleDeleteCustomer();
                        case 4 -> {
                            return;
                        }
                        default -> System.out.println("❌ Invalid choice");
                    }
                }
            }

            private void handleGuestBrowse () {
                System.out.println("\n👀 === GUEST BROWSING ===");
                productController.showCustomerMenu();
                System.out.println("\n🔒 Please login to add items to cart!");
            }

            private void handleViewDetails () {
                System.out.println("\n--- View Customer Details ---");
                System.out.print("Enter customer ID: ");
                try {
                    int customerId = Integer.parseInt(scanner.nextLine());
                    Customer customer = customerService.getCustomer(customerId);

                    if (customer != null) {
                        printCustomerDetails(customer);
                    } else {
                        System.out.println("Customer not found");
                    }
                } catch (NumberFormatException e) {
                    logger.warning("Invalid ID input: " + e.getMessage());
                    System.out.println("Invalid customer ID format");
                } catch (Exception e) {
                    logger.severe("Error retrieving customer: " + e.getMessage());
                    System.out.println("Error retrieving customer details");
                }
            }

            private void handleUpdateDetails () {
                System.out.println("\n--- Update Customer Details ---");
                System.out.print("Enter customer ID: ");
                try {
                    int customerId = Integer.parseInt(scanner.nextLine());
                    Customer customer = customerService.getCustomer(customerId);

                    if (customer == null) {
                        System.out.println("Customer not found");
                        return;
                    }

                    System.out.println("\nCurrent details:");
                    printCustomerDetails(customer);

                    System.out.println("\nEnter new details (press Enter to keep current value):");
                    System.out.print("New name [" + customer.getName() + "]: ");
                    String newName = scanner.nextLine();
                    System.out.print("New email [" + customer.getEmail() + "]: ");
                    String newEmail = scanner.nextLine();
                    System.out.print("New phone [" + customer.getPhone() + "]: ");
                    String newPhone = scanner.nextLine();
                    System.out.print("New address [" + customer.getAddress() + "]: ");
                    String newAddress = scanner.nextLine();

                    // Update fields if new values provided
                    if (!newName.isEmpty()) customer.setName(newName);
                    if (!newEmail.isEmpty()) customer.setEmail(newEmail);
                    if (!newPhone.isEmpty()) customer.setPhone(newPhone);
                    if (!newAddress.isEmpty()) customer.setAddress(newAddress);

                    customerService.updateCustomer(customer);
                    System.out.println("\nCustomer details updated successfully:");
                    printCustomerDetails(customer);

                } catch (NumberFormatException e) {
                    logger.warning("Invalid ID input: " + e.getMessage());
                    System.out.println("Invalid customer ID format");
                } catch (Exception e) {
                    logger.severe("Update error: " + e.getMessage());
                    System.out.println("Update failed: " + e.getMessage());
                }
            }

            private void handleDeleteCustomer () {
                System.out.println("\n--- Delete Customer ---");
                System.out.print("Enter customer ID to delete: ");

                try {
                    int customerId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Are you sure you want to delete customer " + customerId + "? (y/n): ");
                    String confirmation = scanner.nextLine();

                    if (confirmation.equalsIgnoreCase("y")) {
                        customerService.deleteCustomer(customerId);
                        System.out.println("Customer deleted successfully");
                    } else {
                        System.out.println("Deletion cancelled");
                    }
                } catch (NumberFormatException e) {
                    logger.warning("Invalid ID input: " + e.getMessage());
                    System.out.println("Invalid customer ID format");
                } catch (Exception e) {
                    logger.severe("Delete error: " + e.getMessage());
                    System.out.println("Delete failed: " + e.getMessage());
                }
            }

    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Please enter a number.");
            return -1;
        }
    }

            private void printCustomerDetails (Customer customer){
                System.out.println("\n+------------+----------------------+");
                System.out.println("| CustomerID | Value                |");
                System.out.println("+------------+----------------------+");
                System.out.printf("| %-10d | %-20s |\n", customer.getCustomerId(), "ID");
                System.out.printf("| %-10s | %-20s |\n", "Name", customer.getName());
                System.out.printf("| %-10s | %-20s |\n", "Email", customer.getEmail());
                System.out.printf("| %-10s | %-20s |\n", "Phone", customer.getPhone());
                System.out.printf("| %-10s | %-20s |\n", "Address", customer.getAddress());
                System.out.println("+------------+----------------------+");
            }
    }


