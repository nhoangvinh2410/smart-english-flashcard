package com.flashcard.english_card.service;

import com.flashcard.english_card.entity.Vocabulary;

import java.util.List;

public interface VocabularyService {
    //thêm 1 từ vựng mới vào danh mục
    Vocabulary createVocabulary(Vocabulary vocabulary, Long categoryId);
    List<Vocabulary> getVocabulariesToReview();
    //xử lý khi user bấm quên hoặc thuộc lúc lật thẻ
    Vocabulary proessReview(Long id, boolean isCorrect);
    Vocabulary updateVocabularyInfo(Long id, Vocabulary newInfo);
    void deleteVocabulary(Long id);
}
