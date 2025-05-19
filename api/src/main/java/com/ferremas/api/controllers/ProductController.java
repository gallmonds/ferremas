package com.ferremas.api.controllers;

import com.ferremas.api.dtos.*;
import com.ferremas.api.entities.*;
import com.ferremas.api.repositories.*;
import com.ferremas.api.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired private ProductRepository productRepository;
    @Autowired private PriceRepository priceRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private BrandRepository brandRepository;
    @Autowired private JwtUtil jwtUtil;

    private boolean isAuthenticated(String token, String... allowedRoles) {
        if (token == null || !jwtUtil.isTokenValid(token)) return false;
        String role = jwtUtil.extractRole(token);
        return Arrays.asList(allowedRoles).contains(role);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody CreateUpdateProduct dto,
                                           @RequestHeader("Authorization") String authHeader) {
        String token = authHeader != null && authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
        if (!isAuthenticated(token, "admin", "vendor"))
            return ResponseEntity.status(403).body("Access denied.");

        Optional<Brand> brandOpt = brandRepository.findById(dto.getBrandId());
        if (brandOpt.isEmpty()) return ResponseEntity.badRequest().body("Brand not found.");

        Product product = new Product();
        product.setProductCode(dto.getProductCode());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setBrandCode(dto.getBrandCode());
        product.setBrand(brandOpt.get());
        product.setStock(dto.getStock());

        if (dto.getCategoryId() != null) {
            categoryRepository.findById(dto.getCategoryId()).ifPresent(product::setCategory);
        }

        product = productRepository.save(product);

        Price price = new Price();
        price.setProduct(product);
        price.setAmount(dto.getPrice());
        priceRepository.save(price);

        return ResponseEntity.ok("Product created.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id,
                                        @RequestBody CreateUpdateProduct dto,
                                        @RequestHeader("Authorization") String authHeader) {
        String token = authHeader != null && authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
        if (!isAuthenticated(token, "admin", "vendor"))
            return ResponseEntity.status(403).body("Access denied.");

        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) return ResponseEntity.notFound().build();

        Product product = productOpt.get();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setStock(dto.getStock());

        if (dto.getCategoryId() != null) {
            categoryRepository.findById(dto.getCategoryId()).ifPresent(product::setCategory);
        }

        if (dto.getBrandId() != null) {
            brandRepository.findById(dto.getBrandId()).ifPresent(product::setBrand);
        }

        productRepository.save(product);

        Price price = new Price();
        price.setProduct(product);
        price.setAmount(dto.getPrice());
        priceRepository.save(price);

        return ResponseEntity.ok("Product updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id,
                                           @RequestHeader("Authorization") String authHeader) {
        String token = authHeader != null && authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
        if (!isAuthenticated(token, "admin"))
            return ResponseEntity.status(403).body("Only admins can delete products.");

        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) return ResponseEntity.notFound().build();

        productRepository.delete(productOpt.get());
        return ResponseEntity.ok("Product deleted.");
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> dtos = mapProductsToDTOs(products);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getByCategory(@PathVariable Integer categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        List<ProductDTO> dtos = mapProductsToDTOs(products);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) return ResponseEntity.notFound().build();

        ProductDTO dto = mapProductToDTO(productOpt.get());
        return ResponseEntity.ok(dto);
    }

    private ProductDTO mapProductToDTO(Product product) {
    List<Price> prices = priceRepository.findByProductIdOrderByDateDesc(product.getId());
    List<PriceRequest> priceDTOs = prices.stream()
            .map(p -> new PriceRequest(p.getDate(), p.getAmount()))
            .toList();

    return new ProductDTO(
            product.getId(),
            product.getProductCode(),
            product.getName(),
            product.getDescription(),
            product.getCategory() != null ? product.getCategory().getId() : null,
            product.getBrand().getId(),
            product.getStock(),
            priceDTOs
    );
}


    private List<ProductDTO> mapProductsToDTOs(List<Product> products) {
        return products.stream()
                .map(this::mapProductToDTO)
                .toList();
    }
}