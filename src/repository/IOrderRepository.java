package repository;

import java.util.List;

public interface IOrderRepository {

    //double applyDiscount(int orderId, double discountPercentage);
    List<String> getAllOrders();
    boolean cancelOrder(int orderId);
    boolean updateOrder(int orderId, int customerId);
    List<String> getOrderHistory(int customerId);
    //boolean processPayment(int orderId,String paymentMethod, double amount);

    //int createOrder(int customerId);
}
