package com.flashcard.english_card.repository;

import com.flashcard.english_card.entity.RefreshToken;
import com.flashcard.english_card.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    // Xóa tất cả các Refresh Token của 1 User cụ thể (dùng khi Logout hoặc dọn token rác)
    void deleteByUser(User user);
    //xóa ref khi người dùng đăng xuất (logout)
    void deleteByRefreshToken(String refreshToken);
}
