package controller;

import model.Manufacturer;
import model.Product;
import service.ProductService;

import java.util.List;
import java.util.Scanner;

public class ProductController {
    private final ProductService productService;
    private final Scanner scanner;

    public ProductController( ProductService productService ) {
        this.productService = productService;
        this.scanner = new Scanner(System.in);
    }

    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    public void showProductMenu() {
        System.out.println("\nProduct Management");
        System.out.println("1. List All Products");
        System.out.println("2. View Product Details");
        System.out.println("3. Add New Product");
        System.out.println("4. Update Product");
        System.out.println("5. Delete Product");
        System.out.println("6. Back to Main Menu");
    }

    public void handleProductOperations() {
        while (true) {
            showProductMenu();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    listAllProducts();
                    break;
                case 2:
                    viewProductDetails();
                    break;
                case 3:
                    addNewProduct();
                    break;
                case 4:
                    updateProduct();
                    break;
                case 5:
                    deleteProduct();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private void listAllProducts() {
        productService.getAllProducts().forEach(System.out::println);
    }

    private void viewProductDetails() {
        System.out.print("Enter Product ID: ");
        int id =Integer.parseInt(scanner.nextLine());
        System.out.println(productService.getProductById(id));
    }

    private void addNewProduct() {
        Product product = new Product();

        System.out.print("Enter product name: ");
        product.setName(scanner.nextLine());

        System.out.print("Enter description: ");
        product.setDescription(scanner.nextLine());

        System.out.print("Enter price: ");
        product.setPrice(scanner.nextDouble());

        System.out.print("Enter stock quantity: ");
        product.setStockQuantity(scanner.nextInt());
        scanner.nextLine();

        //  need to implement manufacturer selection

        System.out.print("Enter Manufacturer ID: ");
        int manufacturerId = scanner.nextInt();
        scanner.nextLine();

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerId(manufacturerId);
        product.setManufacturers(manufacturer);

        productService.createProduct(product);
        System.out.println("Product created successfully!");
    }


    private void updateProduct() {
        System.out.print("Enter product ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Product product = productService.getProductById(id);
        System.out.print("Enter new name (" + product.getName() + "): ");
        product.setName(scanner.nextLine());
        System.out.print("Enter new description (" + product.getDescription() + "): ");
        product.setDescription(scanner.nextLine());
        System.out.print("Enter new price (" + product.getPrice() + "): ");
        product.setPrice(scanner.nextDouble());
        System.out.print("Enter new stock quantity (" + product.getStockQuantity() + "): ");
        product.setStockQuantity(scanner.nextInt());
        scanner.nextLine();

        productService.updateProduct(product);
        System.out.println("Product updated successfully!");
    }

    private void deleteProduct() {
        System.out.print("Enter product ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        productService.deleteProduct(id);
        System.out.println("Product deleted successfully!");
    }
}






    /*public ProductController(Connection connection) {
        if (connection != null) {
            this.productService = new ProductService(connection);
        } else {
            throw new IllegalArgumentException("Database connection cannot be null");
        }
    }

    public void getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            if (products.isEmpty()) {
                System.out.println("No products found");
            } else {
                for (Product product : products) {
                    System.out.println(product);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting products: " + e.getMessage());
        }
    }
}*/