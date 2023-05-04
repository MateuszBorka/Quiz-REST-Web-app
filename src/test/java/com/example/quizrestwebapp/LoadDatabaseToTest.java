package com.example.quizrestwebapp;

import com.example.quizrestwebapp.domain.Answer;
import com.example.quizrestwebapp.domain.Question;
import com.example.quizrestwebapp.domain.Quiz;
import com.example.quizrestwebapp.repository.QuizRepository;
import com.example.quizrestwebapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class LoadDatabaseToTest {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabaseToTest.class);

    private final UserService userService;

    @Autowired
    public LoadDatabaseToTest(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public CommandLineRunner initDatabase(QuizRepository quizRepository) {

        return args -> {


            userService.createUser("anton", "1234", "anton@gmail.com", "USER");
            userService.createUser("ivan", "12345", "ivan@pw.edu.pl", "ADMIN");


            List<Question> questions1 = new ArrayList<>();
            Answer rightAnswer = new Answer("4");
            questions1.add(new Question("What is 2 + 2?",
                    Arrays.asList(new Answer("2"), new Answer("3"), rightAnswer, new Answer("5")),
                    rightAnswer, 5, 50.0f));
            rightAnswer = new Answer("Paris");
            questions1.add(new Question("What is the capital of France?",
                    Arrays.asList(rightAnswer, new Answer("Madrid"), new Answer("Berlin"), new Answer("Rome")),
                    rightAnswer, 10, 70.0f));
            Quiz quiz1 = Quiz.createQuiz("General Knowledge Quiz", 2, "admin", questions1);


            List<Question> questions2 = new ArrayList<>();
            rightAnswer = new Answer("H2O");
            questions2.add(new Question("What is the formula for water?",
                    Arrays.asList(new Answer("CO2"), new Answer("H2SO4"), rightAnswer, new Answer("NaCl")),
                    rightAnswer, 5, 60.0f));
            rightAnswer = new Answer("Elon Musk");
            questions2.add(new Question("Who is the CEO of Tesla?",
                    Arrays.asList(new Answer("Bill Gates"), new Answer("Steve Jobs"), rightAnswer, new Answer("Mark Zuckerberg")),
                    rightAnswer, 10, 80.0f));
            Quiz quiz2 = Quiz.createQuiz("Science and Technology Quiz", 3, "admin", questions2);

            log.info("Preloading..." + quizRepository.save(quiz1));
            log.info("Preloading..." + quizRepository.save(quiz2));
        };
    }
}
