package com.flashcard.english_card.mapper;

import com.flashcard.english_card.dto.request.FlashcardRequest;
import com.flashcard.english_card.dto.response.FlashcardDTO;
import com.flashcard.english_card.entity.Flashcard;
import com.flashcard.english_card.entity.FlashcardSet;
import org.springframework.stereotype.Component;

@Component
public class FlashcardMapper {
    public Flashcard toEntity(FlashcardRequest request, FlashcardSet flashcardSet){
        Flashcard flashcard = new Flashcard();
        ///flashcardSet: Vì một thẻ Flashcard bắt buộc phải nằm trong 1 Bộ thẻ
        flashcard.setFlashcardSet(flashcardSet);
        flashcard.setWord(request.getWord());
        flashcard.setMeaning(request.getMeaning());
        flashcard.setPronunciation(request.getPronunciation());
        flashcard.setExample(request.getExample());
        flashcard.setImageUrl(request.getImageUrl());
        return  flashcard;
    }

    public FlashcardDTO toDTO(Flashcard flashcard){
        if(flashcard == null) return null;
        FlashcardDTO flashcardDTO = new FlashcardDTO();
        flashcardDTO.setId(flashcard.getId());

        if(flashcard.getFlashcardSet() != null){
            flashcardDTO.setFlashcardSetId(flashcard.getFlashcardSet().getId());
        }

        flashcardDTO.setWord(flashcard.getWord());
        flashcardDTO.setMeaning(flashcard.getMeaning());
        flashcardDTO.setPronunciation(flashcard.getPronunciation());
        flashcardDTO.setExample(flashcard.getExample());
        flashcardDTO.setImageUrl(flashcard.getImageUrl());
        flashcardDTO.setBoxLevel(flashcard.getBoxLevel());
        flashcardDTO.setNextReviewDate(flashcard.getNextReviewDate());
        return flashcardDTO;
    }
}
