package controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import model.CartItem;
import repository.impl.OrderRepository;
import service.CartService;
//import service.OrderService;

public class CartController {
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final Scanner scanner = new Scanner(System.in);
    private int customerId;

    public CartController(CartService cartService, OrderRepository orderRepository) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
    }

    public void start() throws SQLException {
        // Ask for Customer ID at the start
        System.out.print("Enter your Customer ID: ");
        customerId = getIntInput();

        while (true) {
            System.out.println("\n🛒 Cart Menu:");
            System.out.println("1. Add Product to Cart");
            System.out.println("2. Remove Product from Cart");
            System.out.println("3. Update Product Quantity");
            System.out.println("4. View Cart");
            System.out.println("5. Clear Cart");
            System.out.println("6. Show Total Price");
            System.out.println("7. Apply Discountt");
            System.out.println("8. Place Order ✅");
            System.out.println("9. Process Payment");
            System.out.println("10. Exit");
            System.out.print("Choose an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> removeProduct();
                case 3 -> updateProductQuantity();
                case 4 -> viewCart();
                case 5 -> clearCart();
                case 6 -> showTotalCartPrice();
                case 7 -> applyDiscount();
                case 8 -> placeOrderWithDiscount();
                case 9 -> processPayment();
                case 10 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("❌ Invalid choice. Try again.");
            }
        }
    }

    private void addProduct() {
        System.out.print("Enter Product ID: ");
        int productId = getIntInput();
        System.out.print("Enter Quantity: ");
        int quantity = getIntInput();

        String result = cartService.addProductToCart(customerId, productId, quantity);
        System.out.println(result);
    }

    private void removeProduct() {
        System.out.print("Enter Product ID to remove: ");
        int productId = getIntInput();

        String result = cartService.removeProductFromCart(customerId, productId);
        System.out.println(result);
    }

    private void clearCart() {
        String result = cartService.clearCart(customerId);
        System.out.println(result);
    }

    private void viewCart() {
        List<CartItem> items = cartService.getCartItems(customerId);
        if (items.isEmpty()) {
            System.out.println("🛒 Your cart is empty.");
        } else {
            System.out.println("\n+------------+------------+");
            System.out.println("| Product ID | Quantity   |");
            System.out.println("+------------+------------+");

            for (CartItem item : items) {
                System.out.printf("| %-10d | %-10d |\n",
                        item.getProductId(), item.getQuantity());
            }

            System.out.println("+------------+------------+");
        }
    }

    private void showTotalCartPrice() throws SQLException {
        double totalPrice = cartService.getTotalCartPrice(customerId);
        if (totalPrice > 0) {
            System.out.println("\n🛒 Total Cart Price: KR" + totalPrice);
        } else {
            System.out.println("\n⚠️ Error: Unable to fetch cart total. Please try again.");
        }
    }

    private void updateProductQuantity() {
        System.out.print("Enter Product ID: ");
        int productId = getIntInput();

        System.out.print("Enter New Quantity: ");
        int newQuantity = getIntInput();

        String response = cartService.updateProductQuantity(customerId, productId, newQuantity);
        System.out.println(response);
    }

    private void applyDiscount() {
        try {
            System.out.print("Enter Discount Percentage: ");
            double discountPercentage = getDoubleInput();

            double totalCartPrice = cartService.getTotalCartPrice(customerId);
            double discountedTotal = cartService.applyDiscount(customerId, discountPercentage);

            System.out.println("Original Total: KR" + totalCartPrice);
            System.out.println("Discounted Total: KR" + discountedTotal);
        } catch (SQLException e) {
            System.out.println("❌ Error applying discount: " + e.getMessage());
        }
    }
    private void placeOrderWithDiscount() throws SQLException {
        System.out.print("Enter Discount Percentage: ");
        double discountPercentage = getDoubleInput();

        int orderId = cartService.placeOrder(customerId, discountPercentage);
        if (orderId > 0) {
            System.out.println("✅ Order placed successfully! Order ID: " + orderId);
        } else {
            System.out.println("❌ Failed to place order. Please try again.");
        }
    }

    private void processPayment() throws SQLException {
        System.out.print("Enter Discount Percentage: ");
        double discountPercentage = getDoubleInput();
        System.out.print("Enter Payment Method: ");
        String paymentMethod = scanner.next();

        boolean paymentSuccess = cartService.processPayment(customerId, paymentMethod, discountPercentage);
        System.out.println(paymentSuccess ? "✅ Payment processed successfully." : "❌ Payment failed.");
    }


    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("❌ Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private double getDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.println("❌ Invalid input. Please enter a valid number.");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}
