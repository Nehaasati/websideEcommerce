
package controller;

import model.Product;
import service.ProductService;
import Main.Main;

import java.util.List;
import java.util.Scanner;

public class GuestMenu {
    private final ProductService productService;
    private final Scanner scanner;


    public GuestMenu(ProductService productService, Scanner scanner) {
        this.productService = productService;
        this.scanner = scanner;
    }


    public void show() {
        while (true) {
            System.out.println("\n👀 === GUEST BROWSING ===");
            System.out.println("1. 🔍 Search Products");
            System.out.println("2. 🏷️ View All Products");
            System.out.println("3. ↩️ Return");
            System.out.print("🔀 Choose option: ");

            int choice = Main.getIntInput(scanner); // Use Main.Main.getIntInput, pass scanner
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter search term: ");
                    String term = scanner.nextLine();
                    List<Product> results = productService.searchProductsByName(term);
                    displayProducts(results);
                }
                case 2 -> {
                    List<Product> allProducts = productService.getAllProducts();
                    displayProducts(allProducts);
                }
                case 3 -> {
                    return;
                }
                default -> System.out.println("❌ Invalid option");
            }
        }
    }

    private void displayProducts(List<Product> products) {
        System.out.println("\n🛍️ Available Products:");
        System.out.println("| ID  | Name                 | Price     |");
        System.out.println("|-----|----------------------|-----------|");

        products.stream()
                .filter(p -> p.getStockQuantity() > 0) // Only display available products
                .forEach(p -> {
                    System.out.printf("| %-3d | %-20s | %-9.2f |\n", p.getProductId(), p.getName(), p.getPrice());
                });

        if (products.isEmpty()) {
            System.out.println("No products available matching your search term.");
        }
    }


        /*System.out.println("\n🛍️ Available Products:");
        products.forEach(p ->
                System.out.printf("| %-20s | $%-8.2f |\n",
                        p.getName(), p.getPrice())
        );
    }*/
}
