package service;

import model.OrderProduct;
import java.util.List;

public interface OrderProductService {
    boolean addOrderProduct(int orderId, int productId, int quantity, double unitPrice);
    List<OrderProduct> getOrderProducts(int orderId);
    boolean removeOrderProduct(int orderProductId);
}
