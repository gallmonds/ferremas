package com.ferremas.api.controllers;

import com.ferremas.api.dtos.BrandRequest;
import com.ferremas.api.entities.Brand;
import com.ferremas.api.entities.Role;
import com.ferremas.api.repositories.BrandRepository;
import com.ferremas.api.repositories.ProductRepository;
import com.ferremas.api.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private boolean isAdminOrVendor(String role) {
        return Role.admin.name().equals(role) || Role.vendor.name().equals(role);
    }

    @PostMapping
    public ResponseEntity<?> addBrand(@RequestBody BrandRequest brandDTO,
                                      @RequestHeader("Authorization") String authHeader) {
        String token = extractToken(authHeader);

        if (!jwtUtil.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Invalid token.");
        }

        String userRole = jwtUtil.extractRole(token);
        if (!isAdminOrVendor(userRole)) {
            return ResponseEntity.status(403).body("Access denied. Only admin or vendor can add brands.");
        }

        if (brandRepository.existsByName(brandDTO.getName())) {
            return ResponseEntity.badRequest().body("Brand name already exists.");
        }

        Brand brand = new Brand();
        brand.setName(brandDTO.getName());
        brandRepository.save(brand);

        return ResponseEntity.ok(new BrandRequest(brand.getId(), brand.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable Integer id,
                                         @RequestBody BrandRequest brandDTO,
                                         @RequestHeader("Authorization") String authHeader) {
        String token = extractToken(authHeader);

        if (!jwtUtil.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Invalid token.");
        }

        String userRole = jwtUtil.extractRole(token);
        if (!isAdminOrVendor(userRole)) {
            return ResponseEntity.status(403).body("Access denied. Only admin or vendor can edit brands.");
        }

        Optional<Brand> brandOpt = brandRepository.findById(id);
        if (brandOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Brand brand = brandOpt.get();

        if (brandRepository.existsByName(brandDTO.getName()) && !brand.getName().equals(brandDTO.getName())) {
            return ResponseEntity.badRequest().body("Brand name already exists.");
        }

        brand.setName(brandDTO.getName());
        brandRepository.save(brand);

        return ResponseEntity.ok(new BrandRequest(brand.getId(), brand.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Integer id,
                                         @RequestHeader("Authorization") String authHeader) {
        String token = extractToken(authHeader);

        if (!jwtUtil.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Invalid token.");
        }

        String userRole = jwtUtil.extractRole(token);
        if (!Role.admin.name().equals(userRole)) {
            return ResponseEntity.status(403).body("Access denied. Only admin can delete brands.");
        }

        Optional<Brand> brandOpt = brandRepository.findById(id);
        if (brandOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        boolean hasProducts = productRepository.existsByBrandId(id);
        if (hasProducts) {
            return ResponseEntity.badRequest().body("Cannot delete brand with associated products.");
        }

        brandRepository.deleteById(id);

        return ResponseEntity.ok("Brand deleted successfully.");
    }

    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<?> getAllBrands(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader != null && authHeader.startsWith("Bearer ")
                    ? authHeader.substring(7) : null;

        if (token == null || !jwtUtil.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Invalid token.");
        }

        String role = jwtUtil.extractRole(token);
        if (!role.equals("admin") && !role.equals("vendor")) {
            return ResponseEntity.status(403).body("Access denied.");
        }

        var brands = brandRepository.findAll();
        var dtos = brands.stream()
                        .map(b -> new BrandRequest(b.getId(), b.getName()))
                        .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBrandById(@PathVariable Integer id) {
        var brandOpt = brandRepository.findById(id);
        if (brandOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var brand = brandOpt.get();
        return ResponseEntity.ok(new BrandRequest(brand.getId(), brand.getName()));
    }
}
