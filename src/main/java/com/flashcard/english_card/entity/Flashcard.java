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
@Table(name = "flashcards")
@NoArgsConstructor
@AllArgsConstructor
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flashcard_set_id", nullable = false)
    private FlashcardSet flashcardSet;

    @Column(nullable = false)
    private String word;
    @Column(nullable = false)
    private String meaning;
    private String pronunciation; //phiên âm
    @Column(columnDefinition = "TEXT")
    private String example;
    private String imageUrl;
    private Integer boxLevel = 1; //(1->5)
    private LocalDateTime nextReviewDate = LocalDateTime.now();

}
