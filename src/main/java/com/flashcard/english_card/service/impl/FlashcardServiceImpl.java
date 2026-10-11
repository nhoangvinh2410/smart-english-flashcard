package com.flashcard.english_card.service.impl;

import com.flashcard.english_card.dto.request.FlashcardRequest;
import com.flashcard.english_card.dto.response.FlashcardDTO;
import com.flashcard.english_card.entity.Flashcard;
import com.flashcard.english_card.entity.FlashcardSet;
import com.flashcard.english_card.mapper.FlashcardMapper;
import com.flashcard.english_card.repository.FlashcardRepository;
import com.flashcard.english_card.repository.FlashcardSetRepository;
import com.flashcard.english_card.service.FlashcardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlashcardServiceImpl implements FlashcardService {
    private final FlashcardRepository flashcardRepository;
    private final FlashcardMapper flashcardMapper;
    private final FlashcardSetRepository flashcardSetRepository;

    public FlashcardServiceImpl(FlashcardRepository flashcardRepository,
                                   FlashcardMapper flashcardMapper,
                                   FlashcardSetRepository flashcardSetRepository){
        this.flashcardRepository = flashcardRepository;
        this.flashcardMapper = flashcardMapper;
        this.flashcardSetRepository = flashcardSetRepository;
    }

    //tạo thẻ mới trong bộ thẻ (setId là id của bộ thẻ)
    @Override
    @Transactional
    public FlashcardDTO createCard(Long setId, FlashcardRequest request, Long userId){
        FlashcardSet set = validateSetOwnership(setId, userId);
        Flashcard card = flashcardMapper.toEntity(request, set);
        card.setBoxLevel(1);
        card.setNextReviewDate(LocalDateTime.now());
        Flashcard saveCard = flashcardRepository.save(card);
        return flashcardMapper.toDTO(saveCard);
    }

    //lấy danh sách thẻ trong 1 bộ thẻ
    @Override
    public List<FlashcardDTO> getCardsBySetId(Long setId, Long userId){
        validateSetOwnership(setId, userId);
        List<Flashcard> cards = flashcardRepository.findByFlashcardSetId(setId);
        return cards.stream().map(flashcardMapper::toDTO).toList();
    }

    //cập nhật nội dung thẻ
    @Override
    @Transactional
    public FlashcardDTO updateCard(Long cardId, FlashcardRequest request, Long userId){
        //tìm thẻ cũ từ Database (Đã có ID, BoxLevel, NextReviewDate...)
        Flashcard card  = validateCardOwnership(cardId, userId);
        //mapper cập nhật thông tin mới vào thẻ cũ
        flashcardMapper.updateEntityFromRequest(request, card);
        //lưu lại -> dto
        Flashcard updateCard = flashcardRepository.save(card);
        return flashcardMapper.toDTO(updateCard);
    }

    //xóa thẻ
    @Override
    @Transactional
    public void deleteCard(Long cardId, Long userId){
        //kiểm tra quyền sở hữu
        Flashcard card = validateCardOwnership(cardId, userId);
        flashcardRepository.delete(card);
    }

    //ôn tập 1 thẻ
    @Override
    @Transactional
    public FlashcardDTO review(Long cardId, boolean isCorrect, Long userId){
        //kiểm tra quyền sở hữu
        Flashcard card = validateCardOwnership(cardId, userId);
        //tính toán boxlevel mới
        //trả lời đúng tăng level (tối đa 5), sai thì reset về 1
        int currentLevel = card.getBoxLevel();
        int newLevel = isCorrect ? Math.min(currentLevel + 1, 5) : 1;

        //tính số ngày ôn tập tiếp theo
        int daysToAdd = switch (newLevel){
            case 1 -> 1;
            case 2 -> 3;
            case 3 -> 7;
            case 4 -> 14;
            case 5 -> 30;
            default -> 1;
        };
        //cập nhật lại
        card.setBoxLevel(newLevel);
        card.setNextReviewDate(LocalDateTime.now().plusDays(daysToAdd));
        //lưu lại và -> dto
        Flashcard updateCard = flashcardRepository.save(card);
        return flashcardMapper.toDTO(updateCard);
    }

    //lấy các thẻ cần ôn tập hôm nay của user
    @Override
    @Transactional(readOnly = true)
    public List<FlashcardDTO> getDueCards(Long userId){
        //lấy danh sách thẻ có ngày hẹn ôn (nhỏ hơn hoặc bằng "thời điểm hiện tại")
        List<Flashcard> dueCards = flashcardRepository
                .findByFlashcardSetUserIdAndNextReviewDateLessThanEqual(userId, LocalDateTime.now());
        return dueCards.stream().map(flashcardMapper::toDTO).toList();
    }

    private FlashcardSet validateSetOwnership(Long setId, Long userId) {
        FlashcardSet set = flashcardSetRepository.findById(setId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bộ thẻ!"));

        if (!set.getUser().getId().equals(userId)) {
            throw new RuntimeException("Bạn không có quyền thao tác trên bộ thẻ này!");
        }
        return set;
    }

    private Flashcard validateCardOwnership(Long cardId, Long userId) {
        Flashcard card = flashcardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thẻ!"));

        if (!card.getFlashcardSet().getUser().getId().equals(userId)) {
            throw new RuntimeException("Bạn không có quyền thao tác trên thẻ này!");
        }
        return card;
    }
}
