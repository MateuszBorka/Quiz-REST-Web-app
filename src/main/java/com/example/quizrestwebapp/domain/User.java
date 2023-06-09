package com.example.quizrestwebapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    //Todo: add more fields. Time from registration, email, how much quizzes solved, solved quizzes, how much quizzes created, percentage of right answers.


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    private String username;
    @JsonIgnore
    private String password;

    private String email;

    private int points;

    private int quizzesSolved;

    private int answeredRight;
    @JsonIgnore
    private int answeredWrong;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<UserRole> roles;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoleNames() {
        if (roles.isEmpty()) {
            return Collections.emptySet();
        }
        String roleName = roles.iterator().next().getName();
        return Collections.singleton(roleName);
    }


    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
