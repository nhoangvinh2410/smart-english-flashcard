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
@Table(name = "study_sessions")
@NoArgsConstructor
@AllArgsConstructor
public class StudySession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; //phiên học này của User nào

    private LocalDateTime starTime = LocalDateTime.now();
    private LocalDateTime endTime;
    private Integer totalCardsReviewed = 0; //tổng số thẻ đã lật trong phiên học này
}
