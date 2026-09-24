package com.flashcard.english_card.repository;

import com.flashcard.english_card.entity.FlashcardSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlashcardSetRepository extends JpaRepository<FlashcardSet, Long> {
    //lấy danh sách toàn bộ thẻ do 1 User tạo
    List<FlashcardSet> findByUserId(Long userId);
    //lấy danh sách các thẻ bật chế độ công khai (true)
    List<FlashcardSet> findByIsPublicTrue();

}
