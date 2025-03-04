package service.impl;

import model.OrderProduct;
import service.OrderProductService;
import repository.OrderProductRepository;

import java.util.List;
import java.util.logging.Logger;

/*public class OrderProductServiceImpl implements OrderProductService {
    private final OrderProductRepository orderProductRepository;
    private static final Logger logger = Logger.getLogger(OrderProductService.class.getName());

    public OrderProductService(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }*/

public class OrderProductServiceImpl implements OrderProductService {
    private final OrderProductRepository orderProductRepository;
    private static final Logger logger = Logger.getLogger(OrderProductService.class.getName());

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository) {
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
