package com.flynn.snowapp.dto;

import com.flynn.snowapp.model.Report;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseReportDto {
    private Long id;
    private String title;
    private String description;
    private Double latitude;
    private Double longitude;
    private String status;
    private String createdBy;
    private String executedBy;

    public static ResponseReportDto from(Report report) {
        return ResponseReportDto.builder()
                .id(report.getId())
                .title(report.getTitle())
                .description(report.getDescription())
                .latitude(report.getLatitude())
                .longitude(report.getLongitude())
                .status(report.getStatus().name())
                .createdBy(report.getCreatedBy().getUsername())
                .executedBy(report.getExecutedBy() != null ? report.getExecutedBy().getUsername() : null)
                .build();
    }
}