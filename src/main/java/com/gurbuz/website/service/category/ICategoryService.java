package com.gurbuz.website.service.category;

import com.gurbuz.website.model.Category;

import java.util.List;

public interface ICategoryService {
        Category getCategoryById(Long id);
        Category getCategoryByName(String name);
        List<Category> getAllCategories();
        Category AddCategory(Category category);
        Category updateCategory(Category category, Long id);
        void deleteCategoryById(Long id);

}