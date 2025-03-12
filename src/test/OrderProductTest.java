package test;

import model.OrderProduct;
import repository.impl.OrderProductRepository;
import util.SqliteConnectionManger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class OrderProductTest {
    private static final OrderProductRepository orderProductRepository = new OrderProductRepository();

    public static void main(String[] args) {
        System.out.println("\n🔍 **Checking Order Products in Database** 🔍");

        // Test adding an order product
        boolean isAdded = orderProductRepository.addOrderProduct(1, 101, 2, 49.99);
        System.out.println(isAdded ? "✅ Product added to order successfully." : "❌ Failed to add product to order.");

        // Test fetching order products
        fetchOrderProducts(1);

        // Test removing an order product
        boolean isRemoved = orderProductRepository.removeOrderProduct(1);
        System.out.println(isRemoved ? "✅ Product removed from order successfully." : "❌ Failed to remove product from order.");

        // Verify order products again
        fetchOrderProducts(1);
    }

    public static void fetchOrderProducts(int orderId) {
        List<OrderProduct> orderProducts = orderProductRepository.getOrderProducts(orderId);

        System.out.println("\n+----------------+------------+------------+------------+");
        System.out.println("| Order Product ID | Order ID  | Product ID | Quantity  | Price  |");
        System.out.println("+----------------+------------+------------+------------+");

        for (OrderProduct op : orderProducts) {
            System.out.printf("| %-16d | %-10d | %-10d | %-8d | %-6.2f |\n",
                    op.getOrderProductId(), op.getOrderId(), op.getProductId(), op.getQuantity(), op.getunit_price());
        }

        System.out.println("+----------------+------------+------------+------------+");
    }
}

