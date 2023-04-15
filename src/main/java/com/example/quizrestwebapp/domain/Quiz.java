package com.example.quizrestwebapp.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "quizes")
public class Quiz {         //Todo add questions count, time limit

    private String title;
    private int difficulty;

    private long playedTimes;

    private String creatorNickname;

    private double rating = 1;

    private @Id
    @GeneratedValue Long id;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;


    public Quiz(String title, int difficulty, String creatorNickname, List<Question> questions) {
        this.title = title;
        this.difficulty = difficulty;
        this.creatorNickname = creatorNickname;
        this.questions = questions;
    }

    public Quiz() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public long getPlayedTimes() {
        return playedTimes;
    }

    public void setPlayedTimes(long playedTimes) {
        this.playedTimes = playedTimes;
    }

    public String getCreatorNickname() {
        return creatorNickname;
    }

    public void setCreatorNickname(String creatorNickname) {
        this.creatorNickname = creatorNickname;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
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
