package com.flashcard.english_card.service.impl;

import com.flashcard.english_card.dto.request.FlashcardSetRequest;
import com.flashcard.english_card.dto.response.FlashcardSetDTO;
import com.flashcard.english_card.entity.Category;
import com.flashcard.english_card.entity.FlashcardSet;
import com.flashcard.english_card.entity.User;
import com.flashcard.english_card.mapper.FlashcardSetMapper;
import com.flashcard.english_card.repository.CategoryRepository;
import com.flashcard.english_card.repository.FlashcardRepository;
import com.flashcard.english_card.repository.FlashcardSetRepository;
import com.flashcard.english_card.repository.UserRepository;
import com.flashcard.english_card.service.FlashcardSetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FlashcardSetServiceImpl implements FlashcardSetService {
    private final FlashcardSetRepository flashcardSetRepository;
    private final FlashcardSetMapper flashcarSetdMapper;
    private final UserRepository userRepository;
    private final FlashcardRepository flashcardRepository;
    private final CategoryRepository categoryRepository;

    public FlashcardSetServiceImpl(FlashcardSetRepository flashcardSetRepository,
                                   FlashcardSetMapper flashcardSetMapper,
                                   UserRepository userRepository,
                                   FlashcardRepository flashcardRepository,
                                   CategoryRepository categoryRepository) {
        this.flashcardSetRepository = flashcardSetRepository;
        this.flashcarSetdMapper = flashcardSetMapper;
        this.userRepository = userRepository;
        this.flashcardRepository = flashcardRepository;
        this.categoryRepository = categoryRepository;
    }

    //Tạo bộ thẻ mới cho 1 user
    @Override
    @Transactional
    public FlashcardSetDTO createSet(FlashcardSetRequest request, String username) {
        // bộ thẻ phải do người dùng tạo ra (đã đăng ký tài khoản)
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với tên: " + username));
        // Tìm Category từ database
        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + request.getCategoryId()));
            if (!category.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Bạn không có quyền sử dụng danh mục này");
            }
        }
        //người dùng có tồn tại thì tạo đc bộ thẻ
        //chuyển từ request -> dto
        FlashcardSet flashcardSet = flashcarSetdMapper.toEntity(request, user, category);
        //lưu xuống database
        FlashcardSet saveFlashcardSet = flashcardSetRepository.save(flashcardSet);
        //database -> dto
        return flashcarSetdMapper.toDTO(saveFlashcardSet, 0);
    }

    //Lấy danh sách bộ thẻ cá nhân của user
    @Override
    @Transactional(readOnly = true)
    public List<FlashcardSetDTO> getMySets(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với tên: " + username));
        //lấy tất cả bộ thẻ thuộc về user
        List<FlashcardSet> sets = flashcardSetRepository.findByUserId(user.getId());
        //đếm tổng số card trong từng bộ thẻ map -> dto
        return sets.stream().map(set -> {
            Integer totalCards = flashcardRepository.countByFlashcardSetId(set.getId()); //set là bộ thẻ
            return flashcarSetdMapper.toDTO(set, totalCards);
        }).toList();
    }

    //Lấy danh sách các bộ thẻ công khai (kèm tổng số card)
    @Override
    @Transactional(readOnly = true)
    public List<FlashcardSetDTO> getPublicSets() {
        //lấy tất cả bộ công khai có isPublic = true
        List<FlashcardSet> publicSets = flashcardSetRepository.findByIsPublicTrue();
        //đếm tổng số card trong bộ thẻ map -> dto
        return publicSets.stream().map(set -> {
            Integer totalCards = flashcardRepository.countByFlashcardSetId(set.getId());
            return flashcarSetdMapper.toDTO(set, totalCards);
        }).toList();
    }

    //xem chi tiết 1 bộ thẻ theo id
    @Override
    @Transactional(readOnly = true)
    public FlashcardSetDTO getSetById(Long setId, String username) {
        //tìm bộ thẻ theo setId, nếu không thấy thì báo lỗi
        FlashcardSet set = flashcardSetRepository.findById(setId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bộ thẻ với ID: " + setId));
        if (!set.isPublic()) {
            if (username == null || !set.getUser().getUsername().equals(username)) {
                throw new RuntimeException("Bộ thẻ này ở chế độ riêng tư, bạn không có quyền xem");
            }
        }
        Integer totalCards = flashcardRepository.countByFlashcardSetId(set.getId());
        return flashcarSetdMapper.toDTO(set, totalCards);
    }

    //cập nhật bộ thẻ
    @Override
    @Transactional
    public FlashcardSetDTO updateSet(Long setId, FlashcardSetRequest request, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với tên: " + username));
        FlashcardSet flashcardSet = flashcardSetRepository.findById(setId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bộ thẻ"));
        //kiểm tra an toàn (chỉ có user chính chủ mới cập nhật đc danh mục cá nhân)
        if(!flashcardSet.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Bạn không có quyền chỉnh sửa bộ thẻ này");
        }
        // Tìm Category mới nếu request có gửi categoryId
        Category category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + request.getCategoryId()));
            if (!category.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Bạn không có quyền sử dụng danh mục này");
            }
        }
        flashcarSetdMapper.updateEntityFromRequest(request, flashcardSet, category);
        FlashcardSet updateFlashcardSet = flashcardSetRepository.save(flashcardSet);
        Integer totalCards = flashcardRepository.countByFlashcardSetId(setId);
        return flashcarSetdMapper.toDTO(updateFlashcardSet,totalCards);
    }

    //xóa bộ thẻ
    @Override
    @Transactional
    public void deleteSet(Long setId, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với tên: " + username));
        FlashcardSet flashcardSet = flashcardSetRepository.findById(setId)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy bộ thẻ"));
        //kiểm tra an toàn (chỉ có user chính chủ mới xóa đc danh mục cá nhân)
        if(!flashcardSet.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Bạn không có quyền xóa bộ thẻ này");
        }
        flashcardSetRepository.delete(flashcardSet);
    }

}
