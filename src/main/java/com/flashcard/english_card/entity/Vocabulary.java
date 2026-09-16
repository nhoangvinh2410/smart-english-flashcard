package com.flashcard.english_card.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vocabularies")
public class Vocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //mặt trước
    private String word;
    private String pronunciation;
    private String imageUrl;
    //mặt sau
    private String meaning;
    private String example;

    private int boxLevel = 1;
    private LocalDateTime nextReviewDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
