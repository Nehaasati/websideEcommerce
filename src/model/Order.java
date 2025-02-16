package model;

public class Order {
    private int orderId;
    private int customerid;
    //private String status;
    //private double price;
    private int order_date;

    public Order(int orderId, int customerid, int order_date) {
        this.orderId = orderId;
        this.customerid = customerid;
        this.order_date= order_date;

    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public int getOrder_date() {
        return order_date;
    }

    public void setOrder_date(int order_date) {
        this.order_date = order_date;
    }

    @Override
    public String toString() {
        return "Order.order{" +
                "ordertId=" + orderId +
                ",customerid='" + customerid + '\'' +
                ", order_date='" + order_date + '\'' +
                '}';
    }
}

