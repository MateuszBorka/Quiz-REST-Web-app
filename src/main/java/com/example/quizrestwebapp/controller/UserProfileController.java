package com.example.quizrestwebapp.controller;


import com.example.quizrestwebapp.domain.User;
import com.example.quizrestwebapp.exception.AuthException;
import com.example.quizrestwebapp.repository.UserRepository;
import com.example.quizrestwebapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.quizrestwebapp.domain.JwtAuthentication;
import com.example.quizrestwebapp.service.AuthService;
import org.springframework.hateoas.EntityModel;



@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserProfileController {


    private final AuthService authService;
    private final UserService userService;
    private final UserRepository userRepository;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("user")
    public EntityModel<User> userProfile() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        User user = userService.getByUsername((String) authInfo.getPrincipal())
                .orElseThrow(() -> new AuthException("User not found"));
        return EntityModel.of(user);
    }

}