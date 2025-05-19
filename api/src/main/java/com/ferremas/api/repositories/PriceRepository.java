package com.ferremas.api.repositories;

import com.ferremas.api.entities.Price;
import com.ferremas.api.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Integer> {
    
    List<Price> findByProductOrderByDateDesc(Product product);
    List<Price> findByProductIdOrderByDateDesc(Integer productId);
}