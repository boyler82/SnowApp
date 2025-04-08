package com.flynn.snowapp.service;


import com.flynn.snowapp.dto.UserLoginRequestDto;
import com.flynn.snowapp.dto.UserRegisterRequestDto;
import com.flynn.snowapp.model.User;
import com.flynn.snowapp.repository.UserRepository;
import com.flynn.snowapp.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void register(UserRegisterRequestDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Użytkownik z tym adresem e-mail już istnieje");
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
    }

    public String login(UserLoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Nieprawidłowe dane logowania");
        }

        return jwtService.generateToken(user);
    }
}