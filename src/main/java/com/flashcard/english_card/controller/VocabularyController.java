package com.flashcard.english_card.controller;

import com.flashcard.english_card.dto.VocabularyDTO;
import com.flashcard.english_card.dto.VocabularyRequest;
import com.flashcard.english_card.service.VocabularyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vocabularies")
public class VocabularyController {
    private final  VocabularyService vocabularyService;

    @Autowired
    public VocabularyController(VocabularyService vocabularyService){
        this.vocabularyService = vocabularyService;
    }

    //tạo từ vựng thuộc danh mục
    @PostMapping("/category/{categoryId}")
    public ResponseEntity<VocabularyDTO> createVocabulary(
            @PathVariable Long categoryId,
            @Valid @RequestBody VocabularyRequest request
            ){
        // Gọi "anh đầu bếp" Service để xử lý logic gán dữ liệu và lưu DB
        VocabularyDTO createdVocab = vocabularyService.createVocabulary(categoryId, request);

        // Trả kết quả về cho Client kèm mã HTTP 201 (Created - Đã tạo thành công)
        return new ResponseEntity<>(createdVocab, HttpStatus.CREATED);
    }

    //lấy tất cả từ vựng thuộc về 1 danh mục
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<VocabularyDTO>> getVocabulariesByCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(vocabularyService.getVocabulariesByCategory(categoryId));
    }

    //chỉnh sửa nội dung từ vựng
    @PutMapping("/{id}")
    public ResponseEntity<VocabularyDTO> updateVocabularyInfo(
            @PathVariable Long id,
            @Valid @RequestBody VocabularyRequest request
    ){
        return ResponseEntity.ok(vocabularyService.updateVocabularyInfo(id, request));
    }

    //xóa từ vựng
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVocabulary(@PathVariable Long id){
        vocabularyService.deleteVocabulary(id);
        return ResponseEntity.ok("Da xoa thanh cong tu vung");
    }

    //lấy danh sách từ vựng cần ôn tập
    @GetMapping("/review")
    public ResponseEntity<List<VocabularyDTO>> getVocabulariesToReview(){
        return ResponseEntity.ok(vocabularyService.getVocabulariesToReview());
    }

    //đánh giá lật thẻ thuộc / không thuộc
    @PutMapping("/{id}/review")
    public ResponseEntity<VocabularyDTO> processReview(
        @PathVariable Long id,
        @RequestParam boolean isCorrect
    ){
        return ResponseEntity.ok(vocabularyService.processReview(id, isCorrect));
    }



    

}
