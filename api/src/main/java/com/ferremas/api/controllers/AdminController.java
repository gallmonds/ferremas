package com.ferremas.api.controllers;

import com.ferremas.api.repositories.UserRepository;
import com.ferremas.api.entities.User;
import com.ferremas.api.utils.JwtUtil;
import com.ferremas.api.dtos.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserData(@PathVariable Integer id,
                                         HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("No autorizado: Token no encontrado");
        }
        String token = authHeader.substring(7);

        if (!jwtUtil.isTokenValid(token)) {
            return ResponseEntity.status(401).body("Token inválido");
        }

        String role = jwtUtil.extractRole(token);
        if (!"admin".equalsIgnoreCase(role)) {
            return ResponseEntity.status(403).body("No autorizado: Sólo admins pueden acceder");
        }

        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserInfo userInfoDTO = new UserInfo(userOpt.get());

        return ResponseEntity.ok(userInfoDTO);
    }
}