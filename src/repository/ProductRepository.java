package repository;

import model.Product;


import java.util.Optional;
import java.util.List;

public interface ProductRepository {
    Optional<Product> findById(int productId);
    List<Product> getAllProducts();
    Product save(Product product);
    void update(Product product);
    void delete(int productId);
}


   /* public ProductRepository(Connection connection) {
        this.connection = connection;
    }

    // Method to List all Products
    public List<Product> getAllProducts() throws SQLException {
        List<Product> productList  = new ArrayList<>();

        String query  = "SELECT p.product_id, p.name, p.description, p.price, p.stock_quantity, m.name AS manufacturer_name " +
                "FROM products p JOIN manufacturers m ON p.manufacturer_id = m.manufacturer_id";


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
              //  manufacturers.setManufacturerId(resultSet.getInt("manufacturer_id"));
                manufacturers.setName(resultSet.getString("manufacturer_name"));

                product.setManufacturers(manufacturers);

                productList.add(product);
            }
        } catch (SQLException e) {
            System.out.println("Error getting products: " + e.getMessage()); // debugging
        }
        return productList;

    }
}*/
