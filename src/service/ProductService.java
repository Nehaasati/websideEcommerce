package service;


import model.Product;
import repository.ProductRepository;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class ProductService {
    private final ProductRepository repository;
    private static final int LOW_STOCK_THRESHOLD = 10;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // Product Retrieval Methods
    public List<Product> getAllProducts() {
        try {
            return repository.findAllProducts();
        } catch (SQLException e) {
            handleDatabaseError(e);
            return Collections.emptyList();
        }
    }

    // Search with filtering
    public List<Product> searchProductsByName(String name) {
        try {
            return repository.findProductByName(name);
        } catch (SQLException e) {
            handleDatabaseError(e);
            return Collections.emptyList();
        }
    }

    public List<Product> searchProductsByCategory(int categoryId) {
        try {
            return repository.findProductByCategory(categoryId);
        } catch (SQLException e) {
            handleDatabaseError(e);
            return Collections.emptyList();
        }
    }

    // Modified search methods to use category IDs list
    public List<Product> getProductsInCategory(int categoryId) {
       return searchProductsByCategory(categoryId);
    }

    public List<Product> searchProductsByPriceRange(double min, double max) {
        try {
            return repository.findProductsByPriceRange(min, max).stream()
                    .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            handleDatabaseError(e);
            return Collections.emptyList();
        }
    }

    public Product getProductDetails(int productId) {
        try {
            return repository.findProductById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product Not found"));
        } catch (SQLException e) {
            throw new RuntimeException("Database error" + e.getMessage());
        }
    }

    public double getProductPrice(int productId) {
        try {
            return repository.findProductById(productId)
                    .map(Product::getPrice)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

   // 2. Stock Availability Methods

    public int getStockStatus(int productId) {
        try{
            return repository.findProductById(productId)
                    .map(Product ::getStockQuantity)
                    .orElseThrow(() -> new IllegalArgumentException("Product Not found"));
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    // Check stock availability
    public boolean checkStockAvailability(int productId, int quantity) {
        try {
            return repository.findProductById(productId)
                    .map(p -> p.getStockQuantity() >= quantity)
                    .orElse(false);
        } catch (SQLException e) {
            handleDatabaseError(e);
            return false;
        }
    }

    public List<Product> getLowStockProducts() {
        try{
            return repository.findAllProducts().stream()
                    .filter(p -> p.getStockQuantity() < LOW_STOCK_THRESHOLD)
                    .toList();
        } catch (SQLException e) {
            handleDatabaseError(e);
            return Collections.emptyList();
        }
    }

    // 3. Stock Update Methods

    // Update stock after Order
    public void updateStockAfterOrder( int productId, int quantity) {
        try {
            repository.updateStock(productId, -quantity);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update stock: " + e.getMessage());
        }
    }

    public boolean reduceStock(int productId, int quantity) {
        try{
            if(!checkStockAvailability(productId, quantity)) return false;
            repository.updateStock(productId, -quantity);
            return true;
        } catch (SQLException e) {
            handleDatabaseError(e);
            return false;
        }
    }

    // Restore stock when removing product from cart
    public void addStock(int productId, int quantity) {
        try {
            repository.updateStock(productId, quantity);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add stock: " + e.getMessage());
        }
    }

   public void restockProduct(int productId, int quantity) {
        try{
            repository.updateStock(productId, quantity);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to restock product: " + e.getMessage());
        }
   }

   public boolean isProductInStock(int productId) {
        try {
            return repository.findProductById(productId)
                    .map(p -> p.getStockQuantity() > 0)
                    .orElse(false);
        } catch (SQLException e) {
            handleDatabaseError(e);
            return false;
        }
    }

    private void handleDatabaseError(SQLException e) {
        System.err.println("Database error: " + e.getMessage());
        System.err.println("SQL State: " + e.getSQLState());
        System.err.println("Error Code: " + e.getErrorCode());
    }

}
