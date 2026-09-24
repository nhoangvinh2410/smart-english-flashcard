package com.flashcard.english_card.repository;

import com.flashcard.english_card.entity.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    List<Flashcard> findByFlashcardSetId (Long flashcardSetId);
}
