package com.sparta.amenityclonecoding.controller;

import com.sparta.amenityclonecoding.dto.ResponseDto;
import com.sparta.amenityclonecoding.dto.ReviewDetailDto;
import com.sparta.amenityclonecoding.dto.ReviewDto;
import com.sparta.amenityclonecoding.dto.ReviewRequestDto;
import com.sparta.amenityclonecoding.security.UserDetailsImpl;
import com.sparta.amenityclonecoding.service.ReviewService;
import com.sparta.amenityclonecoding.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "ReviewController", description = "리뷰 Controller")
public class ReviewController {

    private final ReviewService reviewService;
    private final S3Service s3Service;

    // API 를 계층형으로 바꿔봤씀다! : amenity 의 detail 에서 review 목록 조회

    @Operation(summary = "숙박업소 상세 조회 내 토큰값 없이 리뷰 조회 API" , description = "숙박업소 리뷰 조회")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "숙박업소 리뷰 조회")})
    @GetMapping("/api/amenity/detail/{amenityId}/review")
    public ReviewDetailDto getReview(@PathVariable("amenityId") Long amenityId) {
        return reviewService.getReview(amenityId);
    }


    @Operation(summary = "숙박업소 상세 조회 내  로그인 유저만 리뷰 작성 API" , description = "숙박업소 리뷰 작성")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "숙박업소 리뷰 작성")})
    @PostMapping("/api/amenity/detail/{amenityId}/review")
    public ResponseDto writeReview(@PathVariable Long amenityId,
                                   @RequestPart ReviewRequestDto requestDto,
                                   @RequestPart( value = "image", required = false) List<MultipartFile> image,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        return reviewService.writeReview(amenityId, requestDto, image, userDetails.getUser());
    }
}
