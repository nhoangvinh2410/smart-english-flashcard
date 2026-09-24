package com.flashcard.english_card.repository;

import com.flashcard.english_card.entity.ReviewRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRecordRepository extends JpaRepository<ReviewRecord, Long> {
    //xem nhật ký ôn tập của 1 user
    List<ReviewRecord> findByUserId(Long id);
    //xem nhật ký ôn tập của 1 flashcard cụ thể
    List<ReviewRecord> findByFlashcardId(Long flashcardId);
}
