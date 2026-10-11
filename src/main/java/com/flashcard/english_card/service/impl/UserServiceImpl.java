package com.flashcard.english_card.service;

import com.flashcard.english_card.dto.request.UserRegisterRequest;
import com.flashcard.english_card.dto.response.UserDTO;
import com.flashcard.english_card.entity.User;
import com.flashcard.english_card.mapper.UserMapper;
import com.flashcard.english_card.repository.UserRepository;
import com.flashcard.english_card.service.impl.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
        User saveUser = userRepository.save(user);
        //entity -> dto
        return userMapper.toDTO(saveUser);
    }

    @Override
    public UserDTO getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với ID" + id));
        return userMapper.toDTO(user);
    }

}
