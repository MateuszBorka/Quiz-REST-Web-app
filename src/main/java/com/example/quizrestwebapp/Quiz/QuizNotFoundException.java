package com.example.quizrestwebapp.Quiz;

class QuizNotFoundException extends RuntimeException {

    QuizNotFoundException(Long id) {
        super("Could not find quiz " + id);
    }
}
