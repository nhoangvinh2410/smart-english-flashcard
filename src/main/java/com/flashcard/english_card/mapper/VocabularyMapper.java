package com.flashcard.english_card.mapper;

import com.flashcard.english_card.dto.request.VocabularyRequest;
import com.flashcard.english_card.dto.response.VocabularyDTO;
import com.flashcard.english_card.entity.Category;
import com.flashcard.english_card.entity.Vocabulary;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class VocabularyMapper {
    public Vocabulary toEntity(VocabularyRequest request, Category category){
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setWord(request.getWord());
        vocabulary.setPronunciation(request.getPronunciation());
        vocabulary.setImageUrl(request.getImageUrl());
        vocabulary.setMeaning(request.getMeaning());
        vocabulary.setExample(request.getExample());
        vocabulary.setCategory(category); //Gắn từ vựng này vào một Danh mục
        return vocabulary;
    }

    public VocabularyDTO toDTO(Vocabulary vocabulary){
        if(vocabulary == null) return null;
        VocabularyDTO vocabularyDTO = new VocabularyDTO();
        vocabularyDTO.setId(vocabulary.getId());
        vocabularyDTO.setWord(vocabulary.getWord());
        vocabularyDTO.setPronunciation(vocabulary.getPronunciation());
        vocabularyDTO.setImageUrl(vocabulary.getImageUrl());
        vocabularyDTO.setMeaning(vocabulary.getMeaning());
        vocabularyDTO.setExample(vocabulary.getExample());
        vocabularyDTO.setBoxLevel(vocabulary.getBoxLevel());
        /// Kiểm tra từ này có thuộc danh mục nào không
        if(vocabulary.getCategory() != null){
            vocabularyDTO.setCategoryId(vocabulary.getCategory().getId());
            vocabularyDTO.setCategoryName(vocabulary.getCategory().getName());
        }
        return vocabularyDTO;
    }
}

///xem lại cái này nha