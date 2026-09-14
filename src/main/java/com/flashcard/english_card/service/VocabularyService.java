package com.flashcard.english_card.service;

import com.flashcard.english_card.dto.VocabularyDTO;
import com.flashcard.english_card.dto.VocabularyRequest;

import java.util.List;

public interface VocabularyService {
    //thêm 1 từ vựng mới vào danh mục
    VocabularyDTO createVocabulary(Long categoryId, VocabularyRequest request);
    List<VocabularyDTO> getVocabulariesToReview();
    //xử lý khi user bấm quên hoặc thuộc lúc lật thẻ
    VocabularyDTO processReview(Long id, boolean isCorrect);
    VocabularyDTO updateVocabularyInfo(Long id, VocabularyRequest request);
    void deleteVocabulary(Long id);
    List<VocabularyDTO> getVocabulariesByCategory(Long categoryId);
}
