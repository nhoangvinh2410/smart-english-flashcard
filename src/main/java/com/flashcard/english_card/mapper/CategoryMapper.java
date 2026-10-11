package com.flashcard.english_card.mapper;

import com.flashcard.english_card.dto.request.CategoryRequest;
import com.flashcard.english_card.dto.response.CategoryDTO;
import com.flashcard.english_card.entity.Category;
import com.flashcard.english_card.entity.Vocabulary;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {
    public Category toEntity(CategoryRequest request){
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return category;
    }

    public CategoryDTO toDTO(Category category){
        if(category == null) return null;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }

    public void updateEntityFromRequest(CategoryRequest request, Category category) {
        if(request == null || category == null) return;
        category.setName(request.getName());
        category.setDescription(request.getDescription());
    }

}

