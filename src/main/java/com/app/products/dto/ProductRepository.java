package com.app.products.dto;

import com.app.products.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    @Query(value ="SELECT DISTINCT brand FROM product", nativeQuery = true)
    List<String> findAllBrandsDistinct();
}
