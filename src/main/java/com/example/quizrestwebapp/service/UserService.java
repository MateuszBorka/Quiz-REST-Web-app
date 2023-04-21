package com.example.quizrestwebapp.service;

import com.example.quizrestwebapp.domain.User;
import com.example.quizrestwebapp.domain.UserRole;
import com.example.quizrestwebapp.repository.UserRepository;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<User> getByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);
        return user;
    }

    public void createUser(@NonNull String username,  @NonNull String password, @NonNull String email, @NonNull String... roleNames){

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        Set<UserRole> roles = new HashSet<>();
        for (String role : roleNames){
            roles.add(new UserRole(role));
        }

        user.setRoles(roles);
        userRepository.save(user);

    }
}
