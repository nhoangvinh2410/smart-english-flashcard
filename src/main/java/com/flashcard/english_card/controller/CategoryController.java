package com.flashcard.english_card.controller;

import com.flashcard.english_card.dto.response.CategoryDTO;
import com.flashcard.english_card.dto.request.CategoryRequest;
import com.flashcard.english_card.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    //tạo thư mục mới
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(
            Authentication authentication,
            @Valid @RequestBody CategoryRequest request){
        String username = authentication.getName();
        CategoryDTO created = categoryService.createCategory(username, request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    //lấy danh sách tất cả danh mục mục
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(Authentication authentication){
        String username = authentication.getName();
        return ResponseEntity.ok(categoryService.getAllCategories(username));
    }

    //lấy thông tin 1 danh mục theo id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(
            Authentication authentication,
            @PathVariable Long id){
        String username = authentication.getName();
        return ResponseEntity.ok(categoryService.getCategoryById(id, username));
    }

    //cập nhật tên danh mục
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            Authentication authentication,
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    ){
        String username = authentication.getName();
        return ResponseEntity.ok(categoryService.updateCategory(id, username, request));
    }

    //xóa danh mục
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
            Authentication authentication,
            @PathVariable Long id){
        String username = authentication.getName();
        categoryService.deleteCategory(id, username);
        return ResponseEntity.ok("Đã xóa thành công danh mục");
    }

}
