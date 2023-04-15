package com.example.quizrestwebapp.repository;

import com.example.quizrestwebapp.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
