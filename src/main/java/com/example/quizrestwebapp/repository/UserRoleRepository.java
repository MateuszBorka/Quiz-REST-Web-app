package com.example.quizrestwebapp.repository;

import com.example.quizrestwebapp.domain.UserRole;

import java.util.Optional;

public interface UserRoleRepository {
    Optional<UserRole> findByName(String name);
}
