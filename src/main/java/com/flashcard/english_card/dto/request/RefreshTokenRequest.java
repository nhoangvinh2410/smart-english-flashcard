package com.flashcard.english_card.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequest {
    @NotBlank(message = "Refresh token không được để trống")
    private String refreshToken;
    //dùng để hứng chuỗi refreshToken mà FE gửi ngầm lên để xin cấp Access Token mới
}
