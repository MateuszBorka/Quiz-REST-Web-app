package com.example.quizrestwebapp.Question;

import com.example.quizrestwebapp.Answer.Answer;
import com.example.quizrestwebapp.Quiz.Quiz;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "questions")
public class Question {

    private @Id
    @GeneratedValue Long id;
    private String body;
    @JsonIgnore
    private int rightAnswer;
    private int pointsForRightAnswer;
    private float percentOfPeopleRight;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;


    public Question(String body, List<Answer> answers, int rightAnswer,
                    int pointsForRightAnswer, float percentOfPeopleRight) {

        this.body = body;
        this.rightAnswer = rightAnswer;
        this.pointsForRightAnswer = pointsForRightAnswer;
        this.percentOfPeopleRight = percentOfPeopleRight;
        this.answers = answers;
        for (Answer answer: answers){
            answer.setQuestion(this);
        }

    }

    public Question() {

    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setQuestion(this);
    }


    @JsonIgnore
    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
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

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", answers=" + answers +
                ", rightAnswer=" + rightAnswer +
                ", pointsForRightAnswer=" + pointsForRightAnswer +
                ", percentOfPeopleRight=" + percentOfPeopleRight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return rightAnswer == question.rightAnswer && pointsForRightAnswer == question.pointsForRightAnswer && Float.compare(question.percentOfPeopleRight, percentOfPeopleRight) == 0 && Objects.equals(id, question.id) && Objects.equals(body, question.body) && Objects.equals(answers, question.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, body, answers, rightAnswer, pointsForRightAnswer, percentOfPeopleRight);
    }
}
