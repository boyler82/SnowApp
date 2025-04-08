package com.flynn.snowapp.controller;

import com.flynn.snowapp.dto.CreateReportDto;
import com.flynn.snowapp.dto.ResponseReportDto;
import com.flynn.snowapp.model.Report;
import com.flynn.snowapp.model.User;
import com.flynn.snowapp.repository.ReportRepository;
import com.flynn.snowapp.repository.UserRepository;
import com.flynn.snowapp.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.flynn.snowapp.model.ReportStatus;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;


    @PostMapping
    public ResponseEntity<ResponseReportDto> createReport(@RequestBody @Valid CreateReportDto dto, Authentication auth) {
        // username wyciągamy z Authentication (już zautoryzowane przez Spring Security!)
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Report report = Report.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .status(ReportStatus.PENDING)
                .createdBy(user) // <- wcześniej było "creator", nieistniejące
                .build();

        reportRepository.save(report);
        return ResponseEntity.ok(ResponseReportDto.from(report));
    }

    @GetMapping
    public ResponseEntity<List<ResponseReportDto>> getAllReports() {
        List<ResponseReportDto> reports = reportRepository.findAll()
                .stream()
                .map(ResponseReportDto::from)
                .toList();
        return ResponseEntity.ok(reports);
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<?> assignToMe(@PathVariable Long id, Authentication auth) {
        User executor = userRepository.findByEmail(auth.getName()).orElseThrow();
        Report report = reportRepository.findById(id).orElseThrow();

        if (report.getExecutedBy() != null) {
            return ResponseEntity.badRequest().body("Zgłoszenie już przypisane.");
        }

        report.setExecutedBy(executor);
        report.setStatus(ReportStatus.ASSIGNED);
        reportRepository.save(report);
        return ResponseEntity.ok("Zgłoszenie przypisane. Czeka na potwierdzenie zleceniodawcy.");
    }

    @PutMapping("/{id}/confirm-start")
    public ResponseEntity<?> confirmStart(@PathVariable Long id, Authentication auth) {
        Report report = reportRepository.findById(id).orElseThrow();

        if (!auth.getName().equals(report.getCreatedBy().getEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Tylko zgłaszający może zatwierdzić rozpoczęcie.");
        }

        if (report.getStatus() != ReportStatus.ASSIGNED) {
            return ResponseEntity.badRequest().body("Zgłoszenie nie jest jeszcze przypisane.");
        }

        report.setStatus(ReportStatus.IN_PROGRESS);
        reportRepository.save(report);
        return ResponseEntity.ok("Realizacja zgłoszenia rozpoczęta.");
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Long id, Authentication auth) {
        Report report = reportRepository.findById(id).orElseThrow();

        if (!auth.getName().equals(report.getCreatedBy().getEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Nie jesteś właścicielem tego zgłoszenia.");
        }

        if (report.getStatus() != ReportStatus.IN_PROGRESS) {
            return ResponseEntity.badRequest().body("Zgłoszenie nie jest jeszcze w trakcie realizacji.");
        }

        report.setStatus(ReportStatus.DONE);
        reportRepository.save(report);
        return ResponseEntity.ok("Zgłoszenie zakończone przez zgłaszającego.");
    }
    @GetMapping("/available")
    public ResponseEntity<List<ResponseReportDto>> getAvailableReports() {
        List<ResponseReportDto> reports = reportRepository.findByStatus(ReportStatus.PENDING)
                .stream()
                .map(ResponseReportDto::from)
                .toList();

        return ResponseEntity.ok(reports);
    }
}