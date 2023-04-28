package com.example.quizrestwebapp.service;

import com.example.quizrestwebapp.dto.JwtRequest;
import com.example.quizrestwebapp.dto.JwtResponse;
import com.example.quizrestwebapp.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserService userService;

    private final AuthService authService;


    public JwtResponse register(String username, String password,
                                String email, List<String> userRoles){
        if (userService.findUserByUsername(username).isPresent() || userService.findUserByEmail(email).isPresent()){
            throw new UserAlreadyExistsException("A user with the same username or email already exists.");
        }

        userService.createUser(username, password, email, String.valueOf(userRoles));
        JwtRequest loginRequest = new JwtRequest(username, password);

        return authService.login(loginRequest);
    }
}
