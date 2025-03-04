package service.impl;

import repository.OrderRepository;
import service.OrderService;

import java.util.List;
import java.util.logging.Logger;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private static final Logger logger = Logger.getLogger(OrderService.class.getName());

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public int placeOrder(int customerId) {
        return orderRepository.placeOrder(customerId);
    }

    @Override
    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    @Override
    public boolean cancelOrder(int orderId) {
        return orderRepository.cancelOrder(orderId);
    }

    @Override
    public boolean updateOrder(int orderId, int customerId) {
        return orderRepository.updateOrder(orderId, customerId);
    }

    @Override
    public List<String> getOrderHistory(int customerId) {
        return orderRepository.getOrderHistory(customerId);
    }
}
