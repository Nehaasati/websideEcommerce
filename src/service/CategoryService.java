package service;

import Repository.CategoryRepository;
import model.Category;

import java.util.List;

public class CategoryService {
    private final CategoryRepository categoryRepository;


    public CategoryService() {
        this.categoryRepository = new CategoryRepository();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }
}
