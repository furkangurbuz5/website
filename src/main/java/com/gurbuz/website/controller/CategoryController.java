package com.gurbuz.website.controller;


import com.gurbuz.website.exceptions.AlreadyExistsException;
import com.gurbuz.website.exceptions.ResourceNotFoundException;
import com.gurbuz.website.model.Category;
import com.gurbuz.website.response.ApiResponse;
import com.gurbuz.website.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

  private final ICategoryService cs;

  @GetMapping("/all")
  public ResponseEntity<ApiResponse> getAllCategories(){
    try {
      List<Category> categories = cs.getAllCategories();
      return ResponseEntity.ok(new ApiResponse("Found categories.", categories));
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error.", INTERNAL_SERVER_ERROR));
    }
  }

  @PostMapping("/add")
  public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
    try {
      Category theCategory = cs.addCategory(name);
      return ResponseEntity.ok(new ApiResponse("Success", theCategory));
    } catch (AlreadyExistsException e) {
      return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @GetMapping("/category/{id}/category")
  public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
    try {
      Category theCategory = cs.getCategoryById(id);
      return ResponseEntity.ok(new ApiResponse("Found", theCategory));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @GetMapping("category/{name}/category")
  public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
    try {
      Category theCategory = cs.getCategoryByName(name);
      return ResponseEntity.ok(new ApiResponse("Found", theCategory));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @DeleteMapping("/category/{id}/delete")
  public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
    try {
      cs.deleteCategoryById(id);
      return ResponseEntity.ok(new ApiResponse("Deleted",null));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @PutMapping("/category/{id}/update")
  public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id){
    try {
      cs.deleteCategoryById(id);
      return ResponseEntity.ok(new ApiResponse("Deleted",null));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

}













