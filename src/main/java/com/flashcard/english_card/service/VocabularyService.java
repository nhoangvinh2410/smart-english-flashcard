package com.flashcard.english_card.service;

import com.flashcard.english_card.dto.response.VocabularyDTO;
import com.flashcard.english_card.dto.request.VocabularyRequest;

import java.util.List;

public interface VocabularyService {
    //thêm 1 từ vựng mới vào từ điển hệ thống
    VocabularyDTO createVocabulary(VocabularyRequest request);
    //tìm kiếm từ vựng theo từ khóa
    List<VocabularyDTO> searchVocabularies(String keyword);
    //lấy thông tin chi tiết 1 từ vựng theo id
    VocabularyDTO getVocabularyById(Long id);
    //cập nhật thông tin từ vựng
    VocabularyDTO updateVocabulary(Long id, VocabularyRequest request);
    //xóa từ vựng
    void deleteVocabulary(Long id);
}
