package model;

public class OrderProduct {
    private int orderProductId;
    private int orderId;
    private int productId;
    private int quantity;
    private double unit_price;

    public OrderProduct(int orderProductId, int orderId, int productId, int quantity, double unitPrice) {
        this.orderProductId = orderProductId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unit_price = unit_price;
    }

    public int getOrderProductId() { return orderProductId; }
    public int getOrderId() { return orderId; }
    public int getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public double getunit_price() { return unit_price; }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "ID=" + orderProductId +
                ", Order ID=" + orderId +
                ", Product ID=" + productId +
                ", Quantity=" + quantity +
                ", unit Price=" + unit_price +
                '}';
    }
}
