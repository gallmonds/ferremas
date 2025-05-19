package com.ferremas.api.dtos;
import com.ferremas.api.entities.User;

public class UserInfo {
    private String email;
    private String role;
    private String name;
    private String createdAt;
    private String updatedAt;

    public UserInfo(User user) {
        this.email = user.getEmail();
        this.role = user.getRole().name();
        this.name = user.getName();
        this.createdAt = user.getCreatedAt() != null ? user.getCreatedAt().toString() : null;
        this.updatedAt = user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : null;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
