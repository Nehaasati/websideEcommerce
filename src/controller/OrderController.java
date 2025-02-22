package controller;
import service.IOrderService;
import service.impl.OrderService;
import model.Order;
import model.OrderProduct;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderController {
    private final IOrderService orderService = new OrderService();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            System.out.println("\n=== Order Management ===");
            System.out.println("1. Place Order");
            System.out.println("2. Cancel Order");
            System.out.println("3. Display Order with Products");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    placeOrder();
                    break;
                case 2:
                    System.out.print("Enter Order ID to cancel: ");
                    int orderIdToCancel = scanner.nextInt();
                    scanner.nextLine();
                    orderService.cancelOrder(orderIdToCancel);
                    break;
                case 3:
                    System.out.print("Enter Order ID to display: ");
                    int orderIdToDisplay = scanner.nextInt();
                    scanner.nextLine();
                    orderService.displayOrderWithProducts(orderIdToDisplay);
                    break;
                case 0:
                    System.out.println("Exiting Order Management.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void placeOrder() {
        System.out.print("Enter Customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        List<OrderProduct> orderProducts = new ArrayList<>();
        System.out.print("Enter number of products for the order: ");
        int numProducts = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < numProducts; i++) {
            System.out.println("Enter details for product " + (i + 1) + ":");
            System.out.print("Product ID: ");
            int productId = scanner.nextInt();
            System.out.print("Quantity: ");
            int quantity = scanner.nextInt();
            System.out.print("Price: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            OrderProduct op = new OrderProduct(productId, quantity, price);
            orderProducts.add(op);
        }

        // Create order with the current date
        Order order = new Order(customerId, new Date(System.currentTimeMillis()));
        orderService.placeOrder(order, orderProducts);
    }
}
