package by.clevertec.service;

import by.clevertec.entity.Category;
import by.clevertec.repository.CategoryRepository;

import java.util.List;

public class CategoryService {

    private final CategoryRepository categoryRepository = new CategoryRepository();

    public void addCategory(Category category) {
        categoryRepository.create(category);
    }

    public void updateCategory(Category category) {
        categoryRepository.update(category);
    }

    public void deleteCategory(Category category) {
        categoryRepository.delete(category.getId());
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category getByName(String name) {
        return categoryRepository.getByName(name);
    }

    public List<Category> getAllCars() {
        return categoryRepository.findAll();
    }

}
