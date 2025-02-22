package model;

import java.sql.Date;

public class Order {
    private int orderId;
    private int customerId;
    private Date orderDate;

    // Constructor for insertion (orderId auto-generated)
    public Order(int customerId, Date orderDate) {
        this.customerId = customerId;
        this.orderDate = orderDate;
    }

    // Constructor for retrieval
    public Order(int orderId, int customerId, Date orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", orderDate=" + orderDate +
                '}';
    }
}


