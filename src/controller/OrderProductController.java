package controller;

import model.OrderProduct;
import repository.impl.OrderProductRepository;
import service.IOrderProductService;
import service.impl.OrderProductService;

import java.util.List;
import java.util.Scanner;

public class OrderProductController {
    private final IOrderProductService orderProductService;
    private final Scanner scanner = new Scanner(System.in);

    public OrderProductController() {
        this.orderProductService = new OrderProductService(new OrderProductRepository());
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n=== Order Product Management ===");
            System.out.println("1. Add Product to Order");
            System.out.println("2. View Order Products");
            System.out.println("3. Remove Product from Order");
            System.out.println("4. Back to Order Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter Order ID: ");
                    int orderId = scanner.nextInt();
                    System.out.print("Enter Product ID: ");
                    int productId = scanner.nextInt();
                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    System.out.print("Enter Unit Price: ");
                    double unitPrice = scanner.nextDouble();
                    boolean added = orderProductService.addOrderProduct(orderId, productId, quantity, unitPrice);
                    System.out.println(added ? "Product added to order!" : "Failed to add product.");
                    break;
                case 2:
                    System.out.print("Enter Order ID to view products: ");
                    int viewOrderId = scanner.nextInt();
                    List<OrderProduct> orderProducts = orderProductService.getOrderProducts(viewOrderId);
                    if (orderProducts.isEmpty()) {
                        System.out.println("No products found for this order.");
                    } else {
                        orderProducts.forEach(System.out::println);
                    }
                    break;
                case 3:
                    System.out.print("Enter Order Product ID to remove: ");
                    int orderProductId = scanner.nextInt();
                    boolean removed = orderProductService.removeOrderProduct(orderProductId);
                    System.out.println(removed ? "Product removed from order!" : "Failed to remove product.");
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
