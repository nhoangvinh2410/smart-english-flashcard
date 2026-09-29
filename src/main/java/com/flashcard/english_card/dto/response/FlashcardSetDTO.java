package com.flashcard.english_card.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardSetDTO {
    private Long id;
    private String title; //tiêu đề
    private String description; //miêu tả
    private Boolean isPublic;

    //thông tin chủ sở hữu
    private Long userId;
    private String authorName; //tên người tạo
    private Integer totalCards; //tổng số từ vựng

    private LocalDateTime createAt;
}
