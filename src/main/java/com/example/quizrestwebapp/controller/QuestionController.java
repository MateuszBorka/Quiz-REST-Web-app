package com.example.quizrestwebapp.controller;

import com.example.quizrestwebapp.repository.QuestionRepository;
import com.example.quizrestwebapp.service.QuizService;
import com.example.quizrestwebapp.assembler.QuestionModelAssembler;
import com.example.quizrestwebapp.domain.Question;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {

    private final QuestionRepository repository;

    private final QuestionModelAssembler assembler;

    private final QuizService quizService;


    public QuestionController(QuestionRepository repository,
                              QuestionModelAssembler assembler, QuizService quizService) {
        this.repository = repository;
        this.assembler = assembler;
        this.quizService = quizService;
    }

    @GetMapping("/api/quizzes/{quizId}/{id}")
    public EntityModel<Question> one(@PathVariable Long quizId, @PathVariable Long id) {

        Question question = quizService.getQuestion(quizId, id);
        return assembler.toModel(question);
    }



}