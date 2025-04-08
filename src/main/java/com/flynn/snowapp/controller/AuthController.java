package com.flynn.snowapp.controller;

import com.flynn.snowapp.dto.UserLoginRequestDto;
import com.flynn.snowapp.dto.UserRegisterRequestDto;
import com.flynn.snowapp.model.User;
import com.flynn.snowapp.repository.UserRepository;
import com.flynn.snowapp.service.AuthService;
import com.flynn.snowapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequestDto request) {
        userService.register(request);
        return ResponseEntity.ok("Użytkownik zarejestrowany");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto request) {
        try {
            String token = userService.login(request);
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Użytkownik nie istnieje.");
        }
        userRepository.delete(userOpt.get());
        return ResponseEntity.ok("Użytkownik usunięty.");
    }

    @PostMapping("/auth/google")
    public ResponseEntity<Map<String, String>> loginWithGoogle(@RequestBody Map<String, String> body) {
        System.out.println("✅ Odebrano żądanie logowania przez Google");
        String googleToken = body.get("credential");
        System.out.println("🔐 Google token: " + googleToken);

        String jwt = authService.handleGoogleLogin(googleToken);
        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        return ResponseEntity.ok(response);
    }

}