


import controller.*;
import model.Customer;
import repository.*;
import repository.impl.CustomerRepositoryImpl;
import service.CustomerService;
import service.ProductService;
import service.impl.CustomerServiceImpl;


//import controller.OrderController;


import java.sql.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws SQLException {

        //Start the manufacturer menu
        /*ManufacturerController manufacturerController = new ManufacturerController();
          try {
              manufacturerController.start();
          } catch (Exception e) {
              System.out.println("There are error with"+ e.getMessage());
          }*/

        // Start the Categories
      /*  CategoryController categoryController = new CategoryController();
          try{
              categoryController.start();
          } catch (Exception e) {
              System.out.println("There are error with"+ e.getMessage());
          }*/

        // Customer setup
        /*CustomerRepository repository = new CustomerRepositoryImpl();
        CustomerService service = new CustomerServiceImpl(repository);
        CustomerController controller = new CustomerController(service);
        controller.start();*/

        /*OrderController orderController = new OrderController();
        orderController.displayMenu();*/

        /*OrderProductController orderProductController = new OrderProductController();
        orderProductController.displayMenu();*/


        // Product management
        try {
            ProductRepository repo = new ProductRepository();
            ProductService service = new ProductService(repo);
            ProductController controller = new ProductController(service);

            // Choose Admin or Customer mode
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter user type (admin/customer): ");
            String userType = scanner.nextLine().trim().toLowerCase();

            if ("admin".equals(userType)) {
                controller.showAdminMenu();
            } else {
                controller.showCustomerMenu();
            }
        } catch (Exception e) {
            System.err.println("Application failed to start: " + e.getMessage());
        }
    }
}




