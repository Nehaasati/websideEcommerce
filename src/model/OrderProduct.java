package model;

public  class OrderProduct {
    private int orderProductId;
    private int orderId;
    private int productId;
    private int quantity;
    private double unitPrice; //rename the price
    // private double price;

    // Constructor for insertion (w/o orderProductId)
    public OrderProduct(int orderId, int productId, int quantity, double unitPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Constructor for retrieving (with orderProductId)
    public OrderProduct(int orderProductId, int orderId, int productId, int quantity, double unitPrice) {
        this.orderProductId = orderProductId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        // this.price = price;
    }

    // Getters and Setters
    public int getOrderProductId() {

        return orderProductId;
    }

    public void setOrderProductId(int orderProductId) {

        this.orderProductId = orderProductId;
    }

    public int getOrderId() {

        return orderId;
    }

    public void setOrderId(int orderId) {

        this.orderId = orderId;
    }

    public int getProductId() {

        return productId;
    }

    public void setProductId(int productId) {

        this.productId = productId;
    }

    public int getQuantity() {

        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
        /* public double getPrice() {
            return price;
        }
        public void setPrice(double price) {
            this.price = price;
        }*/

    @Override
    public String toString() {
        return "OrderProduct{" +
                "orderProductId=" + orderProductId +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}




