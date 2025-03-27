package com.oxo.gala_food.Gala_Food.service.impl;

import com.oxo.gala_food.Gala_Food.model.User;
import com.oxo.gala_food.Gala_Food.repository.UserRepository;
import com.oxo.gala_food.Gala_Food.request.UserRequest;
import com.oxo.gala_food.Gala_Food.response.UserResponse;
import com.oxo.gala_food.Gala_Food.service.AuthenticationFacade;
import com.oxo.gala_food.Gala_Food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Override
    public UserResponse registerUser(UserRequest request) {

        User newUser = convertToEntity(request);
        newUser = userRepository.save(newUser);
        return convertToResponse(newUser);
    }

    @Override
    public String findByUserId() {

        String loggedInUserEmail = authenticationFacade.getAuthentication().getName();
        User loggedInUser = userRepository.findByEmail(loggedInUserEmail).orElseThrow(() -> new UsernameNotFoundException("User not found.."));
        return loggedInUser.getId();
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
