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
@Table(name = "flashcard_sets")
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //tên bộ thẻ
    @Column(nullable = false)
    private String title;
    private String description;

    //công khai hoặc không (mặc định là riêng tư)
    private boolean isPublic = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private LocalDateTime createAt = LocalDateTime.now();



}
