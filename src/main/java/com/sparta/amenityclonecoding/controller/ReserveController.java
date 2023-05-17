package com.sparta.amenityclonecoding.controller;

import com.sparta.amenityclonecoding.dto.*;
import com.sparta.amenityclonecoding.entity.Reserve;
import com.sparta.amenityclonecoding.exception.Message;
import com.sparta.amenityclonecoding.security.UserDetailsImpl;
import com.sparta.amenityclonecoding.service.AmenityService;
import com.sparta.amenityclonecoding.service.ReserveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "ReserveController", description = "예약 정보 Controller")
@RequestMapping("/api/reserve")
public class ReserveController {
    private final ReserveService reserveService;

    // 예약 조회
    @Operation(summary = "호텔/펜션 예약 등록 API" , description = "호텔/펜션에서 예약 등록 했을 때 ")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "예약 등록할 때")})
    @GetMapping("/myreservelist")
    public List<ReserveResponseDto> getAmenityInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reserveService.getReserveInfo(userDetails.getUser());
    }

    /*
    @Operation(summary = "호텔/펜션 가예약 API" , description = "호텔/펜션에서 가예약 페이지 눌렀을 때 ")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "가예약 때")})
    @PostMapping("/getReserve")
    public List<ReserveResponseDto> getAmenityInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reserveService.getReserveInfo(userDetails.getUser());
    }
     */

    @Operation(summary = "예약 날짜 확인 API" , description = "날짜 조회시 예약정보 반영")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "날짜 조회시 예약정보 반영")})
    @PostMapping("/getReserve/{amenityId}/{startDat}/{endDat}")
    public List<ChkRoomResponseDto> chkReserveDat(
            @PathVariable Long amenityId,
            @PathVariable String startDat,
            @PathVariable String endDat
    ) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date startDate = dateFormat.parse(startDat);
        Date endDate = dateFormat.parse(endDat);
        return reserveService.chkReservDat(amenityId, startDate, endDate);
    }


    // 예약 등록
    @Operation(summary = "호텔/펜션 예약 등록 API" , description = "호텔/펜션에서 예약 등록 했을 때 ")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "예약 등록할 때")})
    @PostMapping("/register")
    public ResponseDto setReserveInfo(@RequestBody ReserveRequestDto reserveRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean ret = reserveService.setReserveInfo(reserveRequestDto,userDetails.getUser());
        return ret == true ? new ResponseDto("예약 성공", HttpStatus.OK) :new ResponseDto("예약 실패", HttpStatus.NOT_FOUND) ;
    }

    // 예약 삭제
    @Operation(summary = "호텔/펜션 예약 등록 API" , description = "호텔/펜션에서 예약 등록 했을 때 ")
    @ApiResponses(value ={@ApiResponse(responseCode= "200", description = "예약 등록할 때")})
    @ResponseBody
    @DeleteMapping("/myreservelist/delete/{reserveId}")
    public ResponseDto deleteAmenityInfo(@PathVariable Long reserveId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean ret = reserveService.deleteReserveInfo(reserveId,userDetails.getUser());
        return ret == true ? new ResponseDto("삭제 성공", HttpStatus.OK) :new ResponseDto("삭제 실패", HttpStatus.NOT_FOUND) ;
    }
}
