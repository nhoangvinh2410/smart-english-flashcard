package com.flashcard.english_card.repository;

import com.flashcard.english_card.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
