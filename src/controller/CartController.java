package controller;

import java.util.List;
import java.util.Scanner;
import model.CartItem;
import service.CartService;

public class CartController {
    private final CartService cartService;
    private final Scanner scanner = new Scanner(System.in);

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    public void start() {
        int customerId = 1;  // Example customer
        while (true) {
            System.out.println("\n1. Add Product to Cart");
            System.out.println("2. Remove Product from Cart");
            //System.out.println("3. Update Product Quantity");
            System.out.println("3. View Cart");
            System.out.println("4. Clear Cart");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addProduct(customerId);
                case 2 -> removeProduct(customerId);
                // case 3 -> updateProduct(customerId);
                case 3 -> viewCart(customerId);
                case 4 -> clearCart(customerId);
                case 5 -> {
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
            System.out.println("Cart Items: " + items);
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

    private void viewCart(int customerId) {
        List<CartItem> items = cartService.getCartItems(customerId);
        if (items.isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            System.out.println("Cart Items: " + items);
        }
    }*/



