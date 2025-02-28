package model;
import java.sql.Date;

import java.sql.Date;

public class Order {
    private int orderId;
    private int customerId;
    private Date order_Date;

    // Constructor for insertion (orderId auto-generated)
    public Order(int customerId) {
        this.customerId = customerId;
        //this.order_Date = order_Date;
    }

    // Constructor for retrieval
    public Order(int orderId, int customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
       // this.order_Date = order_Date;
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
    public Date getOrder_Date() {
        return order_Date;
    }
    public void setOrder_Date(Date orderDate) {
        this.order_Date = order_Date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", order_Date=" + order_Date +
                '}';
    }
}
