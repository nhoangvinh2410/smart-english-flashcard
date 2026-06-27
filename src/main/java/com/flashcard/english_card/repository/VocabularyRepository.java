package com.flashcard.english_card.repository;

import com.flashcard.english_card.entity.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
    List<Vocabulary> findVocabulariesToReview(LocalDateTime now);
}
