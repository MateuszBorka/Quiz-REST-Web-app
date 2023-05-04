package com.example.quizrestwebapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Table(name = "answers")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Answer {

    private @Id
    @GeneratedValue Long id;
    private String body;
    @JsonIgnore
    private boolean isRight;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Question question;

    public Answer(String body) {
        this.body = body;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (isRight != answer.isRight) return false;
        if (!Objects.equals(id, answer.id)) return false;
        if (!Objects.equals(body, answer.body)) return false;
        return Objects.equals(question, answer.question);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (isRight ? 1 : 0);
        result = 31 * result + (question != null ? question.hashCode() : 0);
        return result;
    }
}
