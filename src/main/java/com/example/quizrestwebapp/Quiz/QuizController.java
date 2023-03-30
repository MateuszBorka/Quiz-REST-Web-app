package com.example.quizrestwebapp.Quiz;

import com.example.quizrestwebapp.Question.Question;
import com.example.quizrestwebapp.Question.QuestionModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.List;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class QuizController {


    private final QuizRepository repository;

    private final QuizModelAssembler quizAssembler;

    private final QuizWithQuestionsModelAssembler quizWithQuestionsAssembler;

    private final QuestionModelAssembler questionAssembler;

    public QuizController(QuizRepository repository, QuizModelAssembler quizAssembler,
                          QuizWithQuestionsModelAssembler quizWithQuestionsAssembler,
                          QuestionModelAssembler questionAssembler) {
        this.repository = repository;
        this.quizAssembler = quizAssembler;
        this.quizWithQuestionsAssembler = quizWithQuestionsAssembler;
        this.questionAssembler = questionAssembler;

    }


    @GetMapping("/api/quizzes/{id}/questions")
    CollectionModel<EntityModel<Question>> getQuestionsByQuizId(@PathVariable Long id){

        Quiz quiz = repository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));

        List<EntityModel<Question>> questions = quiz.getQuestions().stream()
                .map(questionAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(questions, linkTo(methodOn(QuizController.class).all()).withSelfRel());




    }

    @GetMapping("/api/quizzes/{id}")
    public EntityModel<Quiz> getQuizById(@PathVariable Long id) {
        Quiz quiz = repository.findById(id)
                .orElseThrow(() -> new QuizNotFoundException(id));

        return quizWithQuestionsAssembler.toModel(quiz);
    }


    @GetMapping("/api/quizzes")
    public CollectionModel<EntityModel<Quiz>> all() {
        List<EntityModel<Quiz>> quizzes = repository.findAll().stream()
                .map(quizAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(quizzes, linkTo(methodOn(QuizController.class).all()).withSelfRel());
    }


}
