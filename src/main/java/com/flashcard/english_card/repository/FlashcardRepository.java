package com.flashcard.english_card.repository;

import com.flashcard.english_card.entity.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    //lấy danh sách thẻ lẻ thuộc 1 bộ
    List<Flashcard> findByFlashcardSetId (Long flashcardSetId);
    // Đếm tổng số thẻ trong 1 bộ (phục vụ cho FlashcardSetServiceImpl)
    Integer countByFlashcardSetId(Long flashcardSetId);
    //lấy danh sách các thẻ đến hạn ôn tập của user
    List<Flashcard> findByFlashcardSetUserIdAndNextReviewDateLessThanEqual(Long userId, LocalDateTime date);

}
