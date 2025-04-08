package com.flynn.snowapp.service;


import com.flynn.snowapp.model.User;
import com.flynn.snowapp.repository.UserRepository;
import com.flynn.snowapp.security.JwtService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    @Value("${google.client.id}")
    private String googleClientId;

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public String login(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje"));

        return jwtService.generateToken(user);
    }

    public String register(String email, String username, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Użytkownik już istnieje");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(newUser);

        return jwtService.generateToken(newUser);
    }

    public String handleGoogleLogin(String idTokenString) {
        System.out.println("🔐 Otrzymano Google ID Token: " + idTokenString);
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(googleClientId))
                    .build();

            System.out.println("🔐 Otrzymano Google ID Token: " + idTokenString);
            GoogleIdToken idToken = verifier.verify(idTokenString);
            System.out.println("🔐 Otrzymano Google ID Token: " + idTokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                String email = payload.getEmail();
                String name = (String) payload.get("name");

                User user = userRepository.findByEmail(email)
                        .orElseGet(() -> {
                            System.out.println("✅ Tworzę nowego użytkownika: " + email);  // <- TUTAJ
                            User newUser = new User();
                            newUser.setEmail(email);
                            newUser.setUsername(name != null ? name : email);
                            newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString())); // 👈 ważne!
                            return userRepository.save(newUser);
                        });

                return jwtService.generateToken(user);
            } else {
                throw new RuntimeException("❌ Token Google ID niepoprawny.");
            }
        } catch (Exception e) {
            throw new RuntimeException("❌ Błąd logowania przez Google: " + e.getMessage());
        }
    }
}
