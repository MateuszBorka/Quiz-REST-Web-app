package com.example.quizrestwebapp.domain;

import jakarta.persistence.*;

import java.util.Set;
import java.util.Collections;

@Entity
@Table(name = "users")
public class User {

    //Todo: add more fields. Time from registration, email, how much quizzes solved, solved quizzes, how much quizzes created, percentage of right answers.


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<UserRole> roles;

    public User(String username, String password, Set<UserRole> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User() {

    }

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
