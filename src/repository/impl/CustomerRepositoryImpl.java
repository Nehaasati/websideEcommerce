package repository.impl;
import model.Customer;
import repository.CustomerRepository;
import util.SqliteConnectionManger;
import java.sql.*;
import java.util.logging.*;


public class CustomerRepositoryImpl implements CustomerRepository {
    private static final Logger logger = Logger.getLogger(CustomerRepositoryImpl.class.getName());

    @Override
    public Customer createCustomer(String name, String email, String phone, String address, String password) throws SQLException {

        logger.info("Attempting to create customer:" + email);

        // Debug log to check if name is empty/null before inserting
        logger.info("Name being inserted: [" + name + "]");


        String sql = "INSERT INTO customers (name, email, phone, address, password) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = SqliteConnectionManger.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, address);
            stmt.setString(5, password);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.severe("Failed to create customer: " + email);
                throw new SQLException("Creating customer failed, no rows affected");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int newId = generatedKeys.getInt(1);
                    logger.info("Successfully created customer with id: " + newId);
                    return new Customer(newId, name, email, phone, address, password);
                }
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed: customers.email")) {
                logger.warning("Duplicate email registration attempt: " + email);
                throw new SQLException("Email already exists", e);
            }
            logger.log(Level.SEVERE, "Database error creating customer", e);
            throw e;
        }
        throw new SQLException("Creating customer failed, no ID obtained.");
    }

    @Override
    public Customer loginCustomer(String email, String password) throws SQLException {
        logger.info("Login attempt for: " + email);
        String sql = "SELECT * FROM customers WHERE email = ? AND password = ?";

        try (Connection conn = SqliteConnectionManger.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    logger.info("Successful login: " + email);
                    return new Customer(
                            rs.getInt("customer_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error during login", e);
            throw e;
        }
        logger.warning("Failed login attempt for: " + email);
        return null;
    }

    @Override
    public Customer getCustomerDetails(int customerId) throws SQLException {
        logger.info("Fetching details for customer: " + customerId);
        String sql =  "SELECT * FROM customers WHERE customer_id = ?";

        try (Connection conn = SqliteConnectionManger.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Log all retrieved values
                    logger.info("Retrieved name: " + rs.getString("name"));
                    logger.info("Retrieved email: " + rs.getString("email"));
                    logger.info("Retrieved phone: " + rs.getString("phone"));
                    logger.info("Retrieved address: " + rs.getString("address"));
                    logger.info("Retrieved password: " + rs.getString("password"));

                    String name = rs.getString("name");

                    // Check if name is empty and log the issue
                    if (name == null || name.trim().isEmpty()) {
                        logger.severe("Empty name for customer ID: " + customerId);
                        throw new SQLException("Invalid customer data: empty name");
                    }

                   /* String nameFromDB = rs.getString("name");  // for debugging
                    logger.info("Retrieved name: " + nameFromDB);

                    String name = rs.getString("name");
                      if (name == null || name.trim().isEmpty()) {
                            logger.severe("Empty name for customer ID: " + customerId);
                            throw new SQLException("Invalid customer data: empty name");
                      }*/
                    return new Customer(
                            rs.getInt("customer_id"),
                            name,
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error fetching customer", e);
            throw e;
        }
        return null;
    }



    @Override
    public void updateCustomerDetails(Customer customer) throws SQLException {
        logger.info("Updating customer: " + customer.getEmail());
        String sql = "UPDATE customers SET name = ?, email = ?, phone = ?, address = ? "
                + "WHERE customer_id = ?";

        try (Connection conn = SqliteConnectionManger.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getAddress());
            stmt.setInt(5, customer.getCustomerId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.severe("Update failed for customer: " + customer.getCustomerId());
                throw new SQLException("Updating customer failed, no rows affected.");
            }
            logger.info("Successfully updated customer: " + customer.getCustomerId());
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                logger.warning("Duplicate email update attempt: " + customer.getEmail());
                throw new SQLException("Email already in use", e);
            }
            logger.log(Level.SEVERE, "Database error updating customer", e);
            throw e;
        }
    }

    @Override
    public void deleteCustomer(int customerId) throws SQLException {
        logger.info("Attempting to delete customer ID: " + customerId);
        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection conn = SqliteConnectionManger.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                logger.warning("No customer found with ID: " + customerId);
                throw new SQLException("No customer found with ID: " + customerId);
            }

            logger.info("Successfully deleted customer ID: " + customerId);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting customer ID: " + customerId, e);
            throw e;
        }
    }

    @Override
    public boolean emailExists(String email) throws SQLException {
        logger.info("Checking email existence: " + email);
        String sql = "SELECT 1 FROM customers WHERE email = ?";

        try (Connection conn = SqliteConnectionManger.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error checking email existence", e);
            throw e;
        }
    }


}
