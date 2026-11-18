package com.flashcard.english_card.controller;

import com.flashcard.english_card.dto.request.FlashcardSetRequest;
import com.flashcard.english_card.dto.response.FlashcardSetDTO;
import com.flashcard.english_card.service.FlashcardSetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flashcard-sets")
@CrossOrigin(origins = "*")
public class FlashcardSetController {
    private final FlashcardSetService flashcardSetService;
    public FlashcardSetController(FlashcardSetService flashcardSetService){
        this.flashcardSetService = flashcardSetService;
    }

    //Tạo bộ thẻ mới cho 1 user
    @PostMapping
    public ResponseEntity<FlashcardSetDTO> createSet(
            Authentication authentication,
            @Valid @RequestBody FlashcardSetRequest request){
        String username = authentication.getName();
        return new ResponseEntity<>(flashcardSetService.createSet(request, username), HttpStatus.CREATED);
    }

       //Lấy danh sách bộ thẻ cá nhân của user (kèm tổng số card)
    @GetMapping("/my-sets")
    public ResponseEntity<List<FlashcardSetDTO>> getSetsByUserId(Authentication authentication){
        String username = authentication.getName();
        return ResponseEntity.ok(flashcardSetService.getMySets(username));
    }

    //Lấy danh sách các bộ thẻ công khai (kèm tổng số card)
    @GetMapping("/public")
    public ResponseEntity<List<FlashcardSetDTO>> getPublicSets(){
        return ResponseEntity.ok(flashcardSetService.getPublicSets());
    }

    //xem chi tiết 1 bộ thẻ theo id
    @GetMapping("/{setId}")
    public ResponseEntity<FlashcardSetDTO> getSetById(
            Authentication authentication,
            @PathVariable Long setId){
        String username = (authentication != null) ? authentication.getName() : null;
        return ResponseEntity.ok(flashcardSetService.getSetById(setId, username));
    }

    //cập nhật bộ thẻ
    @PutMapping("/{setId}")
    public ResponseEntity<FlashcardSetDTO> updateSet(
            Authentication authentication,
            @PathVariable Long setId,
            @Valid @RequestBody FlashcardSetRequest request){
        return ResponseEntity.ok(flashcardSetService.updateSet(setId, request, authentication.getName()));
    }

    //xóa bộ thẻ (phải biết người xóa là ai)
    @DeleteMapping("/{setId}")
    public ResponseEntity<String> deleteSet(
            Authentication authentication,
            @PathVariable Long setId){
        flashcardSetService.deleteSet(setId, authentication.getName());
        return ResponseEntity.ok("Đã xóa thành công bộ thẻ");
    }

}
