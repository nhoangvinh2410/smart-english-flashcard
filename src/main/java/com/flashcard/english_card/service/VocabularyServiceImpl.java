package com.flashcard.english_card.service;

import com.flashcard.english_card.dto.VocabularyDTO;
import com.flashcard.english_card.dto.VocabularyRequest;
import com.flashcard.english_card.entity.Category;
import com.flashcard.english_card.entity.Vocabulary;
import com.flashcard.english_card.repository.CategoryRepository;
import com.flashcard.english_card.repository.VocabularyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VocabularyServiceImpl implements VocabularyService{
    @Autowired
    private VocabularyRepository vocabularyRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public VocabularyDTO createVocabulary(Long categoryId, VocabularyRequest request){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục này"));
        //nhận request và gán vào vocabulary
        Vocabulary vocabulary = new Vocabulary();
        vocabulary.setWord(request.getWord());
        vocabulary.setMeaning(request.getMeaning());
        vocabulary.setPronunciation(request.getPronunciation());
        vocabulary.setExample(request.getExample());
        vocabulary.setImageUrl(request.getImageUrl());

        vocabulary.setBoxLevel(1);
        vocabulary.setNextReviewDate(LocalDateTime.now());
        vocabulary.setCategory(category);

        Vocabulary saveVocabulary = vocabularyRepository.save(vocabulary);

        //hiển thị ở dạng json
        VocabularyDTO dto = new VocabularyDTO();
        dto.setId(saveVocabulary.getId());
        dto.setWord(saveVocabulary.getWord());
        dto.setMeaning(saveVocabulary.getMeaning());
        dto.setPronunciation(saveVocabulary.getPronunciation());
        dto.setExample(saveVocabulary.getExample());
        dto.setImageUrl(saveVocabulary.getImageUrl());
        dto.setBoxLevel(saveVocabulary.getBoxLevel());

        return dto;
    }

    //lấy từ vựng cần ôn tập
    @Override
    public List<VocabularyDTO> getVocabulariesToReview(){
        List<Vocabulary> vocabularies = vocabularyRepository.findVocabulariesToReview(LocalDateTime.now());
        return vocabularies.stream().map(vocab ->{
            VocabularyDTO dto = new VocabularyDTO();
            dto.setId(vocab.getId());
            dto.setWord(vocab.getWord());
            dto.setMeaning(vocab.getMeaning());
            dto.setImageUrl(vocab.getImageUrl());
            dto.setExample(vocab.getExample());
            dto.setBoxLevel(vocab.getBoxLevel());
            dto.setPronunciation(vocab.getPronunciation());
            return dto;
        }).collect(Collectors.toList());
        //Sau map(), kết quả vẫn đang ở dạng Stream
        //Đóng gói tất cả DTO vừa tạo thành một List để trả về
    }

    @Transactional
    @Override
    public  VocabularyDTO updateVocabularyInfo(Long id, VocabularyRequest request){
            Vocabulary vocabulary = vocabularyRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy từ đó để sửa!!"));

            //thay đổi dữ liểu ở database
            vocabulary.setWord(request.getWord());
            vocabulary.setMeaning(request.getMeaning());
            vocabulary.setPronunciation(request.getPronunciation());
            vocabulary.setImageUrl(request.getImageUrl());
            vocabulary.setExample(request.getExample());

            //ko cần save vì transactional đã tự động lưu
            //Vocabulary vocabularySave = vocabularyRepository.save(vocabulary);

            VocabularyDTO dto = new VocabularyDTO();
            dto.setId(vocabulary.getId());
            dto.setWord(vocabulary.getWord());
            dto.setMeaning(vocabulary.getMeaning());
            dto.setExample(vocabulary.getExample());
            dto.setImageUrl(vocabulary.getImageUrl());
            dto.setPronunciation(vocabulary.getPronunciation());
            dto.setBoxLevel(vocabulary.getBoxLevel());

            return dto;
    }

    @Override
    public void deleteVocabulary(Long id){
        boolean exists = vocabularyRepository.existsById(id);
        if (!exists){
            throw new RuntimeException("Từ này không tồn tại");
        }
        vocabularyRepository.deleteById(id);
    }

    @Transactional
    @Override
    public VocabularyDTO processReview(Long id, boolean isCorrect){
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

        //chuyển sang dto
        VocabularyDTO dto = new VocabularyDTO();
        dto.setId(vocabulary.getId());
        dto.setWord(vocabulary.getWord());
        dto.setMeaning(vocabulary.getMeaning());
        dto.setExample(vocabulary.getExample());
        dto.setImageUrl(vocabulary.getImageUrl());
        dto.setPronunciation(vocabulary.getPronunciation());
        dto.setBoxLevel(vocabulary.getBoxLevel());

        return dto;

    }

    //lấy tất cả từ vựng thuộc về 1 danh mục
    @Override
    public List<VocabularyDTO> getVocabulariesByCategory(Long categoryId){
        List<Vocabulary> vocabularies = vocabularyRepository.findByCategoryId(categoryId);
        return vocabularies.stream().map( vocab -> {
            VocabularyDTO dto = new VocabularyDTO();
            dto.setId(vocab.getId());
            dto.setWord(vocab.getWord());
            dto.setMeaning(vocab.getMeaning());
            dto.setImageUrl(vocab.getImageUrl());
            dto.setExample(vocab.getExample());
            dto.setBoxLevel(vocab.getBoxLevel());
            dto.setPronunciation(vocab.getPronunciation());
            return dto;
        }).collect(Collectors.toList());
    }


}
