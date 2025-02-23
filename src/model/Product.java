package model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int productId;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private Manufacturer manufacturers;  // Represents the manufacturer of the product
    private List<ProductCategory> categories = new ArrayList<>();          // Categories associated with the product

    public Product() {}  // a no-arg constructor needed for the repository

    public Product(int productId, String name, String description, double price, int stockQuantity) {
            this.productId = productId;
            this.name = name;
            this.description = description;
            this.price = price;
            this.stockQuantity = stockQuantity;
            this.manufacturers = manufacturers;
           // this.categories = categories;
    }
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Manufacturer getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(Manufacturer manufacturers) {
        this.manufacturers = manufacturers;
    }

    public List<ProductCategory> getCategories() {
        if (categories == null) {
            categories = new ArrayList<>();
        }
        return categories;
    }
    public void setCategories(List<ProductCategory> categories) {
        this.categories = categories != null ? categories : new ArrayList<>();
    }


    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", manufacturer=" + (manufacturers != null ? manufacturers.getName() : "N/A") +
                ", categories=" + (categories != null ? categories.size() : 0) +
                '}';

    }
}


