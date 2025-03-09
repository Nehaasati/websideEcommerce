
import controller.*;
import repository.CartRepository;
import repository.ProductRepository;
import repository.impl.OrderProductRepository;
import repository.impl.OrderRepository;
import service.CartService;
import service.ProductService;
import util.SqliteConnectionManger;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        // Initialize repositories
        ProductRepository productRepository = new ProductRepository();
        CartRepository cartRepository = new CartRepository();
        OrderRepository orderRepository = new OrderRepository();
        OrderProductRepository orderProductRepository = new OrderProductRepository();
        // Initialize services
        ProductService productService = new ProductService(productRepository);
        CartService cartService = new CartService(cartRepository, productService, orderRepository, orderProductRepository);

        // Initialize controllers
        CartController cartController = new CartController(cartService, orderRepository);
        OrderController orderController = new OrderController();

        // 🚀 Show the Main Menu (Loop until exit)
        while (true) {
            System.out.println("\n=== 🛍️ Main Menu ===");
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
                    SqliteConnectionManger.closeConnection();  // Close DB connection before exit
                    return;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }
    }
}
