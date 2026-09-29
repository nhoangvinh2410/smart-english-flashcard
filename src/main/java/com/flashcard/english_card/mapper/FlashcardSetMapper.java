package com.flashcard.english_card.mapper;

import com.flashcard.english_card.dto.request.FlashcardSetRequest;
import com.flashcard.english_card.dto.response.FlashcardSetDTO;
import com.flashcard.english_card.entity.FlashcardSet;
import com.flashcard.english_card.entity.User;
import org.springframework.stereotype.Component;

@Component
public class FlashcardSetMapper {
    public FlashcardSet toEntity(FlashcardSetRequest request, User user){
        FlashcardSet flashcardSet = new FlashcardSet();
        flashcardSet.setTitle(request.getTitle());
        flashcardSet.setDescription(request.getDescription());
        if(request.getIsPublic() != null) {
            flashcardSet.setPublic(request.getIsPublic());
        }
        flashcardSet.setUser(user);
        return flashcardSet;
    }

    public FlashcardSetDTO toDTO(FlashcardSet flashcardSet, Integer totalCards) {
        if (flashcardSet == null) return null;
        FlashcardSetDTO flashcardSetDTO = new FlashcardSetDTO();
        flashcardSetDTO.setId(flashcardSet.getId());
        flashcardSetDTO.setTitle(flashcardSet.getTitle());
        flashcardSetDTO.setDescription(flashcardSet.getDescription());
        flashcardSetDTO.setIsPublic(flashcardSet.isPublic());

        if (flashcardSet.getUser() != null) {
            flashcardSetDTO.setUserId(flashcardSet.getUser().getId());
            flashcardSetDTO.setAuthorName(flashcardSet.getUser().getUsername());
        }

        flashcardSetDTO.setCreateAt(flashcardSet.getCreateAt());
        return flashcardSetDTO;
    }
}
