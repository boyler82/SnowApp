package com.flynn.snowapp.dto;

import lombok.Data;

@Data
public class CreateReportDto {
    private String title;
    private String description;
    private Double latitude;
    private Double longitude;
    private String address;
    private String city;
    private String region;
}