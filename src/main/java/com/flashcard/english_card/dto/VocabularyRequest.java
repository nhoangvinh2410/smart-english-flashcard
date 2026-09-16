package com.flashcard.english_card.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyRequest {
    @NotBlank(message = "Từ vựng không được bỏ trống")
    private String word;
    @NotBlank(message = "Nghĩa không được bỏ trống")
    private String meaning;
    private String pronunciation;
    private String example;
    private String imageUrl;
}