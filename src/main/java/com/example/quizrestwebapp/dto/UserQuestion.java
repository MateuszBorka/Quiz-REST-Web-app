package com.example.quizrestwebapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserQuestion {

    private int pointsForRightAnswer;
    private String body;
    private List<UserAnswerWithItRight> answers;

}
