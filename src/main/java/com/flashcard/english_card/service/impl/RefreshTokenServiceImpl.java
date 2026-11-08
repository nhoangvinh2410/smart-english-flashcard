package com.flashcard.english_card.service.impl;

import com.flashcard.english_card.entity.RefreshToken;
import com.flashcard.english_card.entity.User;
import com.flashcard.english_card.repository.RefreshTokenRepository;
import com.flashcard.english_card.repository.UserRepository;
import com.flashcard.english_card.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    // Lấy giá trị 7 ngày (604800000ms) từ file application.properties
    @Value("${app.jwt.refresh-expiration-milliseconds}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository,
                                   UserRepository userRepository){
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<RefreshToken> findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    @Override
    @Transactional
    public RefreshToken createRefreshToken(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy User với ID: " +userId));
        // Xóa các Refresh Token cũ của user này trước khi tạo token mới (tránh tồn đọng dữ liệu rác)
        refreshTokenRepository.deleteByUser(user);
        //Ép Hibernate đẩy lệnh DELETE xuống MySQL NGAY LẬP TỨC
        refreshTokenRepository.flush();
        //khởi tạo đối tượng ref mới
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user); //thuộc về user nào
        refreshToken.setRefreshToken(UUID.randomUUID().toString()); //sinh chuỗi UUID ngẫu nhiên
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs)); // Thời điểm hiện tại + 7 ngày

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional
    public RefreshToken verifyExpiration(RefreshToken refreshToken){
        //nếu thời điểm hết hạn (expiryDate)  < thời điểm hiện tại -> đã quá hạn
        // 04/12 < 5/12 (hiện tại) => hết hạn
        if(refreshToken.getExpiryDate().compareTo(Instant.now()) < 0){ //trả về số âm là hết hạn
            refreshTokenRepository.delete(refreshToken); //dọn dẹp ref hết hạn
            throw new RuntimeException("Refresh token đã hết hạn! Vui lòng thực hiện đăng nhập lại");
        }
        return refreshToken;
    }

    //xóa refreshToken
    @Override
    @Transactional
    public void deleteByRefreshToken(String refreshToken) {

         refreshTokenRepository.deleteByRefreshToken(refreshToken);
    }

}
