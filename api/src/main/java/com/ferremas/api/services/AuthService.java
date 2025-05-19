package com.ferremas.api.services;

import com.ferremas.api.entities.User;
import com.ferremas.api.repositories.UserRepository;
import com.ferremas.api.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(String name, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        String salt = UUID.randomUUID().toString();
        String hashed = BCrypt.hashpw(password + salt, BCrypt.gensalt());

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setSalt(salt);
        user.setPasswordHash(hashed);

        userRepository.save(user);

        return jwtUtil.generateToken(user);
    }

    public String login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) throw new RuntimeException("Invalid credentials");

        User user = userOpt.get();
        String hashed = BCrypt.hashpw(password + user.getSalt(), user.getPasswordHash());

        if (!BCrypt.checkpw(password + user.getSalt(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user);
    }
}
