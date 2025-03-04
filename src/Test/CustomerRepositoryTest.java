package Test;

import repository.impl.CustomerRepositoryImpl;
import model.Customer;
import util.SqliteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class CustomerRepositoryTest {
    private static final Logger logger = Logger.getLogger(CustomerRepositoryTest.class.getName());

    public static void main(String[] args) {
        logger.info("\n🔍 **Testing createCustomer Method** 🔍");

        // Test Data
        String testName = "Nitu Mishra";
        String testEmail = "nitumshr@example.com";  // Make sure this email is unique
        String testPhone = "0701234567";
        String testAddress = "Main Street 10, City";
        String testPassword = "hashed_password_test";

        CustomerRepositoryImpl repository = new CustomerRepositoryImpl();

        try {
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
}


