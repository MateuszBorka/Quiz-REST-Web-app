package com.example.quizrestwebapp.Quiz;

import com.example.quizrestwebapp.Question.Question;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class QuizFactory {

    public Quiz createQuiz(String name, int level, String createdBy, List<Question> questions) {
        Quiz quiz = new Quiz(name, level, createdBy, questions);
        for (Question question : questions) {
            question.setQuiz(quiz);
        }
        return quiz;
    }
}
