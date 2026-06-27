package com.flashcard.english_card.service;

import com.flashcard.english_card.entity.Category;
import com.flashcard.english_card.entity.Vocabulary;
import com.flashcard.english_card.repository.CategoryRepository;
import com.flashcard.english_card.repository.VocabularyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VocabularyServiceImpl implements VocabularyService{
    @Autowired
    private VocabularyRepository vocabularyRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Vocabulary createVocabulary(Vocabulary vocabulary, Long categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục này"));
        vocabulary.setCategory(category);
        vocabulary.setBoxLevel(1);
        vocabulary.setNextReviewDate(LocalDateTime.now());
        return vocabularyRepository.save(vocabulary);
    }

    @Override
    public List<Vocabulary> getVocabulariesToReview(){
        return vocabularyRepository.findVocabulariesToReview(LocalDateTime.now());
    }

    @Override
    public  Vocabulary updateVocabularyInfo(Long id, Vocabulary newInfo){
            Vocabulary vocabulary = vocabularyRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy từ đó để sửa!!"));

            vocabulary.setWord(newInfo.getWord());
            vocabulary.setMeaning(newInfo.getMeaning());
            vocabulary.setPronunciation(newInfo.getPronunciation());
            vocabulary.setExample(newInfo.getExample());
            return vocabularyRepository.save(vocabulary);
    }

    @Override
    public void deleteVocabulary(Long id){
        boolean exists = vocabularyRepository.existsById(id);
        if (!exists){
            throw new RuntimeException("Từ này không tồn tại");
        }
        vocabularyRepository.deleteById(id);
    }

    @Override
    public Vocabulary proessReview(Long id, boolean isCorrect){
        Vocabulary vocabulary = vocabularyRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Từ vựng không tồn tại"));
        if (isCorrect){
            vocabulary.setBoxLevel(Math.min(vocabulary.getBoxLevel() + 1, 5));
        }else {
            //sai thì phạt về hộp 1
            vocabulary.setBoxLevel(1);
        }
        int daysToAdd = 1;
        switch (vocabulary.getBoxLevel()){
            case 2: daysToAdd = 3; break;
            case 3: daysToAdd = 7; break;
            case 4: daysToAdd = 14; break;
            case 5: daysToAdd = 30; break;
        }
        vocabulary.setNextReviewDate(LocalDateTime.now().plusDays(daysToAdd));
        return vocabularyRepository.save(vocabulary);
    }





}
