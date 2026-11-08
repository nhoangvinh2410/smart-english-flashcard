package com.flashcard.english_card.service;

import com.flashcard.english_card.dto.request.RefreshTokenRequest;
import com.flashcard.english_card.dto.request.UserLoginRequest;
import com.flashcard.english_card.dto.request.UserRegisterRequest;
import com.flashcard.english_card.dto.response.AuthDTO;
import com.flashcard.english_card.dto.response.RefreshTokenDTO;
import com.flashcard.english_card.dto.response.UserDTO;

public interface AuthService {
    UserDTO registerUser(UserRegisterRequest request);
    AuthDTO login(UserLoginRequest request);
    RefreshTokenDTO renewTokens(RefreshTokenRequest request);
    //xóa ref khi người dùng đăng xuất (logout)
    void logout(RefreshTokenRequest request);
}
