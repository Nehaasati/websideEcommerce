package service.impl;
import repository.OrderRepository;
import repository.OrderProductRepository;
import model.Order;
import model.OrderProduct;
import service.IOrderService;

import java.util.List;
<<<<<<< HEAD

=======
>>>>>>> Neha
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository = new OrderRepository();
    private final OrderProductRepository orderProductRepository = new OrderProductRepository();

    @Override
    public void placeOrder(Order order, List<OrderProduct> orderProducts) {
        if (order == null) {                                         // validate oder object is not null
            System.err.println("Order cannot be null.");
            return;
        }
        if (order.getCustomerId() <= 0) {                            //validatecustomerid
            System.err.println("Invalid customer ID.");
            return;
        }
        //// It also validates each OrderProduct for correct product ID, positive quantity, and non-negative price.

        if (orderProducts == null || orderProducts.isEmpty()) {
            System.err.println("At least one product must be added to the order.");
            return;
        }
        for (OrderProduct op : orderProducts) {
            if (op.getProductId() <= 0) {
                System.err.println("Invalid product ID: " + op.getProductId());
                return;
            }
            if (op.getQuantity() <= 0) {
                System.err.println("Quantity must be greater than zero for product ID: " + op.getProductId());
                return;
            }
            if (op.getPrice() < 0) {
                System.err.println("Price cannot be negative for product ID: " + op.getProductId());
                return;
            }
        }
        //create order
        int orderId = orderRepository.createOrder(order); //service calls the repository method to create a new order
        if (orderId <= 0) {                                //generated Id
            System.err.println("Failed to create order.");
            return;
        }
        boolean allAdded = true;
        for (OrderProduct op : orderProducts) {
            op.setOrderId(orderId);
            boolean success = orderProductRepository.addOrderProduct(op);
            if (!success) {
                System.err.println("Failed to add product with ID: " + op.getProductId());
                allAdded = false;
            }
        }
        if (allAdded) {
            System.out.println("Order placed successfully with order ID: " + orderId);
        } else {
            System.err.println("Order created but some products could not be added.");
        }
    }

    // delete order
    @Override
    public void cancelOrder(int orderId) {
        if (orderId <= 0) {
            System.err.println("Invalid order ID.");
            return;
        }
        boolean productsDeleted = orderProductRepository.deleteOrderProductsByOrderId(orderId);
        boolean orderDeleted = orderRepository.deleteOrder(orderId);
        if (orderDeleted && productsDeleted) {
            System.out.println("Order canceled successfully.");
        } else if (orderDeleted) {
            System.err.println("Order canceled but failed to delete some order products.");
        } else {
            System.err.println("Failed to cancel order.");
        }
    }

    @Override
    public void displayOrderWithProducts(int orderId) {
        List<String> details = orderRepository.getOrderWithProducts(orderId);
        if (details.isEmpty()) {
            System.out.println("No details found for order ID: " + orderId);
        } else {
            details.forEach(System.out::println);
        }
    }
<<<<<<< HEAD
=======

>>>>>>> Neha
}
