package service;

import java.util.List;

public interface IOrderService {
   // int placeOrder(int customerId);
    List<String> getAllOrders();
    boolean cancelOrder(int orderId);
    boolean updateOrder(int orderId, int customerId);
    List<String> getOrderHistory(int customerId);

    //double applyDiscount(int discountOrderId, double discountPercentage);
   // boolean processPayment(int orderId,String paymentMethod, double amount);
}

