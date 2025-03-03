package Test;

import model.Category;
import repository.CategoryRepository;

import java.util.List;

public class CategoriesTest {
    public static void main(String[] args) {
        CategoryRepository categoryRepository = new CategoryRepository();

        System.out.println("**Testing CategoryRepository**");

        //Test fetching all categories
       // testGetAllCategories(categoryRepository);

       // Test fetching category by invalid ID
        testGetCategoryById(categoryRepository, -1);  // Negative ID should fail
    }

   /* public static void testGetAllCategories(CategoryRepository repository) {
        System.out.println("\n➡️ Fetching all categories...");
        try {
            List<Category> categories = repository.getAllCategories();
            if (categories.isEmpty()) {
                System.out.println("⚠️ No categories found.");
            } else {
                System.out.println("+-----------------------+");
                System.out.println("|  ID  |     Name      |");
                System.out.println("+-----------------------+");
                for (Category category : categories) {
                    System.out.printf("|  %-3d | %-12s |\n",
                            category.getCategoryId(), category.getName());
                }
                System.out.println("+-----------------------+");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }*/

    public static void testGetCategoryById(CategoryRepository repository, int id) {
        System.out.println("\n➡️ Fetching category with ID:  " + id);
        try {
            Category category = repository.getCategoryById(id);
            if (category == null) {
                System.out.println("⚠️ Category not found.");
            } else {
                System.out.println("\n✅ Category Found:");
                System.out.println("+-----------------------+");
                System.out.println("|  ID  |     Name      |");
                System.out.println("+-----------------------+");
                System.out.printf("|  %-3d | %-12s |\n",
                        category.getCategoryId(), category.getName());
                System.out.println("+-----------------------+");
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}

