package com.flynn.snowapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String token) {
        String link = "http://localhost:5173/verify?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Potwierdź swoje konto");
        message.setText("Kliknij w link, aby aktywować konto: " + link);

        mailSender.send(message);
        System.out.println("📨 Email weryfikacyjny wysłany do: " + to);
        System.out.println("🔗 Link aktywacyjny: " + link);

    }
}