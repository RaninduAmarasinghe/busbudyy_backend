package com.busbuddy.busbuddy.Controller;

import com.busbuddy.busbuddy.Model.Admin;
import com.busbuddy.busbuddy.Repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminRepo adminRepo;

    // Add new admin
    @PostMapping("/add")
    public ResponseEntity<String> addAdmin(@RequestBody Admin newAdmin) {
        if (adminRepo.findByAdminName(newAdmin.getAdminName()) != null) {
            return ResponseEntity.badRequest().body("Admin already exists");
        }
        adminRepo.save(newAdmin);
        return ResponseEntity.ok("Admin added successfully");
    }

    // Admin login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Admin loginRequest) {
        Admin admin = adminRepo.findByAdminName(loginRequest.getAdminName());
        if (admin != null && admin.getAdminPassword().equals(loginRequest.getAdminPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}