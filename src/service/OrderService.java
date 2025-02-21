package service;
import Repository.ProductRepository;
import Repository.OrderRepositry;


public class OrderService {
    private final OrderRepositry orderRepository = new OrderRepositry();
    private final ProductRepository productRepository = new ProductRepository();

    public void placeOrder(int productId, int quantity) {
        try {
            if (quantity <= 0) {
                System.out.println("❌ Quantity must be greater than zero!");
                return;
            }

            if (!productRepository.productExists(productId)) {
                System.out.println("❌ Product with ID " + productId + " does not exist.");
                return;
            }

            boolean success = orderRepository.addOrder(productId, quantity);
            if (success) {
                System.out.println("✅ Order placed successfully!");
            } else {
                System.out.println("❌ Failed to place order. Please try again later.");
            }
        } catch (Exception e) {
            System.out.println("❌ An unexpected error occurred: " + e.getMessage());
        }
    }

    public void cancelOrder(int orderId) {
        try {
            if (!orderRepository.orderExists(orderId)) {
                System.out.println("❌ Order with ID " + orderId + " does not exist.");
                return;
            }

            boolean success = orderRepository.deleteOrder(orderId);
            if (success) {
                System.out.println("✅ Order deleted successfully!");
            } else {
                System.out.println("❌ Failed to delete order. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("❌ An unexpected error occurred: " + e.getMessage());
        }
    }
}


