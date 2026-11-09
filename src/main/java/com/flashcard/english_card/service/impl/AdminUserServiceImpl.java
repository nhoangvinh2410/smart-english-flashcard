package com.flashcard.english_card.service.impl;

import com.flashcard.english_card.dto.response.UserDTO;
import com.flashcard.english_card.entity.User;
import com.flashcard.english_card.mapper.UserMapper;
import com.flashcard.english_card.repository.UserRepository;
import com.flashcard.english_card.service.AdminUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AdminUserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy người dùng"));
        return userMapper.toDTO(user);
    }

    public UserDTO toggleUserStatus(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("Không tìm thấy người dùng"));
        user.setEnabled(!user.isEnabled());
        User updateUser = userRepository.save(user);
        return userMapper.toDTO(updateUser);
    }


}
