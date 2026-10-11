package com.flashcard.english_card.controller;

import com.flashcard.english_card.dto.request.FlashcardRequest;
import com.flashcard.english_card.dto.response.FlashcardDTO;
import com.flashcard.english_card.service.FlashcardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flashcards")
@CrossOrigin(origins = "*")
public class FlashcardController {
    private final FlashcardService flashcardService;
    public FlashcardController(FlashcardService flashcardService){
        this.flashcardService = flashcardService;
    }

    //tạo thẻ mới trong bộ thẻ (setId là id của bộ thẻ)
    @PostMapping("set/{setId}/user/{userId}")
    public ResponseEntity<FlashcardDTO> createCard(
            @PathVariable Long setId,
            @Valid @RequestBody FlashcardRequest request,
            @PathVariable Long userId){
        return new ResponseEntity<>(flashcardService.createCard(setId, request, userId), HttpStatus.CREATED);
    }

    //lấy danh sách tất cả thẻ trong 1 bộ thẻ
    @GetMapping("set/{setId}/user/{userId}")
    public ResponseEntity<List<FlashcardDTO>> getCardsBySetId(
            @PathVariable Long setId,
            @PathVariable Long userId){
        return ResponseEntity.ok(flashcardService.getCardsBySetId(setId, userId));
    }

    //cập nhật nội dung thẻ
    @PutMapping("/{cardId}")
    public ResponseEntity<FlashcardDTO> updateCard(
            @PathVariable Long cardId,
            @Valid @RequestBody FlashcardRequest request,
            @RequestParam Long userId){
        return ResponseEntity.ok(flashcardService.updateCard(cardId, request, userId));
    }

    //xóa thẻ
    @DeleteMapping("/{cardId}")
    public ResponseEntity<String> deleteCard(
            @PathVariable Long cardId,
            @RequestParam Long userId){
        flashcardService.deleteCard(cardId, userId);
        return ResponseEntity.ok("Đã xóa thành công thẻ");
    }

    //ôn tập 1 thẻ (gửi kết quả nhớ/quên để tính toán lại lịch lặp lại ngắt quãng)
    @PostMapping("/{cardId}/review")
    public ResponseEntity<FlashcardDTO> review(
            @PathVariable Long cardId,
            @RequestParam boolean isCorrect,
            @RequestParam Long userId){
        return ResponseEntity.ok(flashcardService.review(cardId, isCorrect, userId));
    }

    //lấy các thẻ cần ôn tạp hôm nay của user
    @GetMapping("/due/user/{userId}")
    public ResponseEntity<List<FlashcardDTO>> getDueCards(@PathVariable Long userId){
        return ResponseEntity.ok(flashcardService.getDueCards(userId));
    }


}
