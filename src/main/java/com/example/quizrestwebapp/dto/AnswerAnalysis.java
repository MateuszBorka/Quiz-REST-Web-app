package com.example.quizrestwebapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerAnalysis {

    private int pointsForQuestion;
    private String userAnswer;
    private String rightAnswer;


}
