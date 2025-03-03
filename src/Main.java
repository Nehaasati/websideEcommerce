
//import controller.OrderController;

import controller.ManufacturerController;
import repository.*;
import repository.ProductCategoryRepository;


//import controller.OrderController;


import java.sql.*;


public class Main {
    public static void main(String[] args) throws SQLException {

        ManufacturerController manufacturerController = new ManufacturerController();
        manufacturerController.displayManufacturers();

        /*'ProductRepository productRepository = new ProductRepositoryImpl();
        ProductCategoryRepository productCategoryRepository = new ProductCategoryRepository();
        CategoryRepository categoryRepository = new CategoryRepository();
        ProductService productService = new ProductServiceImpl(productRepository,
                productCategoryRepository,
                categoryRepository
        );*/

       /* ProductRepository productRepository = new ProductRepositoryImpl();
        ProductCategoryRepository productCategoryRepository = new ProductCategoryRepository();
        CategoryRepository categoryRepository = new CategoryRepository();

        ProductService productService = new ProductServiceImpl(productRepository, productCategoryRepository,
                categoryRepository);
        ProductController productController = new ProductController(productService);
        productController.displayProductMenu();*/

        //IOrderProductService orderProductService = new OrderProductService();

        //OrderController orderController = new OrderController();

       // OrderProductController orderProductController = new OrderProductController();
        //orderController.displayMenu();
        //orderProductController.run();

        //OrderProductController orderProductController = new OrderProductController(orderProductService);

        //orderController.run();
        //orderProductController.run();




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







 //customers cs =new customers(9,"neha","31@ngmail.com","232312");
//CustomerRepository cr = new CustomerRepository();
//cr.getCustomerById(2);

    }
}

