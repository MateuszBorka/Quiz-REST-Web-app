package com.example.quizrestwebapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateQuizRequest {
    private String title;

    private int difficulty;
    private List<UserQuestion> questions;
}
