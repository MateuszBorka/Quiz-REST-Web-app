package com.example.quizrestwebapp.Quiz;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class QuizModelAssembler {

    public EntityModel<Quiz> toModel(Quiz quiz){

        return EntityModel.of(quiz,
                linkTo(methodOn(QuizController.class).getQuizById(quiz.getId())).withSelfRel(),
                linkTo(methodOn(QuizController.class).all()).withRel("quizzes"));
    }
}
