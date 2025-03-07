
import controller.*;
import repository.CartRepository;
import repository.CustomerRepository;
import repository.ProductRepository;
import repository.impl.CustomerRepositoryImpl;
import service.CartService;
import service.CustomerService;
import service.ProductService;
import service.impl.CustomerServiceImpl;
import util.SqliteConnectionManger;


import java.sql.*;
//import controller.ProductController;
//import service.impl.ProductServiceImpl;

public class Main {
    public static void main(String[] args) throws SQLException {




        //ProductController productController = new ProductController(productService);

        //productController.handleProductOperations();*/

       // IOrderProductService orderProductService = new OrderProductService();

        //OrderController orderController = new OrderController();
        //orderController.displayMenu();
        //OrderProductController orderProductController = new OrderProductController();
       // orderProductController.displayMenu();


        //ProductRepository productRepo = new ProductRepository();

       ProductRepository productRepository = new ProductRepository();
        CartRepository cartRepository = new CartRepository();

        // Initialize services
         ProductService productService = new ProductService(productRepository);
         CartService cartService = new CartService(cartRepository, productService);

        // Initialize controller
        CartController cartController = new CartController(cartService);
        ProductController productController = new ProductController(productService);
       // productController.showAdminMenu();
        //productController.showCustomerMenu();
        // Start the cart system
        cartController.start();

        // Close database connection before exiting
        SqliteConnectionManger.closeConnection();





        // Start the Categories
       /*CategoryController categoryController = new CategoryController();
          try{
              categoryController.start();
          } catch (Exception e) {
              System.out.println("There are error with" + e.getMessage());
          }*/
        //CustomerService service = new CustomerService();
        //service.showAllCustomers();
        //CartRepository cartRepository = new CartRepository();
        //CartService cartService = new CartService(cartRepository,new ProductService());
        //CartController controller = new CartController(cartService);
        //controller.start();
        //CartRepository cartRepo = new CartRepository();


        //CustomerController customerController = new CustomerController();
        //customerController.run();

        //productController.run();

        //CategoryController categoryController = new CategoryController();
        // categoryController.displayCategories();


         // ManufacturerController manufacturerController = new ManufacturerController();
         // manufacturerController.displayManufacturers();

        //CustomerRepository repository = new CustomerRepositoryImpl();
        //CustomerService service = new CustomerServiceImpl(repository);
        //CustomerController controller = new CustomerController(service);
        //controller.start();


 //customers cs =new customers(9,"neha","31@ngmail.com","232312");
//CustomerRepository cr = new CustomerRepository();
//cr.getCustomerById(2);

    }
}

