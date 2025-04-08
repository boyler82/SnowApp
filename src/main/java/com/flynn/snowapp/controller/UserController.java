package com.flynn.snowapp.controller;

import com.flynn.snowapp.dto.ResponseReportDto;
import com.flynn.snowapp.dto.UserPrivateDto;
import com.flynn.snowapp.dto.UserPublicDto;
import com.flynn.snowapp.model.User;
import com.flynn.snowapp.repository.ReportRepository;
import com.flynn.snowapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

    @GetMapping("/me")
    public ResponseEntity<UserPrivateDto> getMyProfile(Authentication auth) {
        var user = userRepository.findByEmail(auth.getName()).orElseThrow();
        return ResponseEntity.ok(UserPrivateDto.from(user));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserPublicDto> getUserByUsername(@PathVariable String username) {
        var user = userRepository.findByUsername(username).orElseThrow();
        return ResponseEntity.ok(UserPublicDto.from(user));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ResponseReportDto>> getMyReports(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();

        List<ResponseReportDto> reports = reportRepository.findByCreatedBy(user)
                .stream()
                .map(ResponseReportDto::from)
                .toList();

        return ResponseEntity.ok(reports);
    }
}
