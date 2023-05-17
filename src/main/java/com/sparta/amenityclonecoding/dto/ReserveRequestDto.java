package com.sparta.amenityclonecoding.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReserveRequestDto {
    private String username;
    private String payMethod;
    private String userEmail;
    private Long amenityId;
    private Long roomId;
    private Long price;
    private String reserveStartDate;
    private String reserveEndDate;
}
