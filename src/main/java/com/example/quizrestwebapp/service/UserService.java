package com.example.quizrestwebapp.service;

import com.example.quizrestwebapp.domain.User;
import com.example.quizrestwebapp.domain.UserRole;
import com.example.quizrestwebapp.dto.AnswerAnalysis;
import com.example.quizrestwebapp.exception.UserNotFoundException;
import com.example.quizrestwebapp.repository.UserRepository;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService{

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<User> findUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findUserByUsername(username);
    }

    public Optional<User> findUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findUserByEmail(email);
    }

    public void createUser(@NonNull String username,  @NonNull String password, @NonNull String email, @NonNull String... roleNames){

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        Set<UserRole> roles = new HashSet<>();
        for (String role : roleNames){
            roles.add(new UserRole(role));
        }

        user.setRoles(roles);
        userRepository.save(user);

    }

    public void addToStatistics(User user, int sumOfPoints, List<AnswerAnalysis> answersAnalysis){
        user.setQuizzesSolved(user.getQuizzesSolved()+1);       //Todo add additional field in user where ids of quests solved will be present
        user.setPoints(user.getPoints()+sumOfPoints);
        for (AnswerAnalysis answer: answersAnalysis) {
            if (answer.getUserAnswer().equals(answer.getRightAnswer())){
                user.setAnsweredRight(user.getAnsweredRight()+1);
            } else {
                user.setAnsweredWrong(user.getAnsweredWrong()+1);
            }
        }
        userRepository.save(user);
    }
}
