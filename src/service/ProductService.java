package service;

import model.Product;
<<<<<<< HEAD
import repository.IOrderRepository;
import repository.impl.OrderRepository;

=======
>>>>>>> origin/master
import java.util.List;
import java.util.logging.Logger;

public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(int id);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    boolean updateProductCategories(int productId, int[] categoryIds);

    void deleteProduct(int id);




    private void validateProduct(Product product) {
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("Product price must be positive");
        }
        if (product.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        // NEW: Validate manufacturer
        if (product.getManufacturers() == null || product.getManufacturers().getManufacturerId() <= 0) {
            throw new IllegalArgumentException("Manufacturer is required");
        }
    }

}
  /*  private final ProductRepository productRepository;

    public ProductService(Connection connection) {
        this.productRepository = new ProductRepository(connection);
    }

    public List<Product> getAllProducts() throws SQLException{
        return productRepository.getAllProducts();
    }
}*/
