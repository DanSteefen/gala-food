package com.oxo.gala_food.Gala_Food.controler;

import com.oxo.gala_food.Gala_Food.request.UserRequest;
import com.oxo.gala_food.Gala_Food.response.UserResponse;
import com.oxo.gala_food.Gala_Food.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register (@RequestBody UserRequest request){
        return userService.registerUser(request);
    }
}
