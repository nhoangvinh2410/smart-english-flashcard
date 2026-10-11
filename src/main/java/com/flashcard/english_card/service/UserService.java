package com.flashcard.english_card.service.impl;

import com.flashcard.english_card.dto.request.UserRegisterRequest;
import com.flashcard.english_card.dto.response.UserDTO;

public interface UserService {
    UserDTO registerUser(UserRegisterRequest request);
    UserDTO getUserById(Long id);
}
