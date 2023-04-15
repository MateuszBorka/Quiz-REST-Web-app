package com.example.quizrestwebapp.assembler;

import com.example.quizrestwebapp.controller.QuestionController;
import com.example.quizrestwebapp.controller.QuizController;
import com.example.quizrestwebapp.domain.Question;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class QuestionModelAssembler {

    public EntityModel<Question> toModel(Question question) {

        return EntityModel.of(question,
                linkTo(methodOn(QuizController.class).getQuizById(question.getQuiz().getId())).withRel("quiz"),
                WebMvcLinkBuilder.linkTo(methodOn(QuestionController.class).one(question.getQuiz().getId(), question.getId())).withSelfRel());

    }


}
