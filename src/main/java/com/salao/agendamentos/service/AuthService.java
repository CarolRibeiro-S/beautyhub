package com.salao.agendamentos.service;

import com.salao.agendamentos.entity.User;
import com.salao.agendamentos.repository.UserRepository;
import com.salao.agendamentos.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
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
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(rawPassword, user.getSenhaHash())) {
            throw new RuntimeException("Senha inválida");
        }

        return jwtService.generateToken(user.getEmail(), user.getRole().name());
    }
}