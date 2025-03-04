package service;

import model.Product;
import repository.ProductRepository;

import java.sql.SQLException;
import java.util.Optional;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Check if enough stock is available
    public boolean checkStock(int productId, int quantity) {
        return productRepository.getProductById(productId)
                .map(product -> product.getStockQuantity() >= quantity)
                .orElse(false);
    }

    // Reduce stock when adding product to cart
    public boolean reduceStock(int productId, int quantity) {
        return productRepository.getProductById(productId)
                .filter(product -> product.getStockQuantity() >= quantity) // Prevents negative stock
                .map(product -> productRepository.updateStock(productId, product.getStockQuantity() - quantity))
                .orElse(false);
    }

    // Restore stock when removing product from cart
    public void addStock(int productId, int quantity) {
        productRepository.getProductById(productId)
                .ifPresent(product -> productRepository.updateStock(productId, product.getStockQuantity() + quantity));
    }

    public boolean checkStockAvailability(int productId, int quantity) throws SQLException {
        return productRepository.getStock(productId) >= quantity;
    }

    public void updateStockAfterOrder(int productId, int quantity) throws SQLException {
        int currentStock = productRepository.getStock(productId);
        productRepository.UpdateStock(productId, currentStock - quantity);
    }

    public void restockProduct(int productId, int quantity) throws SQLException {
        int currentStock = productRepository.getStock(productId);
        productRepository.UpdateStock(productId, currentStock + quantity);
    }

    public int getStockStatus(int productId) throws SQLException {
        return productRepository.getStock(productId);
    }
}



