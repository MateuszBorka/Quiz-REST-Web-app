package com.example.quizrestwebapp.User;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    //Todo: add more fields. Time from registration, how much quizzes solved, solved quizzes, how much quizzes created, percentage of right answers.


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private String username;
    private String password;
    private UserRole role;
}
