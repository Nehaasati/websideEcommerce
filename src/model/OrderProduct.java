package model;

public  class OrderProduct {
    private int orderProductId;
    private int orderId;
    private int productId;
    private int quantity;
    private double unitPrice; //rename the price
    // private double price;

<<<<<<< HEAD
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
=======

    private int orderProductId;
    private int orderId;
    private int productId;
    private int quantity;
    private double unit_price;

<<<<<<< HEAD
    // Constructor for insertion (orderProductId auto-generated)
    public OrderProduct(int productId, int quantity, double unit_price) {
        this.productId = productId;
        this.quantity = quantity;
        this.unit_price = unit_price;
       this.orderId = orderId;
=======
        // Constructor for insertion (orderProductId auto-generated)
        public OrderProduct(int productId, int quantity, double unit_price) {
            this.productId = productId;
            this.quantity = quantity;
            this.unit_price = unit_price;
        }

        // Constructor for retrieval
        public OrderProduct(int orderProductId, int orderId, int productId, int quantity, double unit_price) {
            this.orderProductId = orderProductId;
            this.orderId = orderId;
            this.productId = productId;
            this.quantity = quantity;
            this.unit_price = unit_price;
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
        public double getunit_Price() {
            return unit_price;
        }
        public void setunit_Price(double unit_price) {
            this.unit_price = unit_price;
        }

        @Override
        public String toString() {
            return "OrderProduct{" +
                    "orderProductId=" + orderProductId +
                    ", orderId=" + orderId +
                    ", productId=" + productId +
                    ", quantity=" + quantity +
                    ", unit_price=" + unit_price +
                    '}';
        }
>>>>>>> Neha
>>>>>>> origin/master
    }
}




    // Constructor for retrieval
    public OrderProduct(int orderProductId, int orderId, int productId, int quantity, double unit_price) {
        this.orderProductId = orderProductId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unit_price = unit_price;
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
    public double getunit_Price() {
        return unit_price;
    }
    public void setunit_Price(double unit_price) {
        this.unit_price = unit_price;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "orderProductId=" + orderProductId +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", unit_price=" + unit_price +
                '}';
    }
}
