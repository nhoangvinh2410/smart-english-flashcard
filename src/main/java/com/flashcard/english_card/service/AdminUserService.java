package com.flashcard.english_card.service;

import com.flashcard.english_card.dto.response.UserDTO;

import java.util.List;

public interface AdminUserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long userId);
    UserDTO toggleUserStatus(Long userId);
}
