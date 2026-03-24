package com.beautyhub.service;

import com.beautyhub.entity.User;
import com.beautyhub.exception.AuthenticationException;
import com.beautyhub.repository.UserRepository;
import com.beautyhub.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("Usuário não encontrado"));

        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new AuthenticationException("Senha inválida");
        }

        return jwtService.generateToken(user.getEmail(), "USER");
    }

    public void register(String fullName, String email, String phone, String rawPassword) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new AuthenticationException("Email já cadastrado");
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPasswordHash(passwordEncoder.encode(rawPassword));

        userRepository.save(user);
    }
}

