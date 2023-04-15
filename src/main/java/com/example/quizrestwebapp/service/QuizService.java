package com.example.quizrestwebapp.service;

import com.example.quizrestwebapp.repository.QuizRepository;
import com.example.quizrestwebapp.domain.Question;
import com.example.quizrestwebapp.domain.Quiz;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;


    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }


    public Question getQuestion(Long quizId, Long questionId) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            Optional<Question> questionOptional = quiz.getQuestions().stream()
                    .filter(q -> q.getId().equals(questionId)).findFirst();
            if (questionOptional.isPresent()) {
                return questionOptional.get();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
        }
    }



}
