package com.flashcard.english_card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyDTO {
    private Long id;
    private String word;
    private String meaning;
    private String imageUrl;
    private String example;
    private String pronunciation;
    private int boxLevel;
}
