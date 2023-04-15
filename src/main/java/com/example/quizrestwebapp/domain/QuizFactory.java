package com.example.quizrestwebapp.domain;


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
