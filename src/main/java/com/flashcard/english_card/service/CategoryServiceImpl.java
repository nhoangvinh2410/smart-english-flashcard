package com.flashcard.english_card.service;

import com.flashcard.english_card.dto.CategoryDTO;
import com.flashcard.english_card.dto.CategoryRequest;
import com.flashcard.english_card.entity.Category;
import com.flashcard.english_card.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository){

        this.categoryRepository = categoryRepository;
    }

    //tạo danh mục
    @Override
    public CategoryDTO createCategory(CategoryRequest request){
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category saveCategory = this.categoryRepository.save(category);

        CategoryDTO dto = new CategoryDTO();
        dto.setId(saveCategory.getId());
        dto.setName(saveCategory.getName());
        dto.setDescription(saveCategory.getDescription());
        return dto;
    }

    //lấy danh mục theo Id
    @Override
    public CategoryDTO getCategoryById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy danh mục với id:" +id));

        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }

    //lấy tất cả danh mục
    @Override
    public List<CategoryDTO> getAllCategories(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(category.getId());
            dto.setName(category.getName());
            dto.setDescription(category.getDescription());
            return dto;
            //gom tất cả danh muc hiện tại
        }).collect(Collectors.toList());
    }

    //Cập nhật danh mục
    @Override
    public CategoryDTO updateCategory(Long id, CategoryRequest request){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với id: " + id));
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category updataCategory = categoryRepository.save(category);

        CategoryDTO dto = new CategoryDTO();
        dto.setId(updataCategory.getId());
        dto.setName(updataCategory.getName());
        dto.setDescription(updataCategory.getDescription());
        return dto;
    }

    //Xóa danh mục
    @Override
    public void deleteCategory(Long id){
       if (!categoryRepository.existsById(id)){
            throw new RuntimeException("Không tìm thấy danh mục với id: " +id);
       }
       categoryRepository.deleteById(id);
    }

}


