package com.example.quizrestwebapp;

import com.example.quizrestwebapp.domain.*;
import com.example.quizrestwebapp.repository.QuizRepository;
import com.example.quizrestwebapp.repository.UserRepository;
import com.example.quizrestwebapp.service.UserService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);


    private final QuizFactory quizFactory;
    private final UserRepository userRepository;

    private final UserService userService;


    @Autowired
    public LoadDatabase(QuizFactory quizFactory,
                        UserRepository userRepository,
                        UserService userService) {
        this.quizFactory = quizFactory;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Bean
    public CommandLineRunner initDatabase(QuizRepository quizRepository) {

        return args -> {


            userService.createUser("anton", "1234", "USER");
            userService.createUser("ivan", "12345", "ADMIN");



            List<Question> questions1 = new ArrayList<>();
            questions1.add(new Question("What is 2 + 2?",
                    Arrays.asList(new Answer("2"), new Answer("3"), new Answer("4"), new Answer("5")),
                    2, 5, 50.0f));
            questions1.add(new Question("What is the capital of France?",
                    Arrays.asList(new Answer("Paris"), new Answer("Madrid"), new Answer("Berlin"), new Answer("Rome")),
                    0, 10, 70.0f));
            Quiz quiz1 = quizFactory.createQuiz("General Knowledge Quiz", 2, "admin", questions1);



            List<Question> questions2 = new ArrayList<>();
            questions2.add(new Question("What is the formula for water?",
                    Arrays.asList(new Answer("CO2"), new Answer("H2SO4"), new Answer("H2O"), new Answer("NaCl")),
                    2, 5, 60.0f));
            questions2.add(new Question("Who is the CEO of Tesla?",
                    Arrays.asList(new Answer("Bill Gates"), new Answer("Steve Jobs"), new Answer("Elon Musk"), new Answer("Mark Zuckerberg")),
                    2, 10, 80.0f));
            Quiz quiz2 = quizFactory.createQuiz("Science and Technology Quiz", 3, "admin", questions2);

            log.info("Preloading..." + quizRepository.save(quiz1));
            log.info("Preloading..." + quizRepository.save(quiz2));
        };
    }
}
