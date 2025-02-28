
import controller.CustomerController;
import controller.OrderController;
import controller.OrderProductController;
import repository.CustomerRepository;




import java.sql.*;
//import controller.ProductController;
//import service.impl.ProductServiceImpl;

public class Main {
    public static void main(String[] args) throws SQLException {

        //ProductRepository productRepository = new ProductRepositoryImpl();
        //ProductService productService = new ProductServiceImpl(productRepository);
       // ProductController productController = new ProductController(productService);

       /* ProductRepository productRepository = new ProductRepositoryImpl();
       // ProductService productService = new ProductServiceImpl(productRepository);


        //ProductController productController = new ProductController(productService);

        //productController.handleProductOperations();*/

       // IOrderProductService orderProductService = new OrderProductService();

        //OrderController orderController = new OrderController();
        //orderController.displayMenu();
        OrderProductController orderProductController = new OrderProductController();
        orderProductController.displayMenu();





        // JDBC URL för SQLite
        // String url = "";

       /* Connection connection = SqliteConnection.getConnection();
        if (connection != null) {
            System.out.println("Connection established");

            ProductController productController = new ProductController(connection);
            productController.getAllProducts();
        } else {
            System.out.println("Connection failed");
        }

        //Statement stmt = connection.createStatement(URL);
        //  ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {

        //Loopa genom alla rader i resultatet
          /*  while (rs.next()) {
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

        //CustomerController customerController = new CustomerController();
        //customerController.run();

        //productController.run();

        //CategoryController categoryController = new CategoryController();
        // categoryController.displayCategories();


        //  ManufacturerController manufacturerController = new ManufacturerController();
        //  manufacturerController.displayManufacturers();




 //customers cs =new customers(9,"neha","31@ngmail.com","232312");
//CustomerRepository cr = new CustomerRepository();
//cr.getCustomerById(2);

    }
}

