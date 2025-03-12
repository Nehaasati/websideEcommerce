package controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import model.CartItem;
import model.Product;
import repository.impl.OrderRepository;
import repository.ProductRepository;
import service.CartService;

public class CartController {
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final Scanner scanner = new Scanner(System.in);
    private int customerId;

    public CartController(CartService cartService, OrderRepository orderRepository, ProductRepository productRepository) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public void start() throws SQLException {
        // Ask for Customer ID at the start
        System.out.print("Enter your Customer ID: ");
        customerId = getIntInput();

        while (true) {
            System.out.println("\n🛒 Cart Menu:");
            System.out.println("🛒1. Add Product to Cart");
            System.out.println("🛒2. Remove Product from Cart");
            System.out.println("🛒3. Update Product Quantity");
            System.out.println("🛒4. View Cart");
            System.out.println("🛒5. Clear Cart");
            System.out.println("✅6. Order Management");
            System.out.print("Choose an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> removeProduct();
                case 3 -> updateProductQuantity();
                case 4 -> viewCart();
                case 5 -> clearCart();
                case 6 -> orderManagementMenu();
                default -> System.out.println("❌ Invalid choice. Try again.");
            }
        }
    }

    private void orderManagementMenu() throws SQLException {
        while (true) {
            System.out.println("\n📦 Order Management Menu:");
            System.out.println("📦1. Show Total Price");
            System.out.println("📦2. Apply Discount");
            System.out.println("📦3. Place Order");
            System.out.println("📦4. Process Payment");
            System.out.println("📦5. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1 -> showTotalCartPrice();
                case 2 -> applyDiscount();
                case 3 -> placeOrderWithDiscount();
                case 4 -> processPayment();
                case 5 -> {
                    System.out.println("Returning to Main Menu...");
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

    private void viewCart() throws SQLException {
        List<CartItem> items = cartService.getCartItems(customerId);
        if (items.isEmpty()) {
            System.out.println("🛒 Your cart is empty.");
        } else {
            System.out.println("\n+------------+----------------------+------------+------------+-------------+");
            System.out.println("| Product ID | Product Name         | Quantity   | Price (KR) | Total (KR)  |");
            System.out.println("+------------+----------------------+------------+------------+-------------+");

            for (CartItem item : items) {
                Product product = productRepository.getProductById(item.getProductId());
                if (product != null) {
                    double productTotalPrice = product.getPrice() * item.getQuantity();
                    System.out.printf("| %-10d | %-20s | %-10d | %-10.2f | %-11.2f |\n",
                            item.getProductId(),
                            product.getName(),
                            item.getQuantity(),
                            product.getPrice(),
                            productTotalPrice);
                }
            }

            System.out.println("+------------+----------------------+------------+------------+-------------+");
        }
    }

    private void showTotalCartPrice() throws SQLException {
        List<CartItem> items = cartService.getCartItems(customerId);
        if (items.isEmpty()) {
            System.out.println("🛒 Your cart is empty.");
            return;
        }

        // Display cart details in a table format
        System.out.println("\n+------------+----------------------+------------+------------+-------------+");
        System.out.println("| Product ID | Product Name         | Quantity   | Price (KR) | Total (KR)  |");
        System.out.println("+------------+----------------------+------------+------------+-------------+");

        double totalCartPrice = 0;
        for (CartItem item : items) {
            Product product = productRepository.getProductById(item.getProductId());
            if (product != null) {
                double productTotalPrice = product.getPrice() * item.getQuantity();
                totalCartPrice += productTotalPrice;

                System.out.printf("| %-10d | %-20s | %-10d | %-10.2f | %-11.2f |\n",
                        item.getProductId(),
                        product.getName(),
                        item.getQuantity(),
                        product.getPrice(),
                        productTotalPrice);
            }
        }

        System.out.println("+------------+----------------------+------------+------------+-------------+");
        System.out.printf("| %-46s | %-11.2f |\n", "Total Cart Price:", totalCartPrice);
        System.out.println("+---------------------------------------------------------------+-------------+");
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

            // Display order details
            List<CartItem> items = cartService.getCartItems(customerId);
            if (!items.isEmpty()) {
                System.out.println("\n📦 Order Details:");
                System.out.println("+------------+----------------------+------------+------------+-------------+");
                System.out.println("| Product ID | Product Name         | Quantity   | Price (KR) | Total (KR)  |");
                System.out.println("+------------+----------------------+------------+------------+-------------+");

                double totalOrderPrice = 0;
                for (CartItem item : items) {
                    Product product = productRepository.getProductById(item.getProductId());
                    if (product != null) {
                        double productTotalPrice = product.getPrice() * item.getQuantity();
                        totalOrderPrice += productTotalPrice;

                        System.out.printf("| %-10d | %-20s | %-10d | %-10.2f | %-11.2f |\n",
                                item.getProductId(),
                                product.getName(),
                                item.getQuantity(),
                                product.getPrice(),
                                productTotalPrice);
                    }
                }

                System.out.println("+------------+----------------------+------------+------------+-------------+");
                System.out.printf("| %-46s | %-11.2f |\n", "Total Order Price:", totalOrderPrice);
                System.out.println("+---------------------------------------------------------------+-------------+");
            }
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