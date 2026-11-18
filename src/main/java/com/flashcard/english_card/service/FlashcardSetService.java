package com.flashcard.english_card.service;

import com.flashcard.english_card.dto.request.FlashcardSetRequest;
import com.flashcard.english_card.dto.response.FlashcardSetDTO;

import java.util.List;

public interface  FlashcardSetService {
    //Tạo bộ thẻ mới cho 1 user
    FlashcardSetDTO createSet(FlashcardSetRequest request, String username);
    //Lấy danh sách bộ thẻ cá nhân của user đang đăng nhập
    List<FlashcardSetDTO> getMySets(String username);
    //Lấy danh sách các bộ thẻ công khai (kèm tổng số card)
    List<FlashcardSetDTO> getPublicSets();
    //xem chi tiết 1 bộ thẻ theo id (kiểm tra quyền nếu là bộ thẻ riêng tư)
     FlashcardSetDTO getSetById(Long setId, String username);
     //cập nhật bộ thẻ
    FlashcardSetDTO updateSet(Long setId, FlashcardSetRequest request, String username);
    //xóa bộ thẻ
    void deleteSet(Long setId, String username);
}
