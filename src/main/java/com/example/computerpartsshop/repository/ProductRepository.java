package com.example.computerpartsshop.repository;

import com.example.computerpartsshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);

    @Query(value = "SELECT * FROM product p LIMIT :limit", nativeQuery = true)
    List<Product> findTopN(@Param("limit") int limit);
}
