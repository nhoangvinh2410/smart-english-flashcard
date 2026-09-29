package com.flashcard.english_card.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlashcardRequest {
    @NotBlank(message = "Từ vựng phải thuộc về 1 bộ thẻ")
    private Long flashcardId;
    @NotBlank(message = "Từ tiếng Anh không được để trống")
    private String word;
    @NotBlank(message = "Từ tiếng Việt không được để trống")
    private String meaning;

    private String pronunciation;
    private String example;
    private String imageUrl;

}



