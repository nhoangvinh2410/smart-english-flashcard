package com.flashcard.english_card.service;

import com.flashcard.english_card.dto.request.FlashcardRequest;
import com.flashcard.english_card.dto.response.FlashcardDTO;

import java.util.List;

public interface FlashcardService {
    //tạo thẻ mới trong bộ thẻ (setId là id của bộ thẻ)
    FlashcardDTO createCard(Long setId, FlashcardRequest request, Long userId);
    //lấy danh sách tất cả thẻ trong 1 bộ thẻ
    List<FlashcardDTO> getCardsBySetId(Long setId, Long userId);
    //cập nhật nội dung thẻ
    FlashcardDTO updateCard(Long cardId, FlashcardRequest request, Long userId);
    //xóa thẻ
    void deleteCard(Long cardId, Long userId);
    //ôn tập 1 thẻ
    FlashcardDTO review(Long cardId, boolean isCorrect, Long userId);
    //lấy các thẻ cần ôn tạp hôm nay của user
    List<FlashcardDTO> getDueCards(Long userId);
}
