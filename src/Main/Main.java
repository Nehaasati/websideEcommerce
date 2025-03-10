package Main;

import controller.*;
import repository.*;
import repository.impl.CustomerRepositoryImpl;
import repository.impl.OrderProductRepository;
import repository.impl.OrderRepository;
import service.*;
import service.impl.CustomerServiceImpl;
import service.impl.OrderService;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        // Initialize repositories
        ProductRepository productRepo = new ProductRepository();
        CartRepository cartRepository = new CartRepository();
        CustomerRepository customerRepo = new CustomerRepositoryImpl();
        OrderRepository orderRepo = new OrderRepository();
        OrderProductRepository orderProductRepository = new OrderProductRepository();


        // Initialize services
        ProductService productService = new ProductService(productRepo);
        CartService cartService = new CartService(cartRepository, productService, orderRepo, orderProductRepository);  // Include orderRepo and orderProductRepository
        CustomerService customerService = new CustomerServiceImpl(customerRepo);
        IOrderService orderService = new OrderService(orderRepo);
        ManufacturerService manufacturerService = new ManufacturerService();
        CategoryService categoryService = new CategoryService();

        // Initialize controllers
        ProductController productController = new ProductController(productService);
        CartController cartController = new CartController(cartService, orderRepo);  // Include orderRepo
        OrderController orderController = new OrderController(orderService);
        ManufacturerController manufacturerController = new ManufacturerController();
        CategoryController categoryController = new CategoryController();
        CustomerController customerController = new CustomerController(
                customerService,
                new ProductController(productService),
                cartController,
                orderController
        );

        //CustomerController customerController = new CustomerController(customerService, productController, cartController,orderController);
        AdminController adminController = new AdminController(productService, productController, manufacturerController,
                categoryController, scanner);  // Include productController and scanner

        GuestMenu guestMenu = new GuestMenu(productService, scanner); // Include scanner

        MainMenu mainMenu = new MainMenu(customerController, adminController, guestMenu, scanner);  // Include guestMenu and scanner
        mainMenu.start(); // Use non-static start

        scanner.close(); // Close scanner once after the program finishes
    }

    public static int getIntInput(Scanner scanner) {  // Pass scanner
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // discard non-integer input
        }
        return scanner.nextInt();
    }
}



//Start the manufacturer menu
        /*ManufacturerController manufacturerController = new ManufacturerController();
        try {
            manufacturerController.start();
        } catch (Exception e) {
            System.out.println("There are error with" + e.getMessage());
        }
    }

        // Start the Categories
       CategoryController categoryController = new CategoryController();
          try{
              categoryController.start();
          } catch (Exception e) {
              System.out.println("There are error with"+ e.getMessage());
          }

        // Customer setup
       CustomerRepository repository = new CustomerRepositoryImpl();
        CustomerService service = new CustomerServiceImpl(repository);
        CustomerController controller = new CustomerController(service);
        controller.start();


        // Order setup
        OrderController orderController = new OrderController();
        orderController.displayMenu();

        OrderProductController orderProductController = new OrderProductController();
        orderProductController.displayMenu();


        // Product management
       /* try {
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
    }*/

        /*try {
            // Initialize repositories
             ProductRepository productRepository = new ProductRepository();
             CartRepository cartRepository = new CartRepository();
           // ReviewsRepository reviewsRepository = new ReviewsRepository();
             CustomerRepository customerRepository = new CustomerRepositoryImpl();

            // Initialize services
            ProductService productService = new ProductService(productRepository);
           // CartService cartService = new CartService(cartRepository, productService);
            CustomerService customerService = new CustomerServiceImpl(customerRepository);
           // ReviewService reviewService = new ReviewService(reviewsRepository);

            // Initialize controllers
             //CartController cartController = new CartController(cartService);
           // ReviewsController reviewsController = new ReviewsController(reviewService, productService, customerService);



            // Start the cart system
         //  cartController.start();
        } catch (Exception e) {
            System.err.println("Cart application failed to start: " + e.getMessage());
        }
    }*/
           /* // Call methods to see the output
           /* reviewsController.displayProductReviews();  // To see reviews for a product
            reviewsController.displayCustomerReviews(); // To see reviews by a customer
        } catch (Exception e) {
            System.out.println("There are error with" + e.getMessage());
        }
        }*/

        // Initialize repositories
       /* ProductRepository productRepository = new ProductRepository();
        CartRepository cartRepository = new CartRepository();
        OrderRepository orderRepository = new OrderRepository();
        OrderProductRepository orderProductRepository = new OrderProductRepository();
        // Initialize services
        ProductService productService = new ProductService(productRepository);
        CartService cartService = new CartService(cartRepository, productService, orderRepository, orderProductRepository);

        // Initialize controllers
        CartController cartController = new CartController(cartService, orderRepository);
        OrderController orderController = new OrderController();*/

        // 🚀 Show the Main.Main Menu (Loop until exit)
       /* while (true) {
            System.out.println("\n=== 🛍️ Main.Main Menu ===");
            System.out.println("1. 🛒 Cart Management");
            System.out.println("2. 📦 Order Management");
            System.out.println("3. ❌ Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    cartController.start();  // Show Cart Menu
                    break;
                case 2:
                    orderController.displayMenu();  // Show Order Menu
                    break;
                case 3:
                    System.out.println("Exiting... 👋");
                    SQLiteConnection.closeConnection();  // Close DB connection before exit
                    return;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }

        }*/


















