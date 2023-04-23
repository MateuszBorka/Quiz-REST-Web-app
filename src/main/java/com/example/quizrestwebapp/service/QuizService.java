package com.example.quizrestwebapp.service;

import com.example.quizrestwebapp.dto.AnswerAnalysis;
import com.example.quizrestwebapp.repository.QuizRepository;
import com.example.quizrestwebapp.domain.Question;
import com.example.quizrestwebapp.domain.Quiz;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

//    public List<Boolean> checkIfQuizAnswersRight(Long quizId, ArrayList<UserAnswer> userAnswers){
//        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
//        Quiz quiz;
//        if (quizOptional.isPresent()) {
//            quiz = quizOptional.get();
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
//        }
//        List<Question> questions = quiz.getQuestions();
//        ArrayList<Boolean> isRight = new ArrayList<>();
//        for(int i = 0; i < userAnswers.size(); i++){
//            isRight.add(Objects.equals(userAnswers.get(i).getBody(), questions.get(i).getBody()));
//        }
//        return isRight;
//    }

    public List<AnswerAnalysis> createAnswersAnalysis(Long quizId, ArrayList<String> userAnswers){

        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        Quiz quiz;
        if (quizOptional.isPresent()) {
            quiz = quizOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found");
        }
        List<Question> questions = quiz.getQuestions();


        List<AnswerAnalysis> answerAnalysis = new ArrayList<>();


        for(int i = 0; i < userAnswers.size(); i++){
            AnswerAnalysis singleAnswer = new AnswerAnalysis();
            singleAnswer.setUserAnswer(userAnswers.get(i));
            singleAnswer.setRightAnswer(questions.get(i).getRightAnswer().getBody());
            if (!Objects.equals(userAnswers.get(i), questions.get(i).getRightAnswer().getBody())){
                singleAnswer.setPointsForQuestion(0);
            } else {
                singleAnswer.setPointsForQuestion(questions.get(i).getPointsForRightAnswer());
            }
            answerAnalysis.add(singleAnswer);
        }
        return answerAnalysis;
    }



}
