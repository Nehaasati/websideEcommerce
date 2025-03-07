package repository;

import model.CartItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CartItem;
import util.SqliteConnectionManger;

import java.util.*;

public class CartRepository {
    private static final Logger LOGGER = Logger.getLogger(CartRepository.class.getName());

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
    // Update product quantity
    public boolean updateProductQuantity(int customerId, int productId, int newQuantity) {
        if (!cartData.containsKey(customerId)) return false;

        List<CartItem> items = cartData.get(customerId);
        for (CartItem item : items) {
            if (item.getProductId() == productId) {
                if (newQuantity <= 0) {
                    items.remove(item); // Remove product if quantity is 0
                } else {
                    item.setQuantity(newQuantity); // Update quantity
                }
                return true;
            }
        }
        return false;
    }
    // Replace a product in the cart
    public boolean updateProductInCart(int customerId, int oldProductId, int newProductId, int newQuantity) {
        if (!cartData.containsKey(customerId)) return false;

        // Remove old product
        removeProductFromCart(customerId, oldProductId);

        // Add new product
        return addProductToCart(customerId, newProductId, newQuantity);
    }


    /**
     * Calculates the total price of all items in a customer's cart.
     * @param customerId The ID of the customer whose cart total is to be calculated.
     * @return The total price of the cart.
     */
    // Get all cart items

}

