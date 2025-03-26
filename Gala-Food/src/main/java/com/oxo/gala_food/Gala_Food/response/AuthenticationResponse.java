package com.oxo.gala_food.Gala_Food.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticationResponse {

    private String email;
    private String token;
}
