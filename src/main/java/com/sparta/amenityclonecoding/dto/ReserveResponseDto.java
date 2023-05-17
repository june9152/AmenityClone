package com.sparta.amenityclonecoding.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReserveResponseDto {
    private Long id;
    private String username;
    private String payMethod;
    private String userEmail;
    private String roomNm;
    private String amenityNm;
    private Long price;
    private String reserveStartDate;
    private String reserveEndDate;
    private String amenityImg;

}

