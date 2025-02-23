package repository;
import model.Product;


import java.util.Optional;
import java.util.List;


public interface ProductRepository {
    Optional<Product> searchProductById(int productId);

    List<Product> getAllProducts();

    Product save(Product product);

    void update(Product product);

    void delete(int productId);

    List<Product> searchProductByName(String name);
    List<Product> searchProductByCategory(String categoryName);
    List<Product> searchProductByPriceRange( double minPrice, double maxPrice);
}



