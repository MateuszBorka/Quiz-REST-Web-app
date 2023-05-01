package com.example.quizrestwebapp.controller;


import com.example.quizrestwebapp.domain.User;
import com.example.quizrestwebapp.dto.JwtAuthentication;
import com.example.quizrestwebapp.exception.AuthException;
import com.example.quizrestwebapp.service.AuthService;
import com.example.quizrestwebapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserProfileController {


    private final AuthService authService;
    private final UserService userService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("user")
    public EntityModel<User> userProfile() {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        User user = userService.findUserByUsername((String) authInfo.getPrincipal())
                .orElseThrow(() -> new AuthException("User not found"));
        return EntityModel.of(user);
    }

}