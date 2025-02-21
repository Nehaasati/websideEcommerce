package repository;
import util.SqliteConnection;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {


    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customers";
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = SqliteConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int customerId = rs.getInt("customer_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String password = rs.getString("password");
                customers.add(new Customer(customerId, name, email, phone, address, password));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return customers;
    }

    public Customer getCustomerById(int customerId) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        try (Connection conn = SqliteConnection.getConnection(); // Ensure connection is properly opened
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("password")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving customer: " + e.getMessage());
        }

        return null; // Return null if no customer found
    }

    public boolean addNewCustomer(Customer customer) {
        String sql = " insert into customers(name, email, phone, address,password) values(?,?,?,?,?)";
        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getAddress());
            pstmt.setString(5, customer.getPassword());

            int row = pstmt.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.err.println("Error adding customer to database:" + e.getMessage());

            return false;
        }
    }
    public  boolean deleteCustomer(int customerId) {
        String sql = "DELETE fROM CUSTOMERS WHERE customer_id =?";
        try {
            Connection conn = SqliteConnection.getConnection();
            PreparedStatement ptsmt = conn.prepareStatement(sql);

            ptsmt.setInt(1, customerId);
            int row = ptsmt.executeUpdate();
            return  row >0; //return true if row are deleted

        } catch (SQLException e) {
            System.err.println("delete customer : " + e.getMessage());

            return false;
        }
    }

    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATED customers  SET name =?,email =?, phone =?, address =?, password =? WHERE customer_id =?";
        try {
            Connection conn = SqliteConnection.getConnection();
            PreparedStatement ptstmt = conn.prepareStatement(sql);

            ptstmt.setString(1, customer.getName());
            ptstmt.setString(2, customer.getEmail());
            ptstmt.setString(3, customer.getPhone());
            ptstmt.setString(4, customer.getAddress());
            ptstmt.setString(5, customer.getPassword());
            ptstmt.setInt(6, customer.getCustomer_id());

            int row = ptstmt.executeUpdate();
            return row > 0;// update customer
        } catch (Exception e) {
            System.err.println("Error updating customer :" + e.getMessage());
            return false;
        }

    }

    // Find a customer by email
    public Customer getCustomerByEmail(String email) {
        String sql = "SELECT * FROM customers WHERE email = ?";
        try (Connection conn = SqliteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving customer by email: " + e.getMessage());
        }
        return null; // Return null if no customer found with the provided email
    }

}





/**
 * Här kan fler metoder läggas till som t.ex:
 * - addCustomer
 * - getCustomerById
 * - updateCustomer
 * - deleteCustomer
 * - findCustomerByEmail
 *
 * Varje metod kommer följa samma mönster:
 * 1. Skapa Connection med DriverManager.getConnection(URL)
 * 2. Skapa Statement eller PreparedStatement
 * 3. Utför databasoperationen
 * 4. Hantera resultatet
 * 5. Låt try-with-resources stänga alla resurser
 */

