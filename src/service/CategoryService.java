package service;

import repository.CategoryRepository;
import model.Category;

import java.util.List;

import model.Category;
import repository.CategoryRepository;

import java.sql.SQLException;
import java.util.List;

public class CategoryService {
    private final CategoryRepository categoryRepository;


    public CategoryService() {
        this.categoryRepository = new CategoryRepository();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    public Category getCategoryById(int id) throws IllegalArgumentException {
        if (id <= 0) {
            throw new IllegalArgumentException("Category ID must be a positive number");
        }

        Category category = categoryRepository.getCategoryById(id);
        if (category == null) {
            throw new IllegalArgumentException("Category with ID " + id + " not found");
        }
        return category;
    }
}
