package com.flashcard.english_card.controller;

import com.flashcard.english_card.dto.CategoryDTO;
import com.flashcard.english_card.dto.CategoryRequest;
import com.flashcard.english_card.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    //tạo thư mục mới
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryRequest request){
        return new ResponseEntity<>(categoryService.createCategory(request), HttpStatus.CREATED);
    }

    //lấy danh sách tất cả danh mục mục
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    //lấy thông tin 1 danh mục theo id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    //chỉnh sửa tên danh mục
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    ){
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    //xóa danh mục
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Đã xóa thành công danh mục");
    }




}
