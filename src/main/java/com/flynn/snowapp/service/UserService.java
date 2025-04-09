package com.flynn.snowapp.service;


import com.flynn.snowapp.dto.UserLoginRequestDto;
import com.flynn.snowapp.dto.UserRegisterRequestDto;
import com.flynn.snowapp.exception.EmailAlreadyExistsException;
import com.flynn.snowapp.exception.EmailNotVerifiedException;
import com.flynn.snowapp.model.User;
import com.flynn.snowapp.repository.UserRepository;
import com.flynn.snowapp.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    @Transactional
    public void register(UserRegisterRequestDto request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (user.isEnabled()) {
                throw new EmailAlreadyExistsException("Użytkownik z tym adresem e-mail już istnieje");
            } else {
                // Użytkownik istnieje, ale nie jest aktywowany — wygeneruj nowy token
                String newToken = UUID.randomUUID().toString();
                user.setVerificationToken(newToken);
                userRepository.save(user);
                emailService.sendVerificationEmail(user.getEmail(), newToken);
                throw new EmailNotVerifiedException("Użytkownik istnieje, ale nieaktywowany. Wysłano ponownie link aktywacyjny.");
            }
        }

        // Rejestracja nowego użytkownika
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        String verificationToken = UUID.randomUUID().toString();

        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encodedPassword)
                .enabled(false)
                .locked(false)
                .verificationToken(verificationToken)
                .build();

        userRepository.save(newUser);
        emailService.sendVerificationEmail(newUser.getEmail(), verificationToken);
    }

    public String login(UserLoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Nieprawidłowe dane logowania");
        }

        if (!user.isEnabled()) {
            throw new RuntimeException("✉️ Konto nieaktywne – sprawdź swój e-mail i kliknij link aktywacyjny.");
        }

        return jwtService.generateToken(user);
    }
}