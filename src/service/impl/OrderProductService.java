package service.impl;
import model.OrderProduct;
import repository.IOrderProductRepository;
import service.IOrderProductService;

import java.util.List;
import java.util.logging.Logger;

public class OrderProductService implements IOrderProductService {
    private final IOrderProductRepository orderProductRepository;
    private static final Logger logger = Logger.getLogger(OrderProductService.class.getName());

    public OrderProductService(IOrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public boolean addOrderProduct(int orderId, int productId, int quantity, double unitPrice) {
        return orderProductRepository.addOrderProduct(orderId, productId, quantity, unitPrice);
    }

    @Override
    public List<OrderProduct> getOrderProducts(int orderId) {
        return orderProductRepository.getOrderProducts(orderId);
    }

    @Override
    public boolean removeOrderProduct(int orderProductId) {
        return orderProductRepository.removeOrderProduct(orderProductId);
    }
}

