package controller;

import service.OrderService;

public class OrderController {
    private final OrderService orderService = new OrderService();


    public void placeOrder(int productId, int quantity) {
        try {
            orderService.placeOrder(productId, quantity);
        } catch (Exception e) {
            System.out.println("❌ Error placing order: " + e.getMessage());
        }
    }

    public void cancelOrder(int orderId) {
        try {
            orderService.cancelOrder(orderId);
        } catch (Exception e) {
            System.out.println("❌ Error canceling order: " + e.getMessage());
        }
    }
}

