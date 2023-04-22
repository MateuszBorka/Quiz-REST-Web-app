package com.example.quizrestwebapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class UserQuizAnswerRequest {

    private Long quizId;
    private ArrayList<UserAnswer> answers;
}
