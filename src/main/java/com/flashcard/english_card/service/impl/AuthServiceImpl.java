    package com.flashcard.english_card.service.impl;

    import com.flashcard.english_card.dto.request.RefreshTokenRequest;
    import com.flashcard.english_card.dto.request.UserLoginRequest;
    import com.flashcard.english_card.dto.request.UserRegisterRequest;
    import com.flashcard.english_card.dto.response.AuthDTO;
    import com.flashcard.english_card.dto.response.RefreshTokenDTO;
    import com.flashcard.english_card.dto.response.UserDTO;
    import com.flashcard.english_card.entity.RefreshToken;
    import com.flashcard.english_card.entity.User;
    import com.flashcard.english_card.mapper.UserMapper;
    import com.flashcard.english_card.repository.UserRepository;
    import com.flashcard.english_card.security.JwtTokenProvider;
    import com.flashcard.english_card.service.AuthService;
    import com.flashcard.english_card.service.RefreshTokenService;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    @Service
    public class AuthServiceImpl implements AuthService {
        //authenticationManager: Chú bảo vệ chuyên đối chiếu tên đăng nhập
        // và mật khẩu dưới Database xem có đúng không.
        private final AuthenticationManager authenticationManager;
        private final JwtTokenProvider jwtTokenProvider;
        private final UserRepository userRepository;
        private final UserMapper userMapper;
        private final PasswordEncoder passwordEncoder;
        private final RefreshTokenService refreshTokenService;

        public AuthServiceImpl(AuthenticationManager authenticationManager,
                               JwtTokenProvider jwtTokenProvider,
                               UserRepository userRepository,
                               UserMapper userMapper,
                               PasswordEncoder passwordEncoder,
                               RefreshTokenService refreshTokenService){
            this.authenticationManager =authenticationManager;
            this.jwtTokenProvider = jwtTokenProvider;
            this.userRepository = userRepository;
            this.userMapper = userMapper;
            this.passwordEncoder = passwordEncoder;
            this.refreshTokenService = refreshTokenService;
        }

        @Override
        public UserDTO registerUser(UserRegisterRequest request){
            //kểm tra username và email đã tồn tại chưa
            if(userRepository.existsByUsername(request.getUsername())){
                throw new RuntimeException("Tên đăng nhập đã tồn tại");
            }
            if(userRepository.existsByEmail(request.getEmail())){
                throw new RuntimeException("Email đã tồn tại");
            }
            //dto -> entity
            User user = userMapper.toEntity(request);
            //mã hóa mật khẩu trước khi lưu vào db
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            User saveUser = userRepository.save(user);
            //entity -> dto
            return userMapper.toDTO(saveUser);
        }

        @Override
        @Transactional
        public AuthDTO login(UserLoginRequest request){
            //Xác thực username & password qua authenticationManager
            Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            // Lưu vào SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //Tìm User trong DB để lấy ID
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
            //sinh ra token
            String accessToken = this.jwtTokenProvider.generateToken(authentication.getName());
            //Sinh Refresh Token (7 ngày) và lưu DB
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
            //trích xuất Role
            String role = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("ROLE_USER");

            //Trả về AuthDTO
            return AuthDTO.builder()
                    .accessToken(accessToken) //token
                    .refreshToken(refreshToken.getRefreshToken())
                    .username(authentication.getName()) //tên người dùng
                    .role(role) //quyền
                    .build();
        }


        @Override
        @Transactional
        public RefreshTokenDTO renewTokens(RefreshTokenRequest request){
            return refreshTokenService.findByRefreshToken(request.getRefreshToken())
                    .map(refreshTokenService::verifyExpiration)
                    .map(oldRefreshToken ->{
                        User user = oldRefreshToken.getUser();
                        //sinh access token mới tồn tại 15 phút
                        String newAccessToken = jwtTokenProvider.generateToken(user.getUsername());
                        //XOAY VÒNG: Sinh Refresh Token mới
                        //xóa ref-1 cũ và lưu ref-2 vào DB
                        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user.getId());
                        //trả về AuthDTO chứa accessToken mới và ref mới (ref-2)
                        return RefreshTokenDTO.builder()
                                .accessToken(newAccessToken)
                                .refreshToken(newRefreshToken.getRefreshToken()) //ref-2
                                .build();
                    })
                    .orElseThrow(() -> new RuntimeException("Refresh token không tồn tại hoặc đã bị thu hồi!"));
        }

        @Override
        @Transactional
        public void logout(RefreshTokenRequest request) {
            // Trích xuất token từ DTO và ủy quyền cho RefreshTokenService xử lý
            refreshTokenService.deleteByRefreshToken(request.getRefreshToken());
        }
    }
