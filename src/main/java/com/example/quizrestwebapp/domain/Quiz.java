package com.example.quizrestwebapp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "quizes")
@Getter
@Setter
@NoArgsConstructor
public class Quiz {         //Todo add questions count, time limit

    private String title;
    private int difficulty;

    private long playedTimes;

    private String creatorNickname;         //Todo create link between quiz Creator and User in the database

    private double rating = 1;

    private @Id
    @GeneratedValue Long id;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Question> questions;


    public Quiz(String title, int difficulty, String creatorNickname, List<Question> questions) {
        this.title = title;
        this.difficulty = difficulty;
        this.creatorNickname = creatorNickname;
        this.questions = questions;
    }


    @Override
    public String toString() {
        return "Quiz{" +
                "title='" + title + '\'' +
                ", difficulty=" + difficulty +
                ", playedTimes=" + playedTimes +
                ", creatorNickname='" + creatorNickname + '\'' +
                ", rating=" + rating +
                ", id=" + id +
                ", questions=" + questions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return difficulty == quiz.difficulty && playedTimes == quiz.playedTimes && Double.compare(quiz.rating, rating) == 0 && Objects.equals(title, quiz.title) && Objects.equals(creatorNickname, quiz.creatorNickname) && Objects.equals(id, quiz.id) && Objects.equals(questions, quiz.questions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, difficulty, playedTimes, creatorNickname, rating, id, questions);
    }
}
