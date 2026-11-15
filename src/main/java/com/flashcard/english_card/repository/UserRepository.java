package com.flashcard.english_card.repository;
import com.flashcard.english_card.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //dùng cho đăng nhập, để tìm kiểm người dùng với tên "name"
    //nếu có trả về user không thì trả về trạng thái rỗng
    Optional<User> findByUsername(String username);
    //dùng cho đăng ký kiểm tra name
    Boolean existsByUsername(String username);
    //dùng cho đăng ký dùng kiểm tra email
    Boolean existsByEmail(String email);
}
