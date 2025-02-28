package repository;

import model.CartItem;

import java.util.*;

public class CartRepository {

    // Stores cart data: { customerId → { productId → CartItem } }
    private final Map<Integer, Map<Integer, CartItem>> cartStorage = new HashMap<>();



    // Adds a product to the cart
    public synchronized void addProductToCart(int customerId, int productId, int quantity) {
        cartStorage.putIfAbsent(customerId, new HashMap<>());      // Create cart if not present
        Map<Integer, CartItem> cart = cartStorage.get(customerId);

        cart.compute(productId, (key, item) -> {                     //To add/increase quantity efficiently.
            if (item == null) return new CartItem(productId, quantity);
            item.setQuantity(item.getQuantity() + quantity);        // Increase quantity if item exists
            return item;
        });
    }

    // Removes a product from the cart
    public synchronized boolean removeProductFromCart(int customerId, int productId) {
        return cartStorage.containsKey(customerId) && cartStorage.get(customerId).remove(productId) != null;
    }

    // Updates the quantity of an existing product in the cart
    public synchronized boolean updateProductQuantity(int customerId, int productId, int newQuantity) {
        if (!cartStorage.containsKey(customerId) || !cartStorage.get(customerId).containsKey(productId)) {
            return false;
        }

        if (newQuantity <= 0) {                         // If quantity is 0, remove the product
            removeProductFromCart(customerId, productId);
        } else {
            cartStorage.get(customerId).get(productId).setQuantity(newQuantity);
        }
        return true;
    }

    // Retrieves all cart items for a given customer

    public synchronized List<CartItem> getCartItems(int customerId) {
        return cartStorage.getOrDefault(customerId, Collections.emptyMap())
                .values().stream().toList();
    }


    // Clears the cart for a customer
    public synchronized boolean clearCart(int customerId) {
        return cartStorage.remove(customerId) != null;
    }
}
