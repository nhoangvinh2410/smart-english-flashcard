package com.flashcard.english_card.mapper;

import com.flashcard.english_card.dto.request.UserRegisterRequest;
import com.flashcard.english_card.dto.response.UserDTO;
import com.flashcard.english_card.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    //đầu vào là nội dung người dùng nhập xong đóng gói xuống entity
    public User toEntity(UserRegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole("USER");
        return user;
    }

    //đầu ra là dung ở database đóng gói đưa lên dto
    public UserDTO toDTO(User user) {
        if(user == null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setCreatedAt(user.getCreateAt());
        return userDTO;
    }
}
