package com.oxo.gala_food.Gala_Food.service;

import com.oxo.gala_food.Gala_Food.request.UserRequest;
import com.oxo.gala_food.Gala_Food.response.UserResponse;

public interface UserService {

    UserResponse registerUser(UserRequest request);
    String findByUserId();
}
