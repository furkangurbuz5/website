package com.gurbuz.website.repository;

import com.gurbuz.website.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
        List<Product> findByCategoryNameIgnoreCase(String category);

        List<Product> findByBrandIgnoreCase(String brand);

        List<Product> findByCategoryNameAndBrandIgnoreCase(String category, String brand);

        List<Product> findByNameIgnoreCase(String name);

        List<Product> findByBrandAndNameIgnoreCase(String brand, String name);

        Long countByBrandAndNameIgnoreCase(String brand, String name);
}
