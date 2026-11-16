package com.flashcard.english_card.repository;

import com.flashcard.english_card.entity.Category;
import com.flashcard.english_card.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Lấy tất cả danh mục của 1 user cụ thể
    List<Category> findByUser(User user);
    // Kiểm tra trùng tên danh mục trong phạm vi của chính user đó (không phân biệt hoa thường)
    boolean existsByNameIgnoreCaseAndUser(String name, User user);
    // Tìm danh mục theo ID VÀ phải thuộc về đúng user đó
    Optional<Category> findByIdAndUser(Long id, User user);
}
