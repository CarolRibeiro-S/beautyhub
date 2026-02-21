package com.salao.agendamentos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Para MVP: sem CSRF (facilita formulários simples)
                .csrf(csrf -> csrf.disable())

                // Libera a home e arquivos estáticos
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index", "/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().permitAll()
                )

                // Desliga a tela de login automática
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                .build();
    }
}
