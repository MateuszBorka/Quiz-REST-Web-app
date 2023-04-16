package com.example.quizrestwebapp.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "answers")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Answer {

    private @Id @GeneratedValue Long id;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Question question;

    public Answer(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Answer answer = (Answer) o;
        return id != null && Objects.equals(id, answer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
