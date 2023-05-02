package com.example.quizrestwebapp.controller;

import com.example.quizrestwebapp.domain.Quiz;
import com.example.quizrestwebapp.dto.CreateQuizRequest;
import com.example.quizrestwebapp.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/create")
@AllArgsConstructor
public class QuizManagementController {

    private final QuizService quizService;

    @PreAuthorize("hasAuthority('USER') || hasAuthority('ADMIN')")
    @PostMapping("quiz")
    public EntityModel<Quiz> createQuiz(@RequestBody CreateQuizRequest request){
        quizService.createQuiz(request);
        return null;
    }
}
