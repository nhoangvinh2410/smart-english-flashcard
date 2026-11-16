package com.flashcard.english_card.service;

import com.flashcard.english_card.dto.request.ChangePasswordRequest;
import com.flashcard.english_card.dto.request.UpdateProfileRequest;
import com.flashcard.english_card.dto.response.UserDTO;

public interface UserService {
    //dùng để đổi thông tin user
    UserDTO updateProfile(String username, UpdateProfileRequest request);
    //dùng để hiển thị thông tin khi đăng nhập thành công
    UserDTO getCurrentUserProfile(String username);
    //dùng để đổi mật khẩu
    void changePassword(String username, ChangePasswordRequest request);
}
