package com.flashcard.english_card.repository;

import com.flashcard.english_card.entity.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {
    //phục vụ tính năng gợi ý từ vựng
    List<Vocabulary> findByWordContainingIgnoreCase(String keyword);
    //kiểm tra từ đã tồn tại trong từ điển h
    boolean existsByWord(String word);
}
