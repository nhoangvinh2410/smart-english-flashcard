package com.flashcard.english_card.service;

import com.flashcard.english_card.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    //tìm RefreshToken rừ CSDL
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    //Tạo mã Refresh Token mới cho User dựa vào userId và lưu xuống DB
    RefreshToken createRefreshToken(Long userId);
    //kiểm tra xem RefreshToken đã quá hạn 7 ngày chưa (nếu quá hạn sẽ tự xóa khỏi DB)
    RefreshToken verifyExpiration(RefreshToken refreshToken);
    //xóa ref để đăng xuất
    void deleteByRefreshToken(String refreshToken);
}
