package controller;

import repository.OrderRepository;
import repository.impl.OrderRepositoryImpl;
import service.OrderService;
import service.impl.OrderServiceImpl;

import java.util.List;
import java.util.Scanner;

public class OrderController {
    private final OrderService orderService;
    private final Scanner scanner = new Scanner(System.in);

    // Constructor to properly initialize OrderService with OrderRepositoryImpl
    public OrderController() {
        this.orderService = new OrderServiceImpl(new OrderRepositoryImpl());
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n=== Order Management ===");
            System.out.println("1. Place Order");
            System.out.println("2. Get All Orders");
            System.out.println("3. Cancel Order");
            System.out.println("4. Update Order");
            System.out.println("5. View Order History");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter Customer ID: ");
                    int customerId = scanner.nextInt();
                    int orderId = orderService.placeOrder(customerId);
                    System.out.println(orderId > 0 ? "Order placed successfully! Order ID: " + orderId : "Failed to place order.");
                    break;
                case 2:
                    List<String> orders = orderService.getAllOrders();
                    if (orders.isEmpty()) {
                        System.out.println("No orders found.");
                    } else {
                        orders.forEach(System.out::println);
                    }
                    break;
                case 3:
                    System.out.print("Enter Order ID to cancel: ");
                    int cancelOrderId = scanner.nextInt();
                    System.out.println(orderService.cancelOrder(cancelOrderId) ? "Order Cancelled!" : "Cancel Failed!");
                    break;
                case 4:
                    System.out.print("Enter Order ID to Update: ");
                    int updateOrderId = scanner.nextInt();
                    System.out.print("Enter New Customer ID: ");
                    int newCustomerId = scanner.nextInt();
                    System.out.println(orderService.updateOrder(updateOrderId, newCustomerId) ? "Order Updated!" : "Update Failed!");
                    break;
                case 5:
                    System.out.print("Enter Customer ID: ");
                    int viewCustomerId = scanner.nextInt();
                    List<String> history = orderService.getOrderHistory(viewCustomerId);
                    if (history.isEmpty()) {
                        System.out.println("No order history found.");
                    } else {
                        history.forEach(System.out::println);
                    }
                    break;
                case 6:
                    System.out.println("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}