package com.example.quizrestwebapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRegisterRequest {

/*    {
        "login": "antonn",
            "password": "1234",
            "email": "antonn@gmail.com",
            "userRoles": ["USER"]
    }*/

    private String login;
    private String password;
    private String email;
    private List<String> userRoles;

}
