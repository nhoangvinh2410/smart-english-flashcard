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
public class FlashcardDTO {
    private Long id;
    private Long flashcardSetId; //bộ thẻ
    private String word;
    private String meaning;
    private String pronunciation; //phát âm
    private String example;
    private String imageUrl;
    private Integer boxLevel;
    private LocalDateTime nextReviewDate; //ngày ôn tập tiếp theo
}


