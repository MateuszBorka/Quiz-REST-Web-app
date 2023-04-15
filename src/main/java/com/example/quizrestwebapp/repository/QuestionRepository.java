package com.example.quizrestwebapp.repository;

import com.example.quizrestwebapp.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
