package service;

import Repository.ProductRepository;
import model.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(Connection connection) {
        this.productRepository = new ProductRepository(connection);
    }

    public List<Product> getAllProducts() throws SQLException{
        return productRepository.getAllProducts();
    }
}
