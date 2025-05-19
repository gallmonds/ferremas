package com.ferremas.api.controllers;

import com.ferremas.api.entities.Role;
import com.ferremas.api.entities.User;
import com.ferremas.api.repositories.UserRepository;
import com.ferremas.api.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PutMapping("/{id}/role")
    public ResponseEntity<?> changeUserRole(@PathVariable Integer id, 
                                            @RequestParam String newRole, 
                                            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader != null && authHeader.startsWith("Bearer ") 
                       ? authHeader.substring(7) : null;

        if (token == null || !jwtUtil.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Invalid token.");
        }

        String role = jwtUtil.extractRole(token);
        if (!Role.admin.name().equals(role)) {
            return ResponseEntity.status(403).body("Access denied. Only users with the role 'admin' can change roles.");
        }

        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOpt.get();

        if (user.getRole() == Role.admin) {
            return ResponseEntity.status(403).body("Access denied. Can't change an admin role.");
        }

        Role roleToSet;
        try {
            roleToSet = Role.valueOf(newRole);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Role not valid.");
        }

        if (roleToSet != Role.client && roleToSet != Role.vendor) {
            return ResponseEntity.badRequest().body("Access denied. Can only change to client or vendor.");
        }

        if (user.getRole() == roleToSet) {
            return ResponseEntity.badRequest().body("User already has this role.");
        }

        user.setRole(roleToSet);
        userRepository.save(user);

        return ResponseEntity.ok("Operation Successful. Role to change: " + roleToSet.name());
    }
    @GetMapping("/me")
    public ResponseEntity<?> getMyUserData(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader != null && authHeader.startsWith("Bearer ") 
                    ? authHeader.substring(7) : null;

        if (token == null || !jwtUtil.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Invalid token.");
        }

        String email = jwtUtil.extractUsername(token);
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User not found.");
        }

        User user = userOpt.get();

        Map<String, Object> response = Map.of(
            "email", user.getEmail(),
            "role", user.getRole(),
            "name", user.getName(),
            "createdAt", user.getCreatedAt(),
            "updatedAt", user.getUpdatedAt()
        );

        return ResponseEntity.ok(response);
    }
}