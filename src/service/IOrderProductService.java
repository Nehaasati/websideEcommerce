package service;
import model.OrderProduct;

public interface IOrderProductService {
    boolean addOrderProduct(OrderProduct op);
    boolean deleteOrderProductsByOrderId(int orderId);
}
