package com.example.quizrestwebapp.controller;

import com.example.quizrestwebapp.assembler.QuizModelAssembler;
import com.example.quizrestwebapp.assembler.QuizWithQuestionsModelAssembler;
import com.example.quizrestwebapp.domain.Question;
import com.example.quizrestwebapp.assembler.QuestionModelAssembler;
import com.example.quizrestwebapp.domain.Quiz;
import com.example.quizrestwebapp.exception.QuizNotFoundException;
import com.example.quizrestwebapp.repository.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.List;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/quizzes")
@AllArgsConstructor
public class QuizController {   //Todo reformat mapping


    private final QuizRepository repository;

    private final QuizModelAssembler quizAssembler;

    private final QuizWithQuestionsModelAssembler quizWithQuestionsAssembler;

    private final QuestionModelAssembler questionAssembler;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("{id}/questions")
    public CollectionModel<EntityModel<Question>> getQuestionsByQuizId(@PathVariable Long id){

        Quiz quiz = repository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));

        List<EntityModel<Question>> questions = quiz.getQuestions().stream()
                .map(questionAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(questions, linkTo(methodOn(QuizController.class).all()).withSelfRel());
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("{id}")
    public EntityModel<Quiz> getQuizById(@PathVariable Long id) {
        Quiz quiz = repository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));

        return quizWithQuestionsAssembler.toModel(quiz);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/")
    public CollectionModel<EntityModel<Quiz>> all() {
        List<EntityModel<Quiz>> quizzes = repository.findAll().stream()
                .map(quizAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(quizzes, linkTo(methodOn(QuizController.class).all()).withSelfRel());
    }


}
