package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Officer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long officerId;

    private String name;
    private String email;
    private String phone;
    private String roleType; // e.g., "TRAINING_OFFICER", "PLACEMENT_OFFICER", etc.

    public Officer() {
    }

    public Officer(String name, String email, String phone, String roleType) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.roleType = roleType;
    }

    // Getters and Setters
    public Long getOfficerId() {
        return officerId;
    }

    public void setOfficerId(Long officerId) {
        this.officerId = officerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoleType() {
        return roleType;
    }
    
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
