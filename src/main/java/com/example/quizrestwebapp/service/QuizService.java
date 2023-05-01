package com.example.quizrestwebapp.service;

import com.example.quizrestwebapp.domain.Answer;
import com.example.quizrestwebapp.domain.Question;
import com.example.quizrestwebapp.domain.Quiz;
import com.example.quizrestwebapp.dto.AnswerAnalysis;
import com.example.quizrestwebapp.dto.CreateQuizRequest;
import com.example.quizrestwebapp.dto.UserAnswerWithItRight;
import com.example.quizrestwebapp.dto.UserQuestion;
import com.example.quizrestwebapp.repository.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    public Optional<Quiz> findQuizById(Long id){
        return quizRepository.findById(id);
    }

    public List<Quiz> findAllQuizzes(){
        return quizRepository.findAll();
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


    public Quiz createQuiz(CreateQuizRequest request) {
        List<Question> questions = new ArrayList<>();

        for (UserQuestion userQuestion : request.getQuestions()) {
            List<Answer> answers = new ArrayList<>();
            Answer rightAnswer = null;
            for (UserAnswerWithItRight userAnswer : userQuestion.getAnswers()) {
                Answer answer = new Answer(userAnswer.getBody());
                if (userAnswer.isRight()) {
                    rightAnswer = answer;
                }
                answers.add(answer);
            }
            Question question = new Question(userQuestion.getBody(), answers, rightAnswer,
                    userQuestion.getPointsForRightAnswer(), 0.0f);
            questions.add(question);
        }


        Quiz quiz = Quiz.createQuiz(request.getTitle(), request.getDifficulty(), "admin", questions);
        return quizRepository.save(quiz);
    }












}
