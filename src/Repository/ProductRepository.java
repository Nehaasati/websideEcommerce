package Repository;

import model.Product;
import model.Manufacturers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private Connection connection;

    public ProductRepository(Connection connection) {
        this.connection = connection;
    }

    // Method to List all Products
    public List<Product> getAllProducts() throws SQLException {
        List<Product> productList  = new ArrayList<>();

        String query = "SELECT p.product_id, p.name AS product_name, p.description, p.price, p.stock_quantity, " +
                "m.manufacturer_id, m.name AS manufacturer_name " +
                "FROM products p " +
                "JOIN manufacturers m ON p.manufacturer_id = m.manufacturer_id";

        try (Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("product_id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setStockQuantity(resultSet.getInt("stock_quantity"));

                Manufacturers manufacturers =  new Manufacturers();
                manufacturers.setManufacturerId(resultSet.getInt("manufacturer_id"));
                manufacturers.setName(resultSet.getString("name"));

                product.setManufacturers(manufacturers);

                productList.add(product);
            }
        }
        return productList;

    }
}
