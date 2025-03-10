package controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.CartItem;
import repository.impl.OrderRepository;
import service.CartService;

public class CartController {
    private static final Logger LOGGER = Logger.getLogger(CartController.class.getName());
    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final Scanner scanner = new Scanner(System.in);
    //private int customerId;

    public CartController(CartService cartService, OrderRepository orderRepository) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        //this.customerId = customerId;
    }

    public void start(int customerId) {
       /* // Ask for Customer ID at the start
        System.out.print("Enter your Customer ID: ");
        customerId = getIntInput();*/   // Now no need to ask customerId in cart as it will be declared in customerController so that the customer
        // will be logged in throughout the application until logout
        while (true) {
            System.out.println("\n🛒  === CART MANAGEMENT ===");
            System.out.println("1. Add Product to Cart");
            System.out.println("2. Remove Product from Cart");
            System.out.println("3. Update Product Quantity");
            System.out.println("4. View Cart");
            System.out.println("5. Clear Cart");
            System.out.println("6. Show Total Price");
            System.out.println("7. Apply Discount");
            System.out.println("8. Place Order ✅");
            System.out.println("9. Process Payment");
            System.out.println("10. Exit");
            System.out.print("Choose an option: ");

            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("🚨 Please enter a valid option number");
                    continue;
                }
                int choice = Integer.parseInt(input);
                switch (choice) {
                    case 1 -> addProduct(customerId);
                    case 2 -> removeProduct(customerId);
                    case 3 -> updateProductQuantity(customerId);
                    case 4 -> viewCart(customerId);
                    case 5 -> clearCart(customerId);
                    case 6 -> showTotalCartPrice(customerId);
                    case 7 -> applyDiscount(customerId);
                    case 8 -> placeOrderWithDiscount(customerId);
                    case 9 -> processPayment(customerId);
                    case 10 -> {
                        return;
                    }
                    default -> System.out.println("Exiting...");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number between 1-10");
                LOGGER.log(Level.WARNING, "Invalid menu input", e);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Cart operation error", e);
                System.out.println("🚨 Error processing cart operation");
            }
               /* } catch(Exception e){
                    LOGGER.log(Level.SEVERE, "Cart operation error", e);
                    System.out.println("🚨 Error processing cart operation");
            }*/
        }
    }


    private void addProduct(int customerId) {
        System.out.print("Enter Product ID: ");
        int productId = getIntInput();
        System.out.print("Enter Quantity: ");
        int quantity = getIntInput();

        String result = cartService.addProductToCart(customerId, productId, quantity);
        System.out.println(result);
    }

    private void removeProduct(int customerId) {
        System.out.print("Enter Product ID to remove: ");
        int productId = getIntInput();

        String result = cartService.removeProductFromCart(customerId, productId);
        System.out.println(result);
    }

    private void clearCart(int customerId) {
        String result = cartService.clearCart(customerId);
        System.out.println(result);
    }

    private void viewCart(int customerId) {
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

    private void showTotalCartPrice(int customerId) throws SQLException {
        double totalPrice = cartService.getTotalCartPrice(customerId);
        if (totalPrice > 0) {
            System.out.println("\n🛒 Total Cart Price: KR" + totalPrice);
        } else {
            System.out.println("\n⚠️ Error: Unable to fetch cart total. Please try again.");
        }
    }

    private void updateProductQuantity(int customerId) {
        System.out.print("Enter Product ID: ");
        int productId = getIntInput();

        System.out.print("Enter New Quantity: ");
        int newQuantity = getIntInput();

        String response = cartService.updateProductQuantity(customerId, productId, newQuantity);
        System.out.println(response);
    }

    private void applyDiscount(int customerId) {
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

    private void placeOrderWithDiscount(int customerId) throws SQLException {
        System.out.print("Enter Discount Percentage: ");
        double discountPercentage = getDoubleInput();

        int orderId = cartService.placeOrder(customerId, discountPercentage);
        if (orderId > 0) {
            System.out.println("✅ Order placed successfully! Order ID: " + orderId);
        } else {
            System.out.println("❌ Failed to place order. Please try again.");
        }
    }

    private void processPayment(int customerId) throws SQLException {
        System.out.print("Enter Discount Percentage: ");
        double discountPercentage = getDoubleInput();

        System.out.print("Enter Payment Method: ");
        String paymentMethod = scanner.nextLine();

        boolean paymentSuccess = cartService.processPayment(customerId, paymentMethod, discountPercentage);
        System.out.println(paymentSuccess ? "✅ Payment processed successfully." : "❌ Payment failed.");
    }


    private int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid number:");
            }
        }
    }

       /* while (!scanner.hasNextInt()) {
            System.out.println("❌ Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }*/

    private double getDoubleInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid number:");
            }
        }
    }
       /* while (!scanner.hasNextDouble()) {
            System.out.println("❌ Invalid input. Please enter a valid number.");
            scanner.next();
        }
        return scanner.nextDouble();
    }*/
}