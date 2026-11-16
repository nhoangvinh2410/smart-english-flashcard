package com.flashcard.english_card.service.impl;

import com.flashcard.english_card.dto.request.ChangePasswordRequest;
import com.flashcard.english_card.dto.request.UpdateProfileRequest;
import com.flashcard.english_card.dto.response.UserDTO;
import com.flashcard.english_card.entity.User;
import com.flashcard.english_card.mapper.UserMapper;
import com.flashcard.english_card.repository.UserRepository;
import com.flashcard.english_card.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    //dùng để đổi thông tin user
    public UserDTO updateProfile(String username, UpdateProfileRequest request){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy người dùng"));
        if(request.getFullName() != null){
            user.setFullName(request.getFullName());
        }
        if(request.getAvatarUrl() != null){
            user.setAvatarUrl(request.getAvatarUrl());
        }

        User updateUser = userRepository.save(user);
        return userMapper.toDTO(updateUser);
    }

    @Override
    //dùng để hiển thị thông tin khi đăng nhập thành công
    public UserDTO getCurrentUserProfile(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy người dùng"));
        return userMapper.toDTO(user);
    }

    @Override
    public void changePassword(String username, ChangePasswordRequest request){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng "));
        //muốn đổi mật khẩu thì phải nhập mật khẩu cũ (mật khẩu hiện tại chưa đổi)
        //so sánh mật khẩu nhập vào có khớp với mật khẩu đã đc mã hóa lưu ở DB
        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new RuntimeException("Mật khẩu cũ không chính xác");
        }
        //mã hóa mật khẩu mới và lưu xuống DB
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }





}
