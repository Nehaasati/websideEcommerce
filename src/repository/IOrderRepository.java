package repository;

import java.util.List;

public interface IOrderRepository {
    int placeOrder(int customerId);
    List<String> getAllOrders();
    boolean cancelOrder(int orderId);
    boolean updateOrder(int orderId, int customerId);
    List<String> getOrderHistory(int customerId);
}
