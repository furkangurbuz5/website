package com.gurbuz.website.service.category;

import com.gurbuz.website.exceptions.AlreadyExistsException;
import com.gurbuz.website.exceptions.ResourceNotFoundException;
import com.gurbuz.website.model.Category;
import com.gurbuz.website.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
  private final CategoryRepository cr;


  @Override
  public Category getCategoryById(Long id) {
    return cr.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
  }

  @Override
  public Category getCategoryByName(String name) {
    return cr.findByName(name);
  }

  @Override
  public List<Category> getAllCategories() {
    return cr.findAll();
  }

  @Override
  public Category addCategory(Category category) {
    return Optional.of(category)
      .filter(c -> !cr.existsByName(c.getName()))
      .map(cr :: save)
      .orElseThrow(() -> new AlreadyExistsException(category.getName() + " already exists!"));
  }

  @Override
  public Category updateCategory(Category category, Long id) {
    return Optional.ofNullable(getCategoryById(id))
      .map(oldCategory -> {
        oldCategory.setName(category.getName());
        return cr.save(oldCategory);
      })
      .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
  }

  @Override
  public void deleteCategoryById(Long id) {
    cr.findById(id)
      .ifPresentOrElse(cr::delete, () -> {
        throw new ResourceNotFoundException("Category not found!");
      });
  }
}
