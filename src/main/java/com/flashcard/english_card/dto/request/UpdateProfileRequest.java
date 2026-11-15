package com.flashcard.english_card.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequest {
    @NotBlank(message = "Không được để trống")
    private String fullName;
    private String avatarUrl;
}