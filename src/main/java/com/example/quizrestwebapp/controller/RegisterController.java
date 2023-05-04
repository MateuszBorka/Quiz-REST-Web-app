package com.example.quizrestwebapp.controller;

import com.example.quizrestwebapp.dto.JwtResponse;
import com.example.quizrestwebapp.dto.UserRegisterRequest;
import com.example.quizrestwebapp.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("register")
    public ResponseEntity<JwtResponse> registration(@RequestBody UserRegisterRequest userRegisterRequest) {
        JwtResponse token = registerService.register(userRegisterRequest.getLogin(),
                userRegisterRequest.getPassword(),
                userRegisterRequest.getEmail(),
                userRegisterRequest.getUserRoles());

        return ResponseEntity.ok(token);
    }

}
