package service;

import model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();

    Optional<Product> getProductById(int id);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    boolean updateProductCategories(int productId, int[] categoryIds);

    void deleteProduct(int id);

    List<Product>searchProductByName(String name);
    List<Product>searchProductByCategory(String categoryName);
    List<Product>searchProductByPriceRange(double minPrice, double maxPrice);
}
