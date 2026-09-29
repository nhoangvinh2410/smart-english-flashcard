package com.flashcard.english_card.mapper;

import com.flashcard.english_card.dto.request.CategoryRequest;
import com.flashcard.english_card.dto.response.CategoryDTO;
import com.flashcard.english_card.entity.Category;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

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
}

