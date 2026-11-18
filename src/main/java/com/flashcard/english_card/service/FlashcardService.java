package com.flashcard.english_card.service;

import com.flashcard.english_card.dto.request.FlashcardRequest;
import com.flashcard.english_card.dto.response.FlashcardDTO;

import java.util.List;

public interface FlashcardService {
    //tạo thẻ mới category -> flashcard
    FlashcardDTO createCard(Long setId, FlashcardRequest request, String username);
    //lấy danh sách tất cả thẻ trong 1 bộ thẻ
    List<FlashcardDTO> getCardsBySetId(Long setId, String username);
    //cập nhật nội dung thẻ
    FlashcardDTO updateCard(Long cardId, FlashcardRequest request, String username);
    //xóa thẻ
    void deleteCard(Long cardId, String username);
    //ôn tập 1 thẻ
    FlashcardDTO review(Long cardId, boolean isCorrect, String username);
    //lấy các thẻ cần ôn tạp hôm nay của user
    List<FlashcardDTO> getDueCards(String username);
}
