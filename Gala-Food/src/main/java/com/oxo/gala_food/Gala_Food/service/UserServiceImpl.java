package com.oxo.gala_food.Gala_Food.service;

import com.oxo.gala_food.Gala_Food.model.User;
import com.oxo.gala_food.Gala_Food.repository.UserRepository;
import com.oxo.gala_food.Gala_Food.request.UserRequest;
import com.oxo.gala_food.Gala_Food.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse registerUser(UserRequest request) {

        User newUser = convertToEntity(request);
        newUser = userRepository.save(newUser);
        return convertToResponse(newUser);
    }

    private User convertToEntity(UserRequest request){

        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    private UserResponse convertToResponse(User registerUser){

        return UserResponse.builder()
                .id(registerUser.getId())
                .name(registerUser.getName())
                .email(registerUser.getEmail())
                .build();
    }
}
