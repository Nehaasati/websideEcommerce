package service;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import model.CartItem;
import repository.CartRepository;
import repository.ProductRepository;
import java.util.List;

public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartService(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    // Add product to cart
    public String addProductToCart(int customerId, int productId, int quantity) {
        if (quantity <= 0) return "Error: Quantity must be greater than zero.";

        if (!productService.checkStock(productId, quantity)) return "Error: Not enough stock available.";

        // Deduct stock and add to cart
        if (cartRepository.addProductToCart(customerId, productId, quantity)) {
            productService.reduceStock(productId, quantity);
            return "✅ Product added to cart successfully!";
        }
        return "❌ Error: Failed to add product to cart.";
    }

    // Remove product from cart
    public String removeProductFromCart(int customerId, int productId) {
        List<CartItem> cartItems = cartRepository.getCartItems(customerId);
        int quantity = cartItems.stream()
                .filter(item -> item.getProductId() == productId)
                .mapToInt(CartItem::getQuantity)
                .sum();

        if (cartRepository.removeProductFromCart(customerId, productId)) {
            productService.addStock(productId, quantity); // Restore stock
            return "✅ Product removed from cart successfully!";
        }
        return "❌ Error: Product not in cart.";
    }

    // Get all cart items
    public List<CartItem> getCartItems(int customerId) {
        return cartRepository.getCartItems(customerId);
    }

    // Clear entire cart
    public String clearCart(int customerId) {
        if (cartRepository.clearCart(customerId)) {
            return "🛒 Cart cleared successfully!";
        }
        return "❌ Error: Cart is already empty.";
    }
}
