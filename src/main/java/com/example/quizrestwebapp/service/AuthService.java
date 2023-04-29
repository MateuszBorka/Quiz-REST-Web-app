package com.example.quizrestwebapp.service;

import com.example.quizrestwebapp.dto.JwtAuthentication;
import com.example.quizrestwebapp.dto.JwtRequest;
import com.example.quizrestwebapp.dto.JwtResponse;
import com.example.quizrestwebapp.domain.User;
import com.example.quizrestwebapp.exception.AuthException;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final Map<String, String> refreshStorage = new HashMap<>();  //Todo: that's wrong to use there a HashMap, needs to repair, refreshStorage needs new database table.

    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull JwtRequest authRequest){
        final User user = userService.findUserByUsername(authRequest.getLogin())
                .orElseThrow(() -> new AuthException("User not found"));

        if (user.getPassword().equals(authRequest.getPassword())){
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getUsername(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Wrong password");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken){
        if (jwtProvider.validateRefreshToken(refreshToken)){
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String username = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(username);

            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)){

                final User user = userService.findUserByUsername(username)
                        .orElseThrow(() -> new AuthException("User not found"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
         return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)){

            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String username = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(username);

            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)){
                final User user = userService.findUserByUsername(username)
                        .orElseThrow(() -> new AuthException("User not found"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getUsername(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
         throw new AuthException("Invalid jwt token");
    }

    public JwtAuthentication getAuthInfo(){
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
