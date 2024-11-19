package com.gurbuz.website.service.product;

import com.gurbuz.website.exceptions.ProductNotFoundException;
import com.gurbuz.website.model.Category;
import com.gurbuz.website.model.Product;
import com.gurbuz.website.repository.CategoryRepository;
import com.gurbuz.website.repository.ProductRepository;
import com.gurbuz.website.request.AddProductRequest;
import com.gurbuz.website.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class ProductService implements IProductService{
        private final ProductRepository pr;
        private final CategoryRepository cr;

        @Override
        public Product addProduct(AddProductRequest request) {
                //check if category is found in the db
                //if yes, set as new product category
                //if no, save as new category
                Category category = Optional.ofNullable(cr.findByName(request.getCategory().getName()))
                        .orElseGet(() -> {
                                Category newCategory = new Category(request.getCategory().getName());
                                return cr.save(newCategory);
                        });
                request.setCategory(category);
                return pr.save(createProduct(request, category));
        }

        private Product createProduct(AddProductRequest request, Category category){
                return new Product(
                        request.getName(),
                        request.getBrand(),
                        request.getPrice(),
                        request.getInventory(),
                        request.getDescription(),
                        category
                );
        }

        @Override
        public Product getProductById(Long id) {
                return pr.findById(id)
                        .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
        }

        @Override
        public void deleteProductById(Long id) {
                pr.findById(id)
                        .ifPresentOrElse(pr::delete,
                                () -> {throw new ProductNotFoundException("Product not found!");});
        }

        @Override
        public Product updateProduct(ProductUpdateRequest request, Long productId) {
                return pr.findById(productId)
                        .map(existingProduct -> updateExistingProduct(existingProduct, request))
                        .map(pr::save)
                        .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
        }

        private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
                existingProduct.setName(request.getName());
                existingProduct.setBrand(request.getBrand());
                existingProduct.setPrice(request.getPrice());
                existingProduct.setInventory(request.getInventory());
                existingProduct.setDescription(request.getDescription());

                Category category = cr.findByName(request.getCategory().getName());
                existingProduct.setCategory(category);
                return existingProduct;
        }

        @Override
        public List<Product> getAllProducts() {
                return pr.findAll();
        }

        @Override
        public List<Product> getProductsByCategory(String category) {
                return pr.findByCategoryName(category);
        }

        @Override
        public List<Product> getProductsByBrand(String brand) {
                return pr.findByBrand(brand);
        }

        @Override
        public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
                return pr.findByCategoryNameAndBrand(category, brand);
        }

        @Override
        public List<Product> getProductsByName(String name) {
                return pr.findByName(name);
        }

        @Override
        public List<Product> getProductsByBrandAndName(String brand, String name) {
                return pr.findByBrandAndName(brand, name);
        }

        @Override
        public Long countProductsByBrandAndName(String brand, String name) {
                return pr.countByBrandAndName(brand, name);
        }
}
