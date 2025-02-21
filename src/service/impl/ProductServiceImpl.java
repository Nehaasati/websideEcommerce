package service.impl;

import repository.ProductRepository;
import service.ProductService;
import model.Product;
import java.util.List;


public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public Product getProductById(int productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
    }

    @Override
    public Product createProduct(Product product) {
        validateProduct(product);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        validateProduct(product);
        productRepository.update(product);
        return product;
    }

    @Override
    public void deleteProduct(int productId) {
        productRepository.delete(productId);
    }

    private void validateProduct(Product product) {
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("Product price must be positive");
        }
        if (product.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
    }
}
