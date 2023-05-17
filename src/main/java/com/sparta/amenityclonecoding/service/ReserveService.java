package com.sparta.amenityclonecoding.service;

import com.sparta.amenityclonecoding.dto.*;
import com.sparta.amenityclonecoding.entity.*;
import com.sparta.amenityclonecoding.exception.Message;
import com.sparta.amenityclonecoding.exception.StatusEnum;
import com.sparta.amenityclonecoding.repository.*;
import com.sparta.amenityclonecoding.security.UserDetailsImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Date.*;

@Service
@RequiredArgsConstructor
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final AmenityRepository amenityRepository;
    private final AmenityImgRepository amenityImgRepository;
    private final RoomImgRepository roomImgRepository;

    @Transactional
    public List<ReserveResponseDto> getReserveInfo(User user) {
        // Retrieve the list of reservations for the given user from the repository
        List<Reserve> reservations = reserveRepository.findByUserId(user.getUserId());

        // Convert the list of reservations to a list of ReserveResponseDto objects
        List<ReserveResponseDto> responseDtoList = new ArrayList<>();
        for (Reserve reservation : reservations) {
            ReserveResponseDto responseDto = new ReserveResponseDto();
            responseDto.setId(reservation.getId());
            responseDto.setUsername(reservation.getUsername());
            responseDto.setPayMethod(reservation.getPayMethod());
            responseDto.setUserEmail(reservation.getUserEmail());

            // Assuming the room and amenity details are fetched from their respective repositories
            Optional<Room> roomOptional = roomRepository.findById(reservation.getRoomId());
            Optional<Amenity> amenityOptional = amenityRepository.findById(reservation.getAmenityId());
            if (roomOptional.isPresent() && amenityOptional.isPresent()) {
                Room room = roomOptional.get();
                Amenity amenity = amenityOptional.get();
                responseDto.setRoomNm(room.getRoomNm());
                responseDto.setAmenityNm(amenity.getAmenityNm());


                List<AmenityImg> amenityImgList = amenityImgRepository.findAmenityImgByAmenity_AmenityId(amenity.getAmenityId());
                if (!amenityImgList.isEmpty()) {
                    AmenityImg amenityImg = amenityImgList.get(0);
                    responseDto.setAmenityImg(amenityImg.getImageUrl());
                }
            }

            responseDto.setPrice(reservation.getPrice());
            responseDto.setReserveStartDate(reservation.getReserveStartDate());
            responseDto.setReserveEndDate(reservation.getReserveEndDate());

            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }


    @Transactional
    public boolean setReserveInfo(ReserveRequestDto reserveRequestDto, User user) {
        User member = userRepository.findUserByUserEmail(user.getUserEmail()).orElseThrow(
                () -> new IllegalArgumentException("로그인 후 이용 가능합니다.")
        );
        String rxEmail = reserveRequestDto.getUserEmail();
        String userEmail = user.getUserEmail();
        if (rxEmail.equals(userEmail) == false)
            return false;
        Reserve reserve = new Reserve();
        reserve.setUserId(member.getUserId());
        reserve.setAmenityId(reserveRequestDto.getAmenityId());
        reserve.setPrice(reserveRequestDto.getPrice());
        reserve.setReserveStartDate(reserveRequestDto.getReserveStartDate());
        reserve.setReserveEndDate(reserveRequestDto.getReserveEndDate());
        reserve.setRoomId(reserveRequestDto.getRoomId());
        reserve.setUsername(reserveRequestDto.getUsername());
        reserve.setPayMethod(reserveRequestDto.getPayMethod());
        reserve.setUserEmail(reserveRequestDto.getUserEmail());
        // 현재 날짜 구하기 (시스템 시계, 시스템 타임존)
        LocalDate now = LocalDate.now();
        // 현재 날짜 구하기(Paris)
        LocalDate seoulNow = LocalDate.now(ZoneId.of("Asia/Seoul"));
        reserve.setCreateDate(seoulNow.toString());

        reserveRepository.save(reserve);
        return true;
    }

    @Transactional
    public boolean deleteReserveInfo(Long reserveId, User user) {
        User member = userRepository.findUserByUserEmail(user.getUserEmail()).orElseThrow(
                () -> new IllegalArgumentException("로그인 후 이용 가능합니다.")
        );


        // Find the reserve by its ID
        Reserve reserve = reserveRepository.findById(reserveId).orElseThrow(
                () -> new IllegalArgumentException("해당 예약 정보를 찾을 수 없습니다.")
        );

        // Perform the deletion
        reserveRepository.delete(reserve);

        return true;
    }

    @Transactional
    public List<ChkRoomResponseDto> chkReservDat(Long amenityId, Date startDat, Date endDat) throws ParseException {
//        Date sDat = new SimpleDateFormat("yyyyMMdd").parse(startDat);

//        Date eDat = new SimpleDateFormat("yyyyMMdd").parse(endDat);
        long diffSec = (((Date) endDat).getTime() - startDat.getTime()) / 1000;

        Long dayChk = diffSec / (24*60*60);
        Long totalPrice = 0L;
        Long roomPrice = 0L;
        Long roomId = 0L;
        boolean chk;


        //1. 객실정보 list 먼저 구성
        //2. 구성 후 해당 list 를 포이치
        //3. 새로운 타입의 dto list에 옮겨담으면서 몇박인지, 총금액 필드 추가
        //4. 조회하는 날짜에 예약정보가 있는방은 제외
        //연관관계가 없으므로 룸값을 전부 조회해야함 -> 그리고 제외

        List<Room> roomList = roomRepository.findRoomByAmenity_AmenityId(amenityId);
        List<ChkRoomResponseDto> chkRoomResponseDtoList = new ArrayList<>();

        for(Room room: roomList) {
            roomId = room.getRoomId();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String strNowDate = simpleDateFormat.format(startDat);
            String endNowDate = simpleDateFormat.format(endDat);
            chk = reserveRepository.chkReserve(strNowDate, endNowDate, amenityId, roomId);
            String valueWithComma = room.getRoomPrice();
            String valueWithoutComma = valueWithComma.replaceAll(",", "");
            int number = Integer.parseInt(valueWithoutComma);
            roomPrice = Long.valueOf(number);
            totalPrice = roomPrice * dayChk;
            //예약정보가 존재한다면 해당값은 보여주지 않음
            String reserveStr = chk == false ? "예약 가능":"예약 마감";
            if(!chk) {
                ChkRoomResponseDto chkRoomResponseDto = new ChkRoomResponseDto(room.getRoomNm(), reserveStr, roomPrice, totalPrice, dayChk);
                List<RoomImg> roomImgList = roomImgRepository.findRoomImgByRoom_RoomId(roomId);
                List<RoomImgDto> roomImgDtoList = new ArrayList<>();
                for(RoomImg roomImg: roomImgList) {
                    RoomImgDto roomImgDto = new RoomImgDto(roomImg);
                    roomImgDtoList.add(roomImgDto);
                }
                chkRoomResponseDto.setRoomImgDtoList(roomImgDtoList);
                chkRoomResponseDtoList.add(chkRoomResponseDto);
            }
        }
        return chkRoomResponseDtoList;
    }
}
