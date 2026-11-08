package com.flashcard.english_card.controller;

import com.flashcard.english_card.dto.request.RefreshTokenRequest;
import com.flashcard.english_card.dto.request.UserLoginRequest;
import com.flashcard.english_card.dto.request.UserRegisterRequest;
import com.flashcard.english_card.dto.response.AuthDTO;
import com.flashcard.english_card.dto.response.RefreshTokenDTO;
import com.flashcard.english_card.dto.response.UserDTO;
import com.flashcard.english_card.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserRegisterRequest request){
        return new ResponseEntity<>(authService.registerUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@Valid @RequestBody UserLoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenDTO> renewTokens(@Valid @RequestBody RefreshTokenRequest request){
        return ResponseEntity.ok(authService.renewTokens(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest request){
         authService.logout(request);
         return ResponseEntity.ok("Đăng xuất thành công");

    }

}
