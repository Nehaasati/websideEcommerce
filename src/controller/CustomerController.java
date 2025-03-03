package controller;

import model.Customer;
import service.CustomerService;
import java.util.Scanner;
import java.util.logging.*;

public class CustomerController {
    private static final Logger logger = Logger.getLogger(CustomerController.class.getName());
    private final CustomerService customerService;
    private final Scanner scanner;

     public CustomerController(CustomerService customerService) {
         this.customerService = customerService;
         this.scanner = new Scanner(System.in);
     }

    public void start() {
        while (true) {
            System.out.println("\n--- Customer Management ---");
            System.out.println("1. Register New Customer");
            System.out.println("2. Login");
            System.out.println("3. View Customer Details");
            System.out.println("4. Update Customer Details");
            System.out.println("5. Return to Main Menu");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> displayRegistration();
               // case "2" -> displayLogin();
               // case "3" -> displayViewDetails();
               // case "4" -> diaplayUpdateDetails();
                case "5" -> {
                    System.out.println("Returning to main menu...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayRegistration() {
        System.out.println("\n--- New Customer Registration ---");
        System.out.print("Enter full name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            Customer newCustomer = customerService.registerCustomer(name, email, phone, address, password);
            System.out.println("\nRegistration successful!");
            printCustomerDetails(newCustomer);
        } catch (Exception e) {
            logger.warning("Registration failed: " + e.getMessage());
            System.out.println("Registration error: " + e.getMessage());
        }
    }

    private void handleLogin() {
        System.out.println("\n--- Customer Login ---");
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            Customer customer = customerService.login(email, password);
            if (customer != null) {
                System.out.println("\nLogin successful!");
                printCustomerDetails(customer);
            } else {
                System.out.println("Invalid email or password");
            }
        } catch (Exception e) {
            logger.severe("Login error: " + e.getMessage());
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private void handleViewDetails() {
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

    private void handleUpdateDetails() {
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

    private void printCustomerDetails(Customer customer) {
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
