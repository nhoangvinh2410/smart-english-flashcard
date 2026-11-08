package com.flashcard.english_card.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
//tạo ra token mới
//đọc, kiểm tra có bị làm giả hay hết hạn ko
//các hàm bộ trọ dùng để kiểm tra
public class JwtTokenProvider {
    //đọc chuỗi khóa bí mật dùng để làm chữ ký mã hóa Token
    @Value("${app.jwt.secret}")
    private String jwtSecret;
    //đọc thời gian hết hạn accessToken
    @Value("${app.jwt.expiration-milliseconds:900000}")
    private long accessTokenExpirationMs;

    //chuyển chuỗi bí mật jwtSecret -> đối tượng SecretKey
    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    //hàm in acc tồn tại trong 15 phút
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpirationMs);
        return Jwts.builder()
                .setSubject(username) // Đặt username vào phần ruột (Payload) của Token để đánh dấu Token này thuộc về ai
                .setIssuedAt(now) // Đóng dấu thời gian phát hành Token
                .setExpiration(expiryDate) // Đóng dấu thời hạn Token bị vô hiệu hóa
                .signWith(getSigningKey()) // Dùng khóa bí mật dập seal / ký tên chống giả mạo
                .compact(); // Nén tất cả lại thành chuỗi chuỗi chữ dài dạng JWT (eyJhbGci...)
    }


        //hàm giải mã token để móc username ra lại
    public String getUsernameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Đưa khóa bí mật vào để đối chiếu chữ ký
                .build() // Dựng bộ bóc tách JWT
                .parseClaimsJws(token)  // Kiểm tra và bóc tách chuỗi JWT gửi lên
                .getBody()  // Lấy ra phần ruột (Payload)
                .getSubject(); // Trích xuất ra lại username đã cất lúc tạo Token
    }//đầu ra là username

    //Hàm kiểm tra Token có hợp lệ / còn hạn hay không
    public boolean validateToken(String token) {
        try {
            // Thử giải mã token bằng khóa bí mật
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true; // Không phát sinh lỗi -> Token chuẩn còn hạn sử dụng!
        } catch (JwtException | IllegalArgumentException e) {
            // Nếu Token hết hạn, bị nắn sửa chữ ký, hoặc chuỗi bị hỏng/rác
            return false;
        }
    }


}
