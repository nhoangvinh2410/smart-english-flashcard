package com.flashcard.english_card.service;

import com.flashcard.english_card.dto.response.CategoryDTO;
import com.flashcard.english_card.dto.request.CategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(String username, CategoryRequest request);
    List<CategoryDTO> getAllCategories(String username);
    CategoryDTO getCategoryById(Long id, String username);
    CategoryDTO updateCategory(Long id, String username, CategoryRequest request);
    void deleteCategory(Long id, String username);
}