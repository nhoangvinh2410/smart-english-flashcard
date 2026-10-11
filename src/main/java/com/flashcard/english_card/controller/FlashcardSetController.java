package com.flashcard.english_card.controller;

import com.flashcard.english_card.dto.request.FlashcardSetRequest;
import com.flashcard.english_card.dto.response.FlashcardSetDTO;
import com.flashcard.english_card.service.FlashcardSetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/user/{userId}")
    public ResponseEntity<FlashcardSetDTO> createSet(
            @Valid @RequestBody FlashcardSetRequest request,
            @PathVariable Long userId){
        return new ResponseEntity<>(flashcardSetService.createSet(request, userId), HttpStatus.CREATED);
    }

    //Lấy danh sách bộ thẻ cá nhân của user (kèm tổng số card)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FlashcardSetDTO>> getSetsByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(flashcardSetService.getSetsByUserId(userId));
    }

    //Lấy danh sách các bộ thẻ công khai (kèm tổng số card)
    @GetMapping("/public")
    public ResponseEntity<List<FlashcardSetDTO>> getPublicSets(){
        return ResponseEntity.ok(flashcardSetService.getPublicSets());
    }

    //xem chi tiết 1 bộ thẻ theo id
    @GetMapping("/{setId}")
    public ResponseEntity<FlashcardSetDTO> getSetById(@PathVariable Long setId){
        return ResponseEntity.ok(flashcardSetService.getSetById(setId));
    }

    //cập nhật bộ thẻ
    @PutMapping("/{setId}")
    public ResponseEntity<FlashcardSetDTO> updateSet(
            @PathVariable Long setId,
            @Valid @RequestBody FlashcardSetRequest request,
            @RequestParam Long userId){
        return ResponseEntity.ok(flashcardSetService.updateSet(setId, request, userId));
    }

    //xóa bộ thẻ (phải biết người xóa là ai)
    @DeleteMapping("/{setId}")
    public ResponseEntity<String> deleteSet(@PathVariable Long setId,
                                            @RequestParam Long userId){
        flashcardSetService.deleteSet(setId, userId);
        return ResponseEntity.ok("Đã xóa thành công bộ thẻ");
    }

}
