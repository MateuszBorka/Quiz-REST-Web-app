package com.example.quizrestwebapp;

import com.example.quizrestwebapp.Answer.Answer;
import com.example.quizrestwebapp.Question.Question;
import com.example.quizrestwebapp.Quiz.Quiz;
import com.example.quizrestwebapp.Quiz.QuizRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public CommandLineRunner initDatabase(QuizRepository quizRepository){

        return args -> {
            // Create the first quiz
            List<Question> questions1 = new ArrayList<>();
            questions1.add(new Question("What is 2 + 2?",
                    Arrays.asList(new Answer("2"), new Answer("3"), new Answer("4"), new Answer("5")),
                    2, 5, 50.0f));
            questions1.add(new Question("What is the capital of France?",
                    Arrays.asList(new Answer("Paris"), new Answer("Madrid"), new Answer("Berlin"), new Answer("Rome")),
                    0, 10, 70.0f));
            Quiz quiz1 = new Quiz("General Knowledge Quiz", 2, "admin", questions1);
            quiz1.getQuestions().get(0).setQuiz(quiz1);

            // Create the second quiz
            List<Question> questions2 = new ArrayList<>();
            questions2.add(new Question("What is the formula for water?",
                    Arrays.asList(new Answer("CO2"), new Answer("H2SO4"), new Answer("H2O"), new Answer("NaCl")),
                    2, 5, 60.0f));
            questions2.add(new Question("Who is the CEO of Tesla?",
                    Arrays.asList(new Answer("Bill Gates"), new Answer("Steve Jobs"), new Answer("Elon Musk"), new Answer("Mark Zuckerberg")),
                    2, 10, 80.0f));
            Quiz quiz2 = new Quiz("Science and Technology Quiz", 3, "admin", questions2);

            // Save the quizzes to the database
            log.info("Preloading..." + quizRepository.save(quiz1));
            log.info("Preloading..." + quizRepository.save(quiz2));
        };
    }
}
