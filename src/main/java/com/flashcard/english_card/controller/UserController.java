package com.flashcard.english_card.controller;

import com.flashcard.english_card.dto.request.ChangePasswordRequest;
import com.flashcard.english_card.dto.request.UpdateProfileRequest;
import com.flashcard.english_card.dto.response.UserDTO;
import com.flashcard.english_card.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    //lấy thông tin người dùng đang đăng nhập
    //tại sao ko truyền vào username vì nếu làm vậy thì trên URL sẽ bị dễ điền đc username=Admin
    //chỉ cần truyền vào rồi lớp JwtAuthenticationFilter tự giải mã
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication){
        String username = authentication.getName();
        UserDTO currentUser = userService.getCurrentUserProfile(username);
        return ResponseEntity.ok(currentUser);
    }

    //cập nhật tên hiển thị và avatar
    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateProfileRequest request){
        String username = authentication.getName();
        UserDTO updatedUser = userService.updateProfile(username, request);
        return ResponseEntity.ok(updatedUser);
    }

    //đổi mật khẩu
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequest request){
        String username = authentication.getName();
        userService.changePassword(username, request);
        return ResponseEntity.ok("Đổi mật khẩu thành công");
    }


}

