package service.impl;
import model.Customer;
import repository.CustomerRepository;
import service.CustomerService;

import java.sql.SQLException;
import java.util.logging.*;
public class CustomerServiceImpl  implements CustomerService{
    private static final Logger logger = Logger.getLogger(CustomerServiceImpl.class.getName());
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer registerCustomer(String name, String email, String phone,
                                     String address, String password) {

        // Validate password first
        if (password == null || password.trim().isEmpty()) {
            logger.warning("Registration attempt with empty password");
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (password.length() < 8) {
            logger.warning("Registration attempt with short password");
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }

        try {
            if (customerRepository.emailExists(email)) {
                logger.warning("Duplicate email registration attempt: " + email);
                throw new IllegalArgumentException("Email already registered");
            }
            return customerRepository.createCustomer(name, email, phone, address, password);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Registration failed for: " + email, e);
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }

    @Override
    public Customer login(String email, String password) {
        try {
            Customer customer = customerRepository.loginCustomer(email, password);
            if (customer != null) {
                logger.info("Successful login: " + email);
            } else {
                logger.warning("Failed login attempt: " + email);
            }
            return customer;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Login error for: " + email, e);
            return null;
        }
    }

    @Override
    public Customer getCustomer(int customerId) {
        try {
            Customer customer = customerRepository.getCustomerDetails(customerId);
            if (customer == null) {
                logger.warning("Customer lookup failed for ID: " + customerId);
            }
            return customer;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching customer: " + customerId, e);
            return null;
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        try {
            // Check if email is being changed
            Customer existing = customerRepository.getCustomerDetails(customer.getCustomerId());
            if (!existing.getEmail().equals(customer.getEmail())) {
                if (customerRepository.emailExists(customer.getEmail())) {
                    logger.warning("Duplicate email update attempt: " + customer.getEmail());
                    throw new IllegalArgumentException("Email already in use");
                }
            }

            customerRepository.updateCustomerDetails(customer);
            logger.info("Successfully updated customer: " + customer.getCustomerId());

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Update error for: " + customer.getCustomerId(), e);
            throw new RuntimeException("Update failed: " + e.getMessage());
        }
    }

    @Override
    public void deleteCustomer(int customerId) {
        try {
            // Verify customer exists first
            Customer customer = customerRepository.getCustomerDetails(customerId);
            if (customer == null) {
                logger.warning("Delete failed: Customer not found ID: " + customerId);
                throw new IllegalArgumentException("Customer not found");
            }

            customerRepository.deleteCustomer(customerId);
            logger.info("Customer deleted successfully ID: " + customerId);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Delete error for customer ID: " + customerId, e);
            throw new RuntimeException("Delete failed: " + e.getMessage());
        }
    }

    @Override
    public boolean validateCredentials(String email, String password) {
        try {
            return customerRepository.loginCustomer(email, password) != null;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Validation error for: " + email, e);
            return false;
        }
    }



}
