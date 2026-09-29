package com.flashcard.english_card.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlashcardSetRequest {
    @NotBlank(message = "Tên bộ thẻ không được bỏ trống")
    @Size(max = 100, message = "Tên bộ thẻ không được vượt quá 100 ký tự")
    private String title;

    private String description;
    private Boolean isPublic = false; //chưa bật công khai

}
