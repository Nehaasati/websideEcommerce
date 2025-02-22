package controller;
import service.IOrderProductService;
import service.impl.OrderProductService;
import model.OrderProduct;
import java.util.Scanner;

public class OrderProductController {
    private final IOrderProductService orderProductService = new OrderProductService();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            System.out.println("\n=== Order Product Management ===");
            System.out.println("1. Add Order Product");
            System.out.println("2. Delete Order Products by Order ID");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addOrderProduct();
                    break;
                case 2:
                    System.out.print("Enter Order ID: ");
                    int orderId = scanner.nextInt();
                    scanner.nextLine();
                    boolean deleted = orderProductService.deleteOrderProductsByOrderId(orderId);
                    if (deleted) {
                        System.out.println("Order products for Order ID " + orderId + " deleted successfully.");
                    } else {
                        System.err.println("Failed to delete order products for Order ID " + orderId);
                    }
                    break;
                case 0:
                    System.out.println("Exiting Order Product Management.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addOrderProduct() {
        System.out.print("Enter Order ID: ");
        int orderId = scanner.nextInt();
        System.out.print("Enter Product ID: ");
        int productId = scanner.nextInt();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter Price: ");
        double unitPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        OrderProduct op = new OrderProduct(0,productId, quantity, unitPrice);
        op.setOrderId(orderId);
        boolean success = orderProductService.addOrderProduct(op);
        if (success) {
            System.out.println("Order product added successfully.");
        } else {
            System.err.println("Failed to add order product.");
        }
    }
}
