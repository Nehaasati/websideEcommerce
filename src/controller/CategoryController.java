package controller;

import model.Category;
import service.CategoryService;
import java.util.List;
import java.util.Scanner;


public class CategoryController {
    private final CategoryService categoryService;
    private final Scanner scanner;

    public CategoryController() {
        this.categoryService = new CategoryService();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n------Category Management------");
            System.out.println("1. List All Categories");
            System.out.println("2. Get Category by ID");
            System.out.println("3. Exit");
            System.out.println("Enter your choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1": displayCategories();
                          break;
                case "2":
                    displayCategoryById();
                    break;
                case "3":
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    public void displayCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            System.out.println("No categories found.");
        return;
        }

        // Print table header
        System.out.println("+------------+--------------------------+");
        System.out.println("| Category ID | Category Name           |");
        System.out.println("+------------+--------------------------+");

        // Print category data
        for (Category category : categories) {
            System.out.printf("| %-10d | %-24s |\n", category.getCategoryId(), category.getName());
        }

        // Print table footer
        System.out.println("+------------+--------------------------+");
    }

        private void displayCategoryById() {
            System.out.print("\nEnter Category ID: ");
            int id;
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid numeric ID.");
                return;
            }

            try {
                Category category = categoryService.getCategoryById(id);
                printCategoryDetails(category);
            } catch (IllegalArgumentException e) {
                System.out.println(" Error: " + e.getMessage());
            }
        }

        private void printCategoryDetails(Category category) {
            System.out.println("\n+------------+--------------------------+");
            System.out.println("| Category ID | Category Name           |");
            System.out.println("+------------+--------------------------+");
            System.out.printf("| %-10d | %-24s |\n", category.getCategoryId(), category.getName());
            System.out.println("+------------+--------------------------+");
        }
    }

