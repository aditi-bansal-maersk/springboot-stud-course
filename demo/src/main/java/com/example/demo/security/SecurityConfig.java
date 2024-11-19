package com.example.demo.security;

import com.example.demo.entity.Student;
import com.example.demo.repo.StudentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig
{

    private final StudentRepository studentRepository;

    public SecurityConfig(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Student student = studentRepository.findByUsername(username); // Load student by username
            if (student == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return User.withUsername(student.getUsername())
                    .password(student.getPassword()) // Use stored password
                    .roles(student.getRoles().toArray(new String[0])) // Assign role dynamically based on your logic
                    .build();
        };
    }

    @Bean
    protected DefaultSecurityFilterChain configure(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(request -> request
                        .requestMatchers("/students/**", "/courses/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
