package com.flashcard.english_card.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotBlank(message = "Không được bỏ trống")
    @Size(min = 2, max = 50, message = "Tên danh mục phải từ 2 ký tự trở lên")
    private String name;
    private String description;
}
