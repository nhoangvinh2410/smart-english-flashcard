package com.flashcard.english_card.mapper;

import com.flashcard.english_card.dto.request.VocabularyRequest;
import com.flashcard.english_card.dto.response.VocabularyDTO;
import com.flashcard.english_card.entity.Vocabulary;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class VocabularyMapper {
    public Vocabulary toEntity(VocabularyRequest request){
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setWord(request.getWord());
        vocabulary.setPronunciation(request.getPronunciation());
        vocabulary.setImageUrl(request.getImageUrl());
        vocabulary.setMeaning(request.getMeaning());
        vocabulary.setExample(request.getExample());
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
        return vocabularyDTO;
    }

    public void updateEntityFromRequest(VocabularyRequest request, Vocabulary vocabulary){
        if(request == null || vocabulary == null) return;
        vocabulary.setWord(request.getWord());
        vocabulary.setMeaning(request.getMeaning());
        vocabulary.setPronunciation(request.getPronunciation());
        vocabulary.setExample(request.getExample());
        vocabulary.setImageUrl(request.getImageUrl());
    }

}

