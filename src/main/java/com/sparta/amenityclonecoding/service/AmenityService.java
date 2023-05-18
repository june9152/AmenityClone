package com.sparta.amenityclonecoding.service;

import com.sparta.amenityclonecoding.dto.*;
import com.sparta.amenityclonecoding.entity.*;
import com.sparta.amenityclonecoding.exception.Message;
import com.sparta.amenityclonecoding.exception.StatusEnum;
import com.sparta.amenityclonecoding.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AmenityService {

    private final AmenityRepository amenityRepository;
    private final AmenityImgRepository amenityImgRepository;
    private final RoomRepository roomRepository;
    private final RoomImgRepository roomImgRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;
    private final ReserveRepository reserveRepository;



    // 하나 눌렀을 때 :
    @Transactional(readOnly = true)
    public ResponseEntity<Message> getAmenityDetail(Long amenityId) {
        Amenity amenity = amenityRepository.findAmenityByAmenityId(amenityId);
        AmenityDetailDto amenityDetailDto = new AmenityDetailDto(amenity);

        //숙박업소 정보 img url mapping 준비
        List<AmenityImg> amenityImgList = amenityImgRepository.findAmenityImgByAmenity_AmenityId(amenityId);
        List<AmenityImgDto> amenityImgDtoList = new ArrayList<>();

        //숙박업소 img url 리스트 매핑
        for (AmenityImg amenityImg : amenityImgList) {
            AmenityImgDto amenityImgDto = new AmenityImgDto(amenityImg);
            amenityImgDtoList.add(amenityImgDto);
        }
        amenityDetailDto.setAmenityImgDtoList(amenityImgDtoList);

        //객실정보 + 객실정보 img url mapping 준비
        List<Room> roomList = roomRepository.findRoomByAmenity_AmenityId(amenityId);
        List<RoomImgDto> roomImgDtoList = new ArrayList<>();
        List<RoomDto> roomDtoList = new ArrayList<>();

        //객실정보당 img url 값 mapping
        for (Room room : roomList) {
            RoomDto roomDto = new RoomDto(room);
            List<RoomImg> roomImgList = roomImgRepository.findRoomImgByRoom_RoomId(room.getRoomId());
            for (RoomImg roomImg : roomImgList) {
                RoomImgDto roomImgDto = new RoomImgDto(roomImg);
                roomImgDtoList.add(roomImgDto);
            }
            roomDto.setRoomImgDtoList(roomImgDtoList);
            roomDtoList.add(roomDto);
        }

        //리뷰정보 + 리뷰정보 img url mapping 준비
        List<Review> reviewList = reviewRepository.findReviewByAmenity_AmenityId(amenityId);
        List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();
        List<ReviewDto> reviewDtoList = new ArrayList<>();
        for (Review review : reviewList) {
            ReviewDto reviewDto = new ReviewDto(review);
            List<ReviewImg> reviewImgList = reviewImgRepository.findReviewImgByReview_ReviewId(review.getReviewId());
            //리뷰정보당 img url 값 mapping
            for (ReviewImg reviewImg : reviewImgList) {
                ReviewImgDto reviewImgDto = new ReviewImgDto(reviewImg);
                reviewImgDtoList.add(reviewImgDto);
            }
            reviewDto.setReviewImgList(reviewImgDtoList);
            reviewDtoList.add(reviewDto);
        }

        //숙박업소 정보(+사진) + 객실 정보(+사진) + 리뷰(+사진)
        amenityDetailDto.setRoomDtoList(roomDtoList);
        amenityDetailDto.setReviewDtoList(reviewDtoList);

        Message message = Message.setSuccess(StatusEnum.OK, "성공", amenityDetailDto);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    // 호텔과 펜션 목록 조회
    @Transactional(readOnly = true)
    public ResponseEntity<Message> getAmenityInfo(Long amenityType) {
        List<Amenity> amenityList = new ArrayList<>();
        List<AmenityDto> amenityDtoList = new ArrayList<>();

        switch (amenityType.toString()) {
            case "0": //호텔
                amenityList = amenityRepository.findAmenityByAmenityTypeAndAmenityLocationAndAmenityDetailLocation(amenityType, "서울", "0");
                break;
            case "1":  //펜션
                amenityList = amenityRepository.findAmenityByAmenityTypeAndAmenityLocationAndAmenityDetailLocation(amenityType, "경기/인천", "0");
                break;
        }

        for (Amenity amenity : amenityList) {
            List<AmenityImgDto> amenityImgDtoList = new ArrayList<>();
            AmenityDto amenityDto = new AmenityDto(amenity);
            List<AmenityImg> amenityImgList = amenityImgRepository.findAmenityImgByAmenity_AmenityId(amenity.getAmenityId());
            for (AmenityImg img : amenityImgList) {
                amenityImgDtoList.add(new AmenityImgDto(img));
            }

            amenityDto.setAmenityImgDtoList(amenityImgDtoList);
            amenityDtoList.add(amenityDto);
        }

        Message message = Message.setSuccess(StatusEnum.OK, "성공", amenityDtoList);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    // 호텔에서 적용 눌렀을 때
    @Transactional(readOnly = true)
    public ResponseEntity<Message> getAmenityFilter(AmenityRequestDto amenityRequestDto) {
        List<Amenity> amenityList = new ArrayList<>();
        List<AmenityDto> amenityDtoList = new ArrayList<>();
        HashMap<Long, Long> chkCnt = new HashMap<>();
        String sDat = amenityRequestDto.getAmenitySdat();
        String eDat = amenityRequestDto.getAmenityEdat();
        List<Reserve> reserveList = reserveRepository.chkReserveDat(sDat, eDat);
        Long amenityId = 0L;
        Long roomCnt = 0L;
        Long resvCnt = 0L;

        for(Reserve reserve: reserveList) {
            if (chkCnt.containsKey(reserve.getAmenityId())) {
                chkCnt.put(reserve.getAmenityId(), chkCnt.get(reserve.getAmenityId()) + 1L);
            } else {
                chkCnt.put(reserve.getAmenityId(), 1L);
            }
        }

        amenityList = amenityRepository.searchFilter(amenityRequestDto);

        for (Amenity amenity : amenityList) {
            amenityId = amenity.getAmenityId();
            List<AmenityImgDto> amenityImgDtoList = new ArrayList<>();
            //기간내 조회한 예약내역 테이블의 숙박업소 ID값이 존재한다면
            if(chkCnt.containsKey(amenityId)) {
                //현재 숙박업소의 룸 카운트와 예약내역 테이블의 숙박업소 아이디로 조회한 값 카운트를 비교
                roomCnt = roomRepository.countRoomByAmenity_AmenityId(amenityId);
                resvCnt = chkCnt.get(amenityId);
                if(roomCnt > resvCnt) {
                    AmenityDto amenityDto = new AmenityDto(amenity);
                    amenityDtoList.add(amenityDto);
                    if(amenityImgDtoList.size() < 1) {
                        AmenityImg amenityImg = amenityImgRepository.findAmenityImgByAmenity_AmenityIdAndImgCnt(amenity.getAmenityId(), 0L);
                        AmenityImgDto amenityImgDto = new AmenityImgDto(amenityImg);
                        amenityImgDtoList.add(amenityImgDto);
                    }
                    amenityDto.setAmenityImgDtoList(amenityImgDtoList);
                }
            }
            else
            {
                AmenityDto amenityDto = new AmenityDto(amenity);
                amenityDtoList.add(amenityDto);
                if(amenityImgDtoList.size() < 1) {
                    AmenityImg amenityImg = amenityImgRepository.findAmenityImgByAmenity_AmenityIdAndImgCnt(amenity.getAmenityId(), 0L);
                    AmenityImgDto amenityImgDto = new AmenityImgDto(amenityImg);
                    amenityImgDtoList.add(amenityImgDto);
                }
                amenityDto.setAmenityImgDtoList(amenityImgDtoList);
            }
        }
        Message message = Message.setSuccess(StatusEnum.OK, "성공", amenityDtoList);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Message> search(String keyword) {
//        List<AmenityDto> amenityDtoList = amenityRepository.findByKeyword(keyword)
//                .stream()
//                .map(AmenityDto::new)
//                .collect(Collectors.toList());

        List<Amenity> amenityList = amenityRepository.findByKeyword(keyword);
        List<AmenityDto> amenityDtoList = new ArrayList<>();
        List<AmenityImgDto> amenityImgDtoList = new ArrayList<>();

        for (Amenity amenity : amenityList) {
            AmenityDto amenityDto = new AmenityDto(amenity);
            amenityDtoList.add(amenityDto);

            if(amenityImgDtoList.size() < 1) {
                AmenityImg amenityImg = amenityImgRepository.findAmenityImgByAmenity_AmenityIdAndImgCnt(amenity.getAmenityId(), 0L);
                AmenityImgDto amenityImgDto = new AmenityImgDto(amenityImg);
                amenityImgDtoList.add(amenityImgDto);
            }

            amenityDto.setAmenityImgDtoList(amenityImgDtoList);
        }


//        return amenityDtoList;
        Message message = Message.setSuccess(StatusEnum.OK, "성공", amenityDtoList);
        return new ResponseEntity<Message>(message, HttpStatus.OK);
    }

}

