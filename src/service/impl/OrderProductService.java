package service.impl;
import model.OrderProduct;
import service.IOrderProductService;


import repository.OrderProductRepository;


public class OrderProductService implements IOrderProductService {
    private final OrderProductRepository orderProductRepository = new OrderProductRepository();

    @Override
    public boolean addOrderProduct(OrderProduct op) {
        if (op == null) {
            System.err.println("Order product cannot be null.");
            return false;
        }
        if (op.getOrderId() <= 0) {
            System.err.println("Invalid order ID for order product.");
            return false;
        }
        if (op.getProductId() <= 0) {
            System.err.println("Invalid product ID for order product.");
            return false;
        }
        if (op.getQuantity() <= 0) {
            System.err.println("Quantity must be greater than zero for order product.");
            return false;
        }
        if (op.getunit_Price() < 0) {
            System.err.println("Price cannot be negative for order product.");
            return false;
        }
        return orderProductRepository.addOrderProduct(op);
    }

    @Override
    public boolean deleteOrderProductsByOrderId(int orderId) {
        if (orderId <= 0) {
            System.err.println("Invalid order ID.");
            return false;
        }
        return orderProductRepository.deleteOrderProductsByOrderId(orderId);
    }

}
