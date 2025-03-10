package Test;

import repository.impl.CustomerRepositoryImpl;
import model.Customer;
import util.SqliteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

public class CustomerRepositoryTest {
    private static final Logger logger = Logger.getLogger(CustomerRepositoryTest.class.getName());

    public static void main(String[] args) {
        logger.info("\n🔍 **Testing createCustomer Method** 🔍");
        CustomerRepositoryImpl repo = new CustomerRepositoryImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Customer Test Menu ---");
            System.out.println("1. Register New Customer");
            System.out.println("2. View Customer Details");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerCustomer(repo, scanner);
                    break;
                case 2:
                    viewCustomer(repo, scanner);
                    break;
                case 3:
                    System.out.println("Exiting test...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void registerCustomer(CustomerRepositoryImpl repo, Scanner scanner) {
        try {
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Phone: ");
            String phone = scanner.nextLine();
            System.out.print("Enter Address: ");
            String address = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            Customer newCustomer = repo.createCustomer(name, email, phone, address, password);
            if (newCustomer != null) {
                logger.info("Customer registered successfully with ID: " + newCustomer.getCustomerId());
            } else {
                logger.warning("Customer registration failed.");
            }
        } catch (Exception e) {
            logger.severe("Error registering customer: " + e.getMessage());
        }
    }

    private static void viewCustomer(CustomerRepositoryImpl repo, Scanner scanner) {
        try {
            System.out.print("Enter Customer ID: ");
            int customerId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Customer customer = repo.getCustomerDetails(customerId);

            if (customer != null) {
                System.out.println("\n--- Customer Details ---");
                System.out.println("ID: " + customer.getCustomerId());
                System.out.println("Name: " + customer.getName());
                System.out.println("Email: " + customer.getEmail());
                System.out.println("Phone: " + customer.getPhone());
                System.out.println("Address: " + customer.getAddress());
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            logger.severe("Error retrieving customer details: " + e.getMessage());
        }
    }
}




// Test Data
       /* String testName = "Nitu Mishra";
        String testEmail = "nitumshr@example.com";  // Make sure this email is unique
        String testPhone = "0701234567";
        String testAddress = "Main.Main Street 10, City";
        String testPassword = "hashed_password_test";

        CustomerRepositoryImpl repository = new CustomerRepositoryImpl();
        Scanner scanner = new Scanner(System.in);

       /* try {
            // Call createCustomer method
           Customer newCustomer = repository.createCustomer(testName, testEmail, testPhone, testAddress, testPassword);

            // Log success message
           // logger.info("✅ Customer Created: " + newCustomer.getEmail());

            // Verify in database
           // verifyCustomerInDB(testEmail);

            // Delete Customer
            deleteTestCustomer(testEmail);

        } catch (SQLException e) {
            logger.severe("❌ Test failed: " + e.getMessage());
        }
    }

    private static void verifyCustomerInDB(String email) {
        String sql = "SELECT * FROM customers WHERE email = ?";

        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                logger.info("✅ Customer Found in Database: " + rs.getString("email"));
            } else {
                logger.warning("⚠️ Customer Not Found in Database!");
            }
        } catch (SQLException e) {
            logger.severe("❌ Database Error: " + e.getMessage());
        }
    }

    private static void deleteTestCustomer(String email) {
        String sql = "DELETE FROM customers WHERE email = ?";

        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                logger.info("🗑️ Deleted test customer: " + email);
            }
        } catch (SQLException e) {
            logger.severe("❌ Error deleting test customer: " + e.getMessage());
        }
    }
}*/


