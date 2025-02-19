import controller.CustomerController;
//import controller.ProductController;
import controller.CategoryController;
import controller.ManufacturerController;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        // JDBC URL för SQLite
        String url = "";

        /*try (Connection connection = SqliteConnection.getConnection();
             Statement stmt = connection.createStatement(URL);
             ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {

             //Loopa genom alla rader i resultatet
            while (rs.next()) {
                // Ändra kolumnnamnen nedan till de som finns i din tabell
                System.out.println(
                       rs.getInt("customer_id") + " | " +
                             rs.getString("first_name")+"|"+
                               rs.getString("email");

                       // Lägg till fler kolumner efter behov);
                        }

        } catch (SQLException e) {
           System.out.println("Ett fel uppstod: " + e.getMessage());
        }*/
        //CustomerService service = new CustomerService();
        //service.showAllCustomers();

       // CustomerController customerController = new CustomerController();
       // customerController.run();
        //ProductController productController = new ProductController();
        //productController.run();

        //CategoryController categoryController = new CategoryController();
       // categoryController.displayCategories();


       ManufacturerController manufacturerController = new ManufacturerController();
        manufacturerController.displayManufacturers();

    }


//customers cs =new customers(9,"neha","31@ngmail.com","232312");
//CustomerRepository cr = new CustomerRepository();
//cr.getCustomerById(2);
}



