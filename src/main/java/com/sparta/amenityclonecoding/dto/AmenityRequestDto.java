package com.sparta.amenityclonecoding.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class AmenityRequestDto {
    private Long amenityType;
    private String amenitySdat;
    private String amenityEdat;
//    private String amenityNm;
//    private String amenityLocation;
//    private String amenityDetailLocation;
    private String amenityCategory;
    private String amenityPeople;
    private String amenityVal;
    private String[] amenityCommon;
    private String[] amenityIn;
    private String[] amenityEtc;
}
