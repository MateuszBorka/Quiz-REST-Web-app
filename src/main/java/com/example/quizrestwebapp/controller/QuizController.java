package com.example.quizrestwebapp.controller;

import com.example.quizrestwebapp.assembler.QuizModelAssembler;
import com.example.quizrestwebapp.assembler.QuizWithQuestionsModelAssembler;
import com.example.quizrestwebapp.domain.Question;
import com.example.quizrestwebapp.assembler.QuestionModelAssembler;
import com.example.quizrestwebapp.domain.Quiz;
import com.example.quizrestwebapp.domain.User;
import com.example.quizrestwebapp.dto.AnswerAnalysis;
import com.example.quizrestwebapp.dto.JwtAuthentication;
import com.example.quizrestwebapp.dto.UserAnswer;
import com.example.quizrestwebapp.dto.UserQuizAnswerRequest;
import com.example.quizrestwebapp.exception.AuthException;
import com.example.quizrestwebapp.exception.QuizNotFoundException;
import com.example.quizrestwebapp.repository.QuizRepository;
import com.example.quizrestwebapp.service.AuthService;
import com.example.quizrestwebapp.service.QuizService;
import com.example.quizrestwebapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/quizzes")
@AllArgsConstructor
public class QuizController {

    private final QuizRepository repository;

    private final QuizService quizService;

    private final QuizModelAssembler quizAssembler;

    private final QuizWithQuestionsModelAssembler quizWithQuestionsAssembler;

    private final QuestionModelAssembler questionAssembler;

    private final UserService userService;

    private final AuthService authService;

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

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("{id}/submit")
    public EntityModel<Map<String, Object>> checkAnswers(@PathVariable Long id, @RequestBody UserQuizAnswerRequest request){

        final JwtAuthentication authInfo = authService.getAuthInfo();           //Todo check if there are other ways to get user(maybe create additional function)
        User user = userService.findUserByUsername((String) authInfo.getPrincipal())
                .orElseThrow(() -> new AuthException("User not found"));

        ArrayList<String> userAnswers = request.getAnswers()
                .stream()
                .map(UserAnswer::getBody)
                .collect(Collectors.toCollection(ArrayList::new));

        List<AnswerAnalysis> answersAnalysis = quizService.createAnswersAnalysis(id, userAnswers);
        int sumOfPoints = answersAnalysis.stream().mapToInt(AnswerAnalysis::getPointsForQuestion).sum();

        userService.addToStatistics(user, sumOfPoints, answersAnalysis);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("sumOfPoints", sumOfPoints);
        responseBody.put("answersAnalysis", answersAnalysis);

        return EntityModel.of(responseBody,
                linkTo(methodOn(QuizController.class).all()).withRel("quizzes"),
                linkTo(methodOn(QuizController.class).getQuizById(id)).withRel("quiz"),
                linkTo(methodOn(QuizController.class).getQuestionsByQuizId(id)).withRel("questions"));
    }


}
