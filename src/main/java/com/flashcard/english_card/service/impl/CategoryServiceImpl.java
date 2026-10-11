package com.flashcard.english_card.service.impl;

import com.flashcard.english_card.dto.response.CategoryDTO;
import com.flashcard.english_card.dto.request.CategoryRequest;
import com.flashcard.english_card.entity.Category;
import com.flashcard.english_card.mapper.CategoryMapper;
import com.flashcard.english_card.repository.CategoryRepository;
import com.flashcard.english_card.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper categoryMapper){

        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    //tạo danh mục
    @Override
    @Transactional
    public CategoryDTO createCategory(CategoryRequest request){
        if(categoryRepository.existsByName(request.getName())){
            throw new RuntimeException("Danh mục này đã tồn tại");
        }
        Category category = categoryMapper.toEntity(request);
        Category saveCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(saveCategory);
    }

    //lấy danh sách tất cả danh mục
    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::toDTO).toList();
    }

    //lấy chi tiết danh mục theo id
    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
        return categoryMapper.toDTO(category);
    }

    //cập nhật danh mục
    @Override
    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryRequest request){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
        //kiểm tra tên mới danh mục này có trùng với danh mục có trong DB hay chưa?
        if(!category.getName().equalsIgnoreCase(request.getName())
             && categoryRepository.existsByName(request.getName())){
            throw new RuntimeException("Tên danh mục này đã tồn tại");
        }
        categoryMapper.updateEntityFromRequest(request, category);
        Category updateCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(updateCategory);
    }

    //xóa danh mục
    @Override
    @Transactional
    public void deleteCategory(Long id){
        if(!categoryRepository.existsById(id)){
            throw new RuntimeException("Danh mục này không tồn tại");
        }
        categoryRepository.deleteById(id);
    }

}



