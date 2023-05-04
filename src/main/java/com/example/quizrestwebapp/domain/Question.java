package com.example.quizrestwebapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
public class Question {

    @JsonIgnore
    private @Id
    @GeneratedValue Long id;
    private String body;
    private int pointsForRightAnswer;
    private float percentOfPeopleRight;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;


    public Question(String body, List<Answer> answers, Answer rightAnswer,
                    int pointsForRightAnswer, float percentOfPeopleRight) {

        this.body = body;
        this.pointsForRightAnswer = pointsForRightAnswer;
        this.percentOfPeopleRight = percentOfPeopleRight;
        this.answers = answers;
        for (Answer answer : answers) {
            answer.setQuestion(this);
            answer.setRight(answer == rightAnswer);
        }

    }


    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setQuestion(this);
    }


    @JsonIgnore
    public Answer getRightAnswer() {

        for (Answer answer : answers) {
            if (answer.isRight()) {
                return answer;
            }
        }

        return null;

    }

    public void setRightAnswer(Answer rightAnswer) {
        for (Answer answer : answers) {
            answer.setRight(false);
            if (answer == rightAnswer) {
                answer.setRight(true);
            }
        }
    }

    public int getPointsForRightAnswer() {
        return pointsForRightAnswer;
    }

    public void setPointsForRightAnswer(int pointsForRightAnswer) {
        this.pointsForRightAnswer = pointsForRightAnswer;
    }

    public float getPercentOfPeopleRight() {
        return percentOfPeopleRight;
    }

    public void setPercentOfPeopleRight(float percentOfPeopleRight) {
        this.percentOfPeopleRight = percentOfPeopleRight;
    }

}
