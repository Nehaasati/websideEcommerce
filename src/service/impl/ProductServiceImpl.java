package service.impl;

import model.Category;
import model.Product;
import model.ProductCategory;
import repository.CategoryRepository;
import repository.ProductCategoryRepository;
import repository.ProductRepository;
import repository.impl.ProductRepositoryImpl;
import service.ProductService;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;


public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
   private final ProductCategoryRepository productCategoryRepository;
   private final CategoryRepository categoryRepository;


    public ProductServiceImpl(ProductRepository productRepository,
                              ProductCategoryRepository productCategoryRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public Optional<Product> getProductById(int productId) {
        return productRepository.searchProductById(productId);
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

    @Override
    public List<Product> searchProductByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        return productRepository.searchProductByName(name);
    }

    @Override
    public List<Product> searchProductByCategory(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        return productRepository.searchProductByCategory(categoryName);
    }

    @Override
    public List<Product> searchProductByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
            throw new IllegalArgumentException("Invalid price range");
        }
        return productRepository.searchProductByPriceRange(minPrice, maxPrice);
    }

    @Override
    public boolean updateProductCategories(int productId, int[] categoryIds) {
        Optional<Product> optionalProduct = productRepository.searchProductById(productId);
        if (optionalProduct.isEmpty()) {
            return false; // Product not found
        }
        // Clear old categories
        productCategoryRepository.deleteCategoriesByProductId(productId);

        // Add new categories
        for (int categoryId : categoryIds) {
            Optional<Category> category = categoryRepository.searchCategory(categoryId);
            if (category.isPresent()) {
                // Create product-category relation
                productCategoryRepository.addProductCategory(new ProductCategory(productId, categoryId));
            }
        }

        return true; // Successfully updated
    }


    private void validateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("Product price must be positive");
        }
        if (product.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
    }
}

