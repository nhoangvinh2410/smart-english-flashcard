package com.flashcard.english_card.repository;

import com.flashcard.english_card.entity.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
    @Query("SELECT v FROM Vocabulary v WHERE v.nextReviewDate <= :now")
    List<Vocabulary> findVocabulariesToReview(@Param("now") LocalDateTime now);

    List<Vocabulary> findByCategoryId(Long categoryId);
}
