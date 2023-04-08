package com.example.quizrestwebapp.User;

import java.util.Optional;

public interface UserRoleRepository {
    Optional<UserRole> findByName(String name);
}
