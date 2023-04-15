package com.example.quizrestwebapp.assembler;

import com.example.quizrestwebapp.controller.QuizController;
import com.example.quizrestwebapp.domain.Quiz;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class QuizModelAssembler {

    public EntityModel<Quiz> toModel(Quiz quiz){

        return EntityModel.of(quiz,
                WebMvcLinkBuilder.linkTo(methodOn(QuizController.class).getQuizById(quiz.getId())).withSelfRel(),
                linkTo(methodOn(QuizController.class).all()).withRel("quizzes"));
    }
}
