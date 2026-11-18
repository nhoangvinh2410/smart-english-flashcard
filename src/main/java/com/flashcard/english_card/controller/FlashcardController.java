package com.flashcard.english_card.controller;

import com.flashcard.english_card.dto.request.FlashcardRequest;
import com.flashcard.english_card.dto.response.FlashcardDTO;
import com.flashcard.english_card.service.FlashcardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    @PostMapping("/set/{setId}")
    public ResponseEntity<FlashcardDTO> createCard(
            Authentication authentication,
            @PathVariable Long setId,
            @Valid @RequestBody FlashcardRequest request){
        String username = authentication.getName();
        return new ResponseEntity<>(flashcardService.createCard(setId, request, username), HttpStatus.CREATED);
    }

    //lấy danh sách tất cả thẻ trong 1 bộ thẻ
    @GetMapping("/set/{setId}")
    public ResponseEntity<List<FlashcardDTO>> getCardsBySetId(
            Authentication authentication,
            @PathVariable Long setId){
        String username = authentication.getName();
        return ResponseEntity.ok(flashcardService.getCardsBySetId(setId, username));
    }

    //cập nhật nội dung thẻ
    @PutMapping("/{cardId}")
    public ResponseEntity<FlashcardDTO> updateCard(
            Authentication authentication,
            @PathVariable Long cardId,
            @Valid @RequestBody FlashcardRequest request){
        String username = authentication.getName();
        return ResponseEntity.ok(flashcardService.updateCard(cardId, request, username));
    }

    //xóa thẻ
    @DeleteMapping("/{cardId}")
    public ResponseEntity<String> deleteCard(
            Authentication authentication,
            @PathVariable Long cardId){
        flashcardService.deleteCard(cardId, authentication.getName());
        return ResponseEntity.ok("Đã xóa thành công thẻ");
    }

    //ôn tập 1 thẻ (gửi kết quả nhớ/quên để tính toán lại lịch lặp lại ngắt quãng)
    @PostMapping("/{cardId}/review")
    public ResponseEntity<FlashcardDTO> review(
            Authentication authentication,
            @PathVariable Long cardId,
            @RequestParam boolean isCorrect){
        String username = authentication.getName();
        return ResponseEntity.ok(flashcardService.review(cardId, isCorrect, username));
    }

    //lấy các thẻ cần ôn tạp hôm nay của user
    @GetMapping("/due")
    public ResponseEntity<List<FlashcardDTO>> getDueCards(Authentication authentication){
        return ResponseEntity.ok(flashcardService.getDueCards(authentication.getName()));
    }


}
