package com.trella.ChatApp.config;

import com.trella.ChatApp.repository.PersonRepository;
import com.trella.ChatApp.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
private final PersonRepository personRepository;

    public SecurityConfig(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception{
        http
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/ws/**").authenticated() // Требовать аутентификацию для WebSocket
                        .requestMatchers("/login","/css/**","/js/**").permitAll() // Разрешить доступ к логину и статике
                        .anyRequest().authenticated())  // Остальные запросы требуют аутентификации
                .formLogin( form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/",true) // Редирект после успешного логина
                        .permitAll())
                .logout( logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(personRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
