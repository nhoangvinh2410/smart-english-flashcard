package com.flashcard.english_card.service;

import com.flashcard.english_card.dto.request.FlashcardSetRequest;
import com.flashcard.english_card.dto.response.FlashcardSetDTO;

import java.util.List;

public interface  FlashcardSetService {
    //Tạo bộ thẻ mới cho 1 user
    FlashcardSetDTO createSet(FlashcardSetRequest request, Long userId);
    //Lấy danh sách bộ thẻ cá nhân của user (kèm tổng số card)
    List<FlashcardSetDTO> getSetsByUserId(Long userId);
    //Lấy danh sách các bộ thẻ công khai (kèm tổng số card)
    List<FlashcardSetDTO> getPublicSets();
    //xem chi tiết 1 bộ thẻ theo id
     FlashcardSetDTO getSetById(Long setId);
     //cập nhật bộ thẻ
    FlashcardSetDTO updateSet(Long setId, FlashcardSetRequest request, Long userId);
    //xóa bộ thẻ (phải biết người xóa là ai)
    void deleteSet(Long setId, Long userId);
}
