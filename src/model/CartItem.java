package model;

public class CartItem {
    private final int productId;          //Once set, it cannot be changed.
    private int quantity;
    private int customerId;
    public CartItem(int customerId,int productId, int quantity) {
        //if (quantity <= 0) {                                   // Ensuring quantity is valid
        //    throw new IllegalArgumentException("Quantity must be greater than zero.");
        // }
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{productId=" + productId + ", quantity=" + quantity + "}";
    }
}
