package repository;

import model.Customer;
import java.sql.SQLException;

public interface CustomerRepository {
     Customer createCustomer(String name, String email, String phone, String address, String password) throws SQLException;
     Customer loginCustomer(String email, String password) throws SQLException;
     Customer getCustomerDetails(int customerId) throws SQLException;
     void updateCustomerDetails(Customer customer) throws SQLException;
     void deleteCustomer(int customerId) throws SQLException;
     boolean emailExists(String email) throws SQLException;
}









