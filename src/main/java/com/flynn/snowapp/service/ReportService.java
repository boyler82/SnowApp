package com.flynn.snowapp.service;

import com.flynn.snowapp.dto.CreateReportDto;
import com.flynn.snowapp.dto.ResponseReportDto;
import com.flynn.snowapp.model.Report;
import com.flynn.snowapp.repository.ReportRepository;
import com.flynn.snowapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ResponseReportDto create(CreateReportDto dto, Authentication auth) {
        var user = userRepository.findByEmail(auth.getName()).orElseThrow();
        Report report = new Report();
        report.setDescription(dto.getDescription());
        report.setLatitude(dto.getLatitude());
        report.setLongitude(dto.getLongitude());
        report.setCreatedBy(user);
        return ResponseReportDto.from(reportRepository.save(report));
    }

    public List<ResponseReportDto> getAll() {
        return reportRepository.findAll().stream()
                .map(ResponseReportDto::from)
                .toList();
    }

    public ResponseReportDto assignToUser(Long reportId, Authentication auth) {
        var user = userRepository.findByEmail(auth.getName()).orElseThrow();
        var report = reportRepository.findById(reportId).orElseThrow();
        report.setExecutedBy(user);
        return ResponseReportDto.from(reportRepository.save(report));
    }
}