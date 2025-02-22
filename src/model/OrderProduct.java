package model;

public class OrderProduct {

        private int orderProductId;
        private int orderId;
        private int productId;
        private int quantity;
        private double price;

        // Constructor for insertion (orderProductId auto-generated)
        public OrderProduct(int productId, int quantity, double price) {
            this.productId = productId;
            this.quantity = quantity;
            this.price = price;
        }

        // Constructor for retrieval
        public OrderProduct(int orderProductId, int orderId, int productId, int quantity, double price) {
            this.orderProductId = orderProductId;
            this.orderId = orderId;
            this.productId = productId;
            this.quantity = quantity;
            this.price = price;
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
        public double getPrice() {
            return price;
        }
        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "OrderProduct{" +
                    "orderProductId=" + orderProductId +
                    ", orderId=" + orderId +
                    ", productId=" + productId +
                    ", quantity=" + quantity +
                    ", price=" + price +
                    '}';
        }
    }

