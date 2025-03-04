package repository;

import model.CartItem;

import java.util.*;

import model.CartItem;
import java.util.*;

public class CartRepository {
    private final Map<Integer, List<CartItem>> cartData = new HashMap<>();

    // Add product to cart
    public boolean addProductToCart(int customerId, int productId, int quantity) {
        cartData.putIfAbsent(customerId, new ArrayList<>());
        cartData.get(customerId).add(new CartItem(customerId, productId, quantity));
        return true;
    }

    // Remove product from cart
    public boolean removeProductFromCart(int customerId, int productId) {
        if (!cartData.containsKey(customerId)) return false;
        return cartData.get(customerId).removeIf(item -> item.getProductId() == productId);
    }

    // Get all cart items for a customer
    public List<CartItem> getCartItems(int customerId) {
        return cartData.getOrDefault(customerId, new ArrayList<>());
    }

    // Clear entire cart for a customer
    public boolean clearCart(int customerId) {
        if (!cartData.containsKey(customerId)) return false;
        cartData.remove(customerId);
        return true;
    }
}
