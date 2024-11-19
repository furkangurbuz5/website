package com.gurbuz.website.service.product;

import com.gurbuz.website.model.Product;
import com.gurbuz.website.request.AddProductRequest;
import com.gurbuz.website.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
        Product addProduct(AddProductRequest request);

        Product getProductById(Long id);
        void deleteProductById(Long id);
        Product updateProduct(ProductUpdateRequest product, Long productId);

        List<Product> getAllProducts();
        List<Product> getProductsByCategory(String category);
        List<Product> getProductsByBrand(String brand);
        List<Product> getProductsByCategoryAndBrand(String category, String brand);
        List<Product> getProductsByName(String name);
        List<Product> getProductsByBrandAndName(String brand, String category);
        Long countProductsByBrandAndName(String brand, String name);

}
