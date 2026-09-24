package com.flashcard.english_card.repository;
import com.flashcard.english_card.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //dùng cho đăng nhập
    Optional<User> findByUsername(String name);
    //dùng cho đăng ký
    Boolean existsByUsername(String username);
    //dùng cho đăng ký
    Boolean existsByEmail(String email);
}
