package service.impl;

import repository.IOrderRepository;
import repository.impl.OrderRepository;
import service.IOrderService;

import java.util.List;
import java.util.logging.Logger;

public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {

        this.orderRepository = orderRepository;
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