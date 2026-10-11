package com.flashcard.english_card.controller;

import com.flashcard.english_card.dto.response.VocabularyDTO;
import com.flashcard.english_card.dto.request.VocabularyRequest;
import com.flashcard.english_card.service.VocabularyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vocabularies")
@CrossOrigin(origins = "*")
public class VocabularyController {
    private final  VocabularyService vocabularyService;

    @Autowired
    public VocabularyController(VocabularyService vocabularyService){

        this.vocabularyService = vocabularyService;
    }

    //thêm 1 từ vựng mới vào từ điển
    @PostMapping
    public ResponseEntity<VocabularyDTO> createVocabulary(
            @Valid @RequestBody VocabularyRequest request
            ){
        // Gọi "anh đầu bếp" Service để xử lý logic gán dữ liệu và lưu DB
        VocabularyDTO createdVocab = vocabularyService.createVocabulary(request);

        // Trả kết quả về cho Client kèm mã HTTP 201 (Created - Đã tạo thành công)
        return new ResponseEntity<>(createdVocab, HttpStatus.CREATED);
    }

    //chỉnh sửa nội dung từ vựng (chỉ Admin)
    @PutMapping("/{id}")
    public ResponseEntity<VocabularyDTO> updateVocabulary(
            @PathVariable Long id,
            @Valid @RequestBody VocabularyRequest request
    ){
        return ResponseEntity.ok(vocabularyService.updateVocabulary(id, request));
    }

    //xóa từ vựng theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVocabulary(@PathVariable Long id){
        vocabularyService.deleteVocabulary(id);
        return ResponseEntity.ok("Đã xóa thành công từ vựng");
    }

    //tìm kiếm từ vựng theo từ khóa
    @GetMapping("/search")
    public ResponseEntity<List<VocabularyDTO>> searchVocabularies(@RequestParam String keyword){
        return ResponseEntity.ok(vocabularyService.searchVocabularies(keyword));
    }

    //lấy thông tin chi tiết 1 từ vựng theo id
    @GetMapping("/{id}")
    public ResponseEntity<VocabularyDTO> getVocabularyById(@PathVariable Long id){
        return ResponseEntity.ok(vocabularyService.getVocabularyById(id));
    }

}
