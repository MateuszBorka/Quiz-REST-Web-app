package com.example.quizrestwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.example.quizrestwebapp", "config"})
public class QuizRestWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizRestWebAppApplication.class, args);
    }

}
