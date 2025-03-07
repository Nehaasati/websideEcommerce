package controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import model.CartItem;
import service.CartService;

public class CartController {
    private final CartService cartService;
    private final Scanner scanner = new Scanner(System.in);
    private int customerId;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    public void start() throws SQLException {
        // Ask for Customer ID at the start
        System.out.print("Enter your Customer ID: ");
        customerId = scanner.nextInt();
        while (true) {
            System.out.println("\n 🛒1. Add Product to Cart");
            System.out.println("🛒2. Remove Product from Cart");
            //System.out.println("3. Update Product Quantity");
            System.out.println("🛒3. View Cart");
            System.out.println("🛒4. Clear Cart");
            System.out.println("🛒5  Show total price");

            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addProduct(customerId);
                case 2 -> removeProduct(customerId);
                // case 3 -> updateProduct(customerId);
                case 3 -> viewCart(customerId);
                case 4 -> clearCart(customerId);
                case 5 -> showTotalCartPrice();
                case 6 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void addProduct(int customerId) {
        System.out.print("Enter Product ID: ");
        int productId = scanner.nextInt();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();

        System.out.println(cartService.addProductToCart(customerId, productId, quantity));
    }

    private void removeProduct(int customerId) {
        System.out.print("Enter Product ID to remove: ");
        int productId = scanner.nextInt();
        System.out.println(cartService.removeProductFromCart(customerId, productId));
    }

    private void clearCart(int customerId) {
        System.out.println(cartService.clearCart(customerId));
    }


    private void viewCart(int customerId) {
        List<CartItem> items = cartService.getCartItems(customerId);
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
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

    public void showTotalCartPrice() throws SQLException {
        System.out.print("Enter Customer ID: ");
        int customerId = scanner.nextInt();

        double totalPrice = cartService.getTotalCartPrice(customerId);
        if (totalPrice > 0) {
            System.out.println("\n🛒 Total Cart Price: KR"  + totalPrice);
        } else {
            System.out.println("\n⚠️ Error: Unable to fetch cart total. Please try again.");
        }
    }
}
/*
    private void updateProduct(int customerId) {
        System.out.print("Enter Product ID: ");
        int productId = scanner.nextInt();
        System.out.print("Enter New Quantity: ");
        int quantity = scanner.nextInt();
        System.out.println(cartService.updateProductQuantity(customerId, productId, quantity));
    }
}
    private void viewCart(int customerId) {
        List<CartItem> items = cartService.getCartItems(customerId);
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            System.out.println("Cart Items: " + items);
        }

} */

