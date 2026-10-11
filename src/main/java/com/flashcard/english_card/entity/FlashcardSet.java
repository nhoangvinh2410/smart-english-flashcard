package com.flashcard.english_card.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
    private User user; //nhìu bộ thẻ thuộc về 1 user

    //nhiều bộ thẻ thuộc về 1 danh mục (Category)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    //xóa bộ thẻ sẽ tự động xóa tất cả các card bên trong
    @OneToMany(mappedBy = "flashcardSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Flashcard> flashcards;

    private LocalDateTime createAt = LocalDateTime.now();



}
