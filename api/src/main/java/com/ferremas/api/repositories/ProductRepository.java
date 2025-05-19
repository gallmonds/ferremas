package com.ferremas.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ferremas.api.entities.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    boolean existsByBrandId(Integer brandId);
    List<Product> findByCategoryId(Integer categoryId);

}
