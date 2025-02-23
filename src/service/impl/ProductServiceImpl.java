package service.impl;

import model.Product;
import model.ProductCategory;
import repository.ProductRepository;
import repository.impl.ProductRepositoryImpl;
import service.ProductService;

import java.util.List;
import java.util.Optional;


public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {

        this.productRepository = new ProductRepositoryImpl();
    }

    @Override
    public List<Product> getAllProducts() {

        return productRepository.getAllProducts();
    }

    @Override
    public Product getProductById(int productId) {
        return productRepository.searchProductById(productId)
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

    @Override
    public boolean updateProductCategories(int productId, int[] categoryIds) {
        Optional<Product> optionalProduct = productRepository.searchProductById(productId);
        if (optionalProduct.isEmpty()) {
            return false; // Product not found
        }

        Product product = optionalProduct.get();
        product.getCategories().clear(); // Remove old categories

        for (int categoryId : categoryIds) {
            product.getCategories().add(new ProductCategory(productId, categoryId));
        }

        productRepository.update(product);
        return true; // Update successful
    }
}
