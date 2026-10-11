package com.flashcard.english_card.service.impl;

import com.flashcard.english_card.dto.response.VocabularyDTO;
import com.flashcard.english_card.dto.request.VocabularyRequest;
import com.flashcard.english_card.entity.Vocabulary;
import com.flashcard.english_card.mapper.VocabularyMapper;
import com.flashcard.english_card.repository.VocabularyRepository;
import com.flashcard.english_card.service.VocabularyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VocabularyServiceImpl implements VocabularyService {
    private final VocabularyRepository vocabularyRepository;
    private final VocabularyMapper vocabularyMapper;

    public VocabularyServiceImpl(VocabularyRepository vocabularyRepository,
                                 VocabularyMapper vocabularyMapper){
        this.vocabularyRepository = vocabularyRepository;
        this.vocabularyMapper = vocabularyMapper;
    }

    //thêm 1 từ vựng mới vào danh mục
    @Override
    @Transactional
    public VocabularyDTO createVocabulary(VocabularyRequest request){
        if(vocabularyRepository.existsByWord(request.getWord())){
            throw new RuntimeException("Từ này đã tồn tại trong kho từ vựng");
        }
        Vocabulary vocabulary = vocabularyMapper.toEntity(request);
        Vocabulary saveVoca = vocabularyRepository.save(vocabulary);
        return vocabularyMapper.toDTO(saveVoca);
    }

    //tìm kiếm từ vựng theo từ khóa
    @Override
    @Transactional(readOnly = true)
    public List<VocabularyDTO> searchVocabularies(String keyword){
        //người dùng nhập vào từ khóa
        if(keyword == null || keyword.trim().isEmpty()){
            return List.of(); //trả về danh sách rỗng ko cần truy vấn DB
        }
        List <Vocabulary> vocabularies = vocabularyRepository.findByWordContainingIgnoreCase(keyword.trim());
        return vocabularies.stream().map(vocabularyMapper::toDTO).toList();
    }

    //lấy thông tin tiết 1 từ vựng theo id
    @Override
    @Transactional(readOnly = true)
    public VocabularyDTO getVocabularyById(Long id){
        Vocabulary vocabulary = vocabularyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy từ vựng"));
        return vocabularyMapper.toDTO(vocabulary);
    }

    //cập nhật thông tin
    @Override
    @Transactional
    public VocabularyDTO updateVocabulary(Long id, VocabularyRequest request){
        Vocabulary vocabulary = vocabularyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy từ vựng"));
        vocabularyMapper.updateEntityFromRequest(request, vocabulary);
        Vocabulary updateVoca = vocabularyRepository.save(vocabulary);
        return vocabularyMapper.toDTO(updateVoca);
    }

    //xóa từ vựng
    @Override
    @Transactional
    public void deleteVocabulary(Long id) {
        if(!vocabularyRepository.existsById(id)){
            throw new RuntimeException("Từ này không tồn tại");
        }
        vocabularyRepository.deleteById(id);
    }

}


