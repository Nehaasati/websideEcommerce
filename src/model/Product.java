package model;

import java.util.List;
import java.util.ArrayList;



public class Product {

    private final int productId;
    private final String name;
    private  String description;
    private double price;
    private int stockQuantity;
    private List<Integer> categoryIds = new ArrayList<>();
    private  int manufacturerId;

   // Main Constructor
    public Product(int productId, int manufacturerId, String name, String description,
                   double price, int stockQuantity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.productId = productId;
        this.manufacturerId = manufacturerId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // Overload construtor
      public Product( int productId, String name){
            this.productId = productId;
            this.name = name;
      }





        // Getters
    public int getProductId() { return productId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public List<Integer> getCategoryIds() { return categoryIds; }
    public void addCategoryId(int categoryId) { categoryIds.add(categoryId); }
    public int getManufacturerId() { return manufacturerId; }

        // setters only
    public void setStockQuantity(int quantity) { stockQuantity = quantity; }
}



