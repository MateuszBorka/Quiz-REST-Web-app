package com.example.quizrestwebapp.Answer;

import com.example.quizrestwebapp.Question.Question;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "answers")
public class Answer {

    private @Id @GeneratedValue Long id;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    public Answer(String body) {
        this.body = body;
    }

    public Answer() {

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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", body='" + body + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return id.equals(answer.id) && Objects.equals(body, answer.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, body);
    }
}
