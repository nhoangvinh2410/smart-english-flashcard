package com.flashcard.english_card.service.impl;

import com.flashcard.english_card.dto.response.CategoryDTO;
import com.flashcard.english_card.dto.request.CategoryRequest;
import com.flashcard.english_card.entity.Category;
import com.flashcard.english_card.entity.User;
import com.flashcard.english_card.mapper.CategoryMapper;
import com.flashcard.english_card.repository.CategoryRepository;
import com.flashcard.english_card.repository.UserRepository;
import com.flashcard.english_card.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final UserRepository userRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper categoryMapper,
                               UserRepository userRepository){

        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.userRepository = userRepository;
    }

    //hàm dùng chung để lấy User (xem user có tồn tại)
    public User getCurrentUser(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy người dùng"));
    }

    //tạo danh mục
    @Override
    @Transactional
    public CategoryDTO createCategory(String username, CategoryRequest request){
        //lấy user ra
        User currentUser = getCurrentUser(username);
        //kiểm tra xem tài khoản hiện tại đã có danh mục vừa nhập ch request.getName
        if(categoryRepository.existsByNameIgnoreCaseAndUser(request.getName().trim(), currentUser)){
            throw new RuntimeException("Danh mục này đã tồn tại");
        }
        Category category = categoryMapper.toEntity(request);
        category.setUser(currentUser); //gán chủ sở hữu
        Category saveCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(saveCategory);
    }

    //lấy danh sách tất cả danh mục
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories(String username){
        User currentUser = getCurrentUser(username);
        List<Category> categories = categoryRepository.findByUser(currentUser);
        return categories.stream().map(categoryMapper::toDTO).toList();
    }

    //lấy chi tiết danh mục theo id
    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id, String username){
        User currentUser = getCurrentUser(username);
        Category category = categoryRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
        return categoryMapper.toDTO(category);
    }

    //cập nhật danh mục
    @Override
    @Transactional
    public CategoryDTO updateCategory(Long id, String username, CategoryRequest request){
        User currentUser = getCurrentUser(username);
        Category category = categoryRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
        //kiểm tra tên mới danh mục này có trùng với danh mục có trong DB hay chưa?
        if(!category.getName().equalsIgnoreCase(request.getName().trim())
             && categoryRepository.existsByNameIgnoreCaseAndUser(request.getName().trim(), currentUser)){
            throw new RuntimeException("Tên danh mục này đã tồn tại");
        }
        categoryMapper.updateEntityFromRequest(request, category);
        Category updateCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(updateCategory);
    }

    //xóa danh mục (chỉ xóa đc nếu đúng của mình
    @Override
    @Transactional
    public void deleteCategory(Long id, String username){
        User currentUser = getCurrentUser(username);
        Category category = categoryRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục hoặc bạn không có quyền xóa"));
        categoryRepository.deleteById(id);
    }

}



