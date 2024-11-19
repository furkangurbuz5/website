package com.gurbuz.website.controller;


import com.gurbuz.website.exceptions.ProductNotFoundException;
import com.gurbuz.website.exceptions.ResourceNotFoundException;
import com.gurbuz.website.model.Product;
import com.gurbuz.website.request.AddProductRequest;
import com.gurbuz.website.request.ProductUpdateRequest;
import com.gurbuz.website.response.ApiResponse;
import com.gurbuz.website.service.product.IProductService;
import com.gurbuz.website.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
class ProductController {
  private final ProductService ps;

  @GetMapping("/all")
  public ResponseEntity<ApiResponse> getAllProducts(){
    List<Product> products = ps.getAllProducts();
    return ResponseEntity.ok(new ApiResponse("Success", products));
  }

  @GetMapping("/product/{productId}/product")
  public ResponseEntity<ApiResponse> getProductById(@PathVariable("productId") Long id){
    try {
      Product product = ps.getProductById(id);
      return ResponseEntity.ok(new ApiResponse("Success", product));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @PostMapping("/add/{product}")
  public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
    try {
      Product theProduct = ps.addProduct(product);
      return ResponseEntity.ok(new ApiResponse("Added product successfully", theProduct));
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @PutMapping("/product/{productId}/update")
  public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId){
    try {
      Product theProduct = ps.updateProduct(request, productId);
      return ResponseEntity.ok(new ApiResponse("Update product success!", theProduct));
    } catch (ProductNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @DeleteMapping("/product/{productId}/delete")
  public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
    try {
      ps.deleteProductById(productId);
      return ResponseEntity.ok(new ApiResponse("Successfully deleted", productId));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @GetMapping("products/by/brand-and-name")
  public ResponseEntity<ApiResponse> getProductByBrandAndName(@PathVariable String brandName, @PathVariable String productName){
    try {
      List<Product> products =  ps.getProductsByBrandAndName(brandName, productName);
      if(products.isEmpty()){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found with name : ", productName));
      }
      return ResponseEntity.ok(new ApiResponse("Successfully retrieved products", products));
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @GetMapping("products/by/category-and-brand")
  public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@PathVariable String category, @PathVariable String brandName){
    try {
      List<Product> products = ps.getProductsByCategoryAndBrand(category, brandName);
      if(products.isEmpty()){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
      }
      return ResponseEntity.ok(new ApiResponse("Success", products));
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @GetMapping("/product/{name}/product")
  public ResponseEntity<ApiResponse> getProductByName(@PathVariable("name") String name){
    try {

      List<Product> products = ps.getProductsByName(name);

      if(products.isEmpty()){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
      }
      return ResponseEntity.ok(new ApiResponse("Success", products));


    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @GetMapping("/product/by-brand")
  public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand){
    try {

      List<Product> products = ps.getProductsByBrand(brand);

      if(products.isEmpty()){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
      }

      return ResponseEntity.ok(new ApiResponse("Success", products));


    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

  @GetMapping("/product/{category}/all/products")
  public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category){
    try {

      List<Product> products = ps.getProductsByCategory(category);

      if(products.isEmpty()){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No products found", null));
      }

      return ResponseEntity.ok(new ApiResponse("Success", products));


    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }
  }

}
















