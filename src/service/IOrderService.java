package service;

import java.util.List;

public interface IOrderService {
    int placeOrder(int customerId);
    List<String> getAllOrders();

    // delete order
    void cancelOrder(int orderId);
    boolean updateOrder(int orderId, int customerId);
    List<String> getOrderHistory(int customerId);
}

