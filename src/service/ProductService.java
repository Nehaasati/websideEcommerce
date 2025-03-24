package service;


import model.Product;
import repository.ProductRepository;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;



public class ProductService {
    private ProductRepository productRepo;
    private static final int LOW_STOCK_THRESHOLD = 10;

    public ProductService(ProductRepository productRepo) {

        this.productRepo = productRepo;
    }


    // FOR CUSTOMER PURPOSE
    // Product Retrieval Methods
    public List<Product> getAllProducts() {
        try {
            return productRepo.findAllProducts();
        } catch (SQLException e) {
            handleDatabaseError(e);
            return Collections.emptyList();
        }
    }

    public Product getProductById(int productId) {
        try {
            return productRepo.getProductById(productId);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving product with ID " + productId, e);
        }
    }



    // Search with filtering
    public List<Product> searchProductsByName(String name) {
        try {
            return productRepo.findProductByName(name);
        } catch (SQLException e) {
            handleDatabaseError(e);
            return Collections.emptyList();
        }
    }

    public List<Product> searchProductsByCategory(int categoryId) {
        try {
            return productRepo.findProductByCategory(categoryId);
        } catch (SQLException e) {
            handleDatabaseError(e);
            return Collections.emptyList();
        }
    }

    public List<Product> searchProductsByPriceRange(double min, double max) {
        // Validate input parameters
        if (min < 0) {
            throw new IllegalArgumentException("Error: Price cannot be negative");
        }
        if (max < min) {
            throw new IllegalArgumentException(
                    String.format("Error: Maximum price (%.2f) must be >= minimum price (%.2f)", max, min)
            );
        }

        try {
            return productRepo.findProductsByPriceRange(min, max);
        } catch (SQLException e) {
            handleDatabaseError(e);
            return Collections.emptyList();
        }
    }

    public Product getProductDetails(int productId) {
        try {
            return productRepo.findProductById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Product Not found"));
        } catch (SQLException e) {
            throw new RuntimeException("Database error" + e.getMessage());
        }
    }

    public double getProductPrice(int productId) {
        try {
            return productRepo.findProductById(productId)
                    .map(Product::getPrice)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    // 2. Stock Availability Methods----INVENTORY MANAGEMENT

    public void updateProductPrice(int productId, double newPrice) {    // FINANCE MANAGEMENT PART supervised by Admin
        if (newPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        try {
            productRepo.updateProductPrice(productId, newPrice);
        } catch (SQLException e) {
            handleDatabaseError(e);
            throw new RuntimeException("Failed to update product price", e);
        }
    }

    // Get exact stock quantity
    public int getStockStatus(int productId) {
        try{
            return productRepo.findProductById(productId)
                    .map(Product ::getStockQuantity)
                    .orElseThrow(() -> new IllegalArgumentException("Product Not found"));
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    // Check if stock meets required quantity
    public boolean checkStockAvailability(int productId, int quantity) {
        try {
            return productRepo.findProductById(productId)
                    .map(p -> p.getStockQuantity() >= quantity)
                    .orElse(false);
        } catch (SQLException e) {
            handleDatabaseError(e);
            return false;
        }
    }
    // 3. Stock Update Methods

    // Generic stock adjustment method (internal)
    private void adjustStock(int productId, int adjustment) {
        try {
           productRepo.updateStock(productId, adjustment);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to adjust stock: " + e.getMessage());
        }
    }

    // Reduce stock with validation
    public boolean reduceStock(int productId, int quantity) {
        try{
            if(!checkStockAvailability(productId, quantity)) return false;
            productRepo.updateStock(productId, -quantity);
            return true;
        } catch (SQLException e) {
            handleDatabaseError(e);
            return false;
        }
    }

    // These methods are not directly used for cart management
// but still might be useful for admin functionality

    // Get products with low stock for inventory management
    public List<Product> getLowStockProducts() {
        try{
            return productRepo.findAllProducts().stream()
                    .filter(p -> p.getStockQuantity() < LOW_STOCK_THRESHOLD)
                    .toList();
        } catch (SQLException e) {
            handleDatabaseError(e);
            return Collections.emptyList();
        }
    }

    // Restock product (for admin operations)
    public void restockProduct(int productId, int quantity) {
        adjustStock(productId, quantity);
    }

    //public void restockProduct(int productId, int quantity) {
       /* try{
            repository.updateStock(productId, quantity);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to restock product: " + e.getMessage());
        }
   }*/

    // Check if product has any stock
    public boolean isProductInStock(int productId) {
        try {
            return productRepo.findProductById(productId)
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

 /*public String getProductNameById(int productId) {                              // connect cart for name of product Totalcartprice
        try {
            return repository.findProductById(productId)
                    .map(Product::getName)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }*/

// 3. Error Handling:
// - Catches SQLException, logs detailed messages (message, SQLState, error code).
// - Rethrows RuntimeException to propagate critical failures to higher layers.

// 4. Validation:
// - Validates product price is not negative before updating.
// - Checks stock availability before reducing stock.
// - Ensures product exists before performing operations.



// COMMENT OUT - Redundant with reduceStock
// Update stock after Order
    /*public void updateStockAfterOrder( int productId, int quantity) {
        try {
            repository.updateStock(productId, -quantity);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update stock: " + e.getMessage());
        }
    }*/

// Add stock when removing from cart
  /*  public void addStock(int productId, int quantity) {
        try {
            repository.updateStock(productId, quantity);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add stock: " + e.getMessage());
        }
    }*
    // Legacy method - should be removed or merged with reduceStock
public void updateStockAfterOrder(int productId, int quantity) {
    reduceStock(productId, quantity);
}*/


