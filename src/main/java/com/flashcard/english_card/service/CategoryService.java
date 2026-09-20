package com.flashcard.english_card.service;

import com.flashcard.english_card.dto.CategoryDTO;
import com.flashcard.english_card.dto.CategoryRequest;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryRequest request);
    CategoryDTO getCategoryById(Long id);
    CategoryDTO updateCategory(Long id, CategoryRequest request);
    List<CategoryDTO> getAllCategories();
    void deleteCategory(Long id);
}