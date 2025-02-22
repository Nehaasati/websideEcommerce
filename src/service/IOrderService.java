package service;
import model.Order;
import model.OrderProduct;
import java.util.List;

public interface IOrderService {
    void placeOrder(Order order, List<OrderProduct> orderProducts);
    void cancelOrder(int orderId);
    void displayOrderWithProducts(int orderId);
}
