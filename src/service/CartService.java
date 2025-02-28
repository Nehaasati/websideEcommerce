package service;

import model.CartItem;
import repository.CartRepository;

import java.util.List;

public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public String addProductToCart(int customerId, int productId, int quantity) {
        if (quantity <= 0) {                                        // prevents negative/zero quantities).
            return "Error: Quantity must be greater than zero.";
        }

        cartRepository.addProductToCart(customerId, productId, quantity);
        return "Product added to cart successfully.";
    }

    public String removeProductFromCart(int customerId, int productId) {
        boolean success = cartRepository.removeProductFromCart(customerId, productId);
        return success ? "Product removed from cart successfully." : "Error: Product not found in cart.";
    }

    public String updateProductQuantity(int customerId, int productId, int newQuantity) {
        if (newQuantity <= 0) {
            return removeProductFromCart(customerId, productId);
        }

        boolean success = cartRepository.updateProductQuantity(customerId, productId, newQuantity);
        return success ? "Product quantity updated successfully." : "Error: Product not found in cart.";
    }

    public List<CartItem> getCartItems(int customerId) {
        return cartRepository.getCartItems(customerId);
    }

    public String clearCart(int customerId) {
        boolean success = cartRepository.clearCart(customerId);
        return success ? "Cart cleared successfully." : "Error: Cart is already empty.";
    }
}
