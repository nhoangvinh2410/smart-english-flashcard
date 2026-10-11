package com.flashcard.english_card.mapper;

import com.flashcard.english_card.dto.request.FlashcardSetRequest;
import com.flashcard.english_card.dto.response.FlashcardSetDTO;
import com.flashcard.english_card.entity.Category;
import com.flashcard.english_card.entity.FlashcardSet;
import com.flashcard.english_card.entity.User;

import org.springframework.stereotype.Component;

@Component
public class FlashcardSetMapper {
    public FlashcardSet toEntity(FlashcardSetRequest request, User user, Category category){
        FlashcardSet flashcardSet = new FlashcardSet();
        flashcardSet.setTitle(request.getTitle());
        flashcardSet.setDescription(request.getDescription());
        if(request.getIsPublic() != null) {
            flashcardSet.setPublic(request.getIsPublic());
        }
        flashcardSet.setUser(user);
        flashcardSet.setCategory(category);
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

        //lấy thông tin Category trả về DTO
        if (flashcardSet.getCategory() != null) {
            flashcardSetDTO.setCategoryId(flashcardSet.getCategory().getId());
            flashcardSetDTO.setCategoryName(flashcardSet.getCategory().getName());
        }

        flashcardSetDTO.setCreateAt(flashcardSet.getCreateAt());
        flashcardSetDTO.setTotalCards(totalCards);
        return flashcardSetDTO;
    }


    /// ///xem lại cái này nha
    public void updateEntityFromRequest(FlashcardSetRequest request, FlashcardSet flashcardSet, Category category) {
        if (request == null || flashcardSet == null) return;

        flashcardSet.setTitle(request.getTitle());
        flashcardSet.setDescription(request.getDescription());

        //chỉ cập nhật trạng thái Public nếu request có gửi lên
        if (request.getIsPublic() != null) {
            flashcardSet.setPublic(request.getIsPublic());
        }

        if (category != null) {
            flashcardSet.setCategory(category);
        }
    }

}
