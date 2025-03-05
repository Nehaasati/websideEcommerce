package repository;

import util.SqliteConnectionManger;
import model.Customer;

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
/* - deleteCustomer
 * - findCustomerByEmail
 *
 * Varje metod kommer följa samma mönster:
 * 1. Skapa Connection med DriverManager.getConnection(URL)
 * 2. Skapa Statement eller PreparedStatement
 * 3. Utför databasoperationen
 * 4. Hantera resultatet
 * 5. Låt try-with-resources stänga alla resurser
 */

