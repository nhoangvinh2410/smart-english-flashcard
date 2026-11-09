package com.flashcard.english_card.controller;

import com.flashcard.english_card.dto.response.UserDTO;
import com.flashcard.english_card.service.AdminUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminUserService adminUserService;

    public AdminController(AdminUserService adminUserService){
        this.adminUserService = adminUserService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(adminUserService.getAllUsers());
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(adminUserService.getUserById(userId));
    }

    @PutMapping("/{userId}/toggle-status")
    public ResponseEntity<UserDTO> toggleUserStatus(@PathVariable Long userId){
        return ResponseEntity.ok(adminUserService.toggleUserStatus(userId));
    }

}
