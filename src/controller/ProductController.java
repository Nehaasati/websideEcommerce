package controller;

import model.Product;
import model.Manufacturer;
import service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProductController{
    private final ProductService productService;
    private final Scanner scanner;

    public ProductController(ProductService productService) {
        this.productService = productService;
        this.scanner =  new Scanner(System.in);
    }

    public void displayProductMenu() {  // Moved outside constructor
        while (true) {
            System.out.println("\n=====Product Management Menu=====");
            System.out.println("1. View All Products");
            System.out.println("2. Search Product by Name");
            System.out.println("3. Search Product by Category");
            System.out.println("4. Search Product by Price Range");
            System.out.println("5. Add New Product");
            System.out.println("6. Update Product");
            System.out.println("7. Delete Product");
            System.out.println("8. Update Product Categories");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAllProducts();
                    break;
                case 2:
                    searchProductByName();
                    break;
                case 3:
                    searchProductByCategory();
                    break;
                case 4:
                    searchProductByPriceRange();
                    break;
                case 5:
                    addNewProduct();
                    break;
                case 6:
                    updateProduct();
                    break;
                case 7:
                    deleteProduct();
                    break;
                case 8:
                    updateProductCategories();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    private void searchProductByName() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        List<Product> products = productService.searchProductByName(name);
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    private void searchProductByCategory() {
        System.out.print("Enter category name: ");
        String categoryName = scanner.nextLine();
        List<Product> products = productService.searchProductByCategory(categoryName);
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    private void searchProductByPriceRange() {
        System.out.print("Enter minimum price: ");
        double minPrice = scanner.nextDouble();
        System.out.print("Enter maximum price: ");
        double maxPrice = scanner.nextDouble();
        List<Product> products = productService.searchProductByPriceRange(minPrice, maxPrice);
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            for (Product product : products) {
                System.out.println(product);
            }
        }
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
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerId(manufacturerId);
        product.setManufacturers(manufacturer);

        productService.createProduct(product);
        System.out.println("Product created successfully!");
    }


    private void updateProduct() {
        System.out.print("Enter product ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Clear the newline character

        // Get product wrapped in Optional
        Optional<Product> optionalProduct = productService.getProductById(id);

        // Check if product exists
        if (!optionalProduct.isPresent()) {
            System.out.println("Product not found!");
            return;
        }

        // Extract actual Product object from Optional
        Product product = optionalProduct.get();

        // Update name
        System.out.print("Enter new name (" + product.getName() + "): ");
        product.setName(scanner.nextLine());

        // Update description
        System.out.print("Enter new description (" + product.getDescription() + "): ");
        product.setDescription(scanner.nextLine());

        // Update price
        System.out.print("Enter new price (" + product.getPrice() + "): ");
        product.setPrice(scanner.nextDouble());
        scanner.nextLine();  // Clear the newline after double input

        // Update stock quantity
        System.out.print("Enter new stock quantity (" + product.getStockQuantity() + "): ");
        product.setStockQuantity(scanner.nextInt());
        scanner.nextLine();  // Clear the newline after int input

        // Save changes
        productService.updateProduct(product);
        System.out.println("Product updated successfully!");
    }

    private void updateProductCategories() {
        System.out.print("Enter product ID to update categories: ");
        int productId = scanner.nextInt();

        System.out.print("Enter number of categories: ");
        int categoryCount = scanner.nextInt();

        int[] categoryIds = new int[categoryCount];
        for (int i = 0; i < categoryCount; i++) {
            System.out.print("Enter category ID: ");
            categoryIds[i] = scanner.nextInt();
        }

        boolean success = productService.updateProductCategories(productId, categoryIds);
        if (success) {
            System.out.println("Product categories updated successfully!");
        } else {
            System.out.println("Product not found!");
        }
    }

    private void deleteProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        productService.deleteProduct(id);
        System.out.println("Product deleted successfully!");
    }
}







