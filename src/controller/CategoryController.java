/*package controller;

import java.util.List;
import service.CategoryService;
import model.Category;

public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController() {
        this.categoryService = new CategoryService();
    }

    public void displayCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            System.out.println("No categories found.");
        } else {
            for (Category category : categories) {
                System.out.println(category);
            }
        }
    }
}*/
