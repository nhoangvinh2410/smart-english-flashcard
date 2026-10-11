package com.flashcard.english_card.service;

import com.flashcard.english_card.dto.response.CategoryDTO;
import com.flashcard.english_card.dto.request.CategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryRequest request);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
    CategoryDTO updateCategory(Long id, CategoryRequest request);
    void deleteCategory(Long id);
}