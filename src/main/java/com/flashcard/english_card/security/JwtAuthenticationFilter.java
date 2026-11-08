package com.flashcard.english_card.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
//xử lý request (thêm, sửa xóa ....các chức năng của hệ thống ngoại trừ /athu)
//chỉ kiểm tra duy nhất 1 lần request trong lần đó
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,
                                   CustomUserDetailsService customUserDetailsService){
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailsService = customUserDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException{
        try {
            //lấy token từ header
            String jwt = getJwtFromRequest(request);
            //kiểm tra token có tồn tại hay hợp lệ không
            if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)){
                //lấy username từ token (mã hóa)
                String username = jwtTokenProvider.getUsernameFromToken(jwt);
                //Sau khi lấy đc username thì tìm thông tin User đầy đủ trong DB
                //có thực sự tồn tại người này
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                //tạo đối tượng xác thực chính thức cho spring security
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null, //mật khẩu (null sẽ an toàn)
                                userDetails.getAuthorities()
                        );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //Lưu thông tin xác thực vào SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception ex){
            logger.error("Không thể thiết lập xác thực người dùng trong Security Context", ex);
        }
        //Mở barie cho Request đi tiếp ví dụ như controller
        //nó nằm ở ngoài try-catch vì dù có token hay ko
        //thì nó vẫn truy cập c trang /login để xử lý phân quyền ở vòng ngoài
        filterChain.doFilter(request, response);
    }

    // Hàm lấy chuỗi Token từ header Authorization
    private String getJwtFromRequest(HttpServletRequest request) {
        //Rút chuỗi Header có tên Authorization từ Request
        String bearerToken = request.getHeader("Authorization");

        //Kiểm tra xem Header có chữ và có bắt đầu bằng Bearer không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            // Cắt bỏ 7 ký tự Bearer để lấy nguyên vẹn chuỗi Token dài đằng sau
            return bearerToken.substring(7);
        }

        return null;
    }
}
