package com.sparta.amenityclonecoding.service;

import com.sparta.amenityclonecoding.dto.ResponseDto;
import com.sparta.amenityclonecoding.dto.ReviewDto;
import com.sparta.amenityclonecoding.entity.*;
import com.sparta.amenityclonecoding.exception.Message;
import com.sparta.amenityclonecoding.exception.StatusEnum;
import com.sparta.amenityclonecoding.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ImgService {

    private final AmenityRepository amenityRepository;
    private final AmenityImgRepository amenityImgRepository;
    private final RoomRepository roomRepository;
    private final RoomImgRepository roomImgRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewImgRepository reviewImgRepository;
    private final S3Service s3Service;

    // 리뷰 작성
    @Transactional
    public ResponseEntity<Message> uploadImg(Long mapId, List<MultipartFile> image, String chkId) throws IOException {
        List<String> imgPaths = s3Service.upload(image, chkId);
        Amenity amenity = null;
        List<Room> roomList = null;
        List<Review> reviewList = null;

        switch (chkId) {
            case "Amenity":
                amenity = amenityRepository.findById(mapId).orElseThrow( () -> new IllegalArgumentException("매칭되는 ID가 없어요."));
                break;
            case "Room":
                roomList = roomRepository.findRoomByAmenity_AmenityId(mapId);
                break;
            case  "Review":
                reviewList = reviewRepository.findReviewByAmenity_AmenityId(mapId);
                break;
        }

        Long mainCnt = 0L;
        Long chkCnt = 0L;
        int imgCnt = 0;

        switch (chkId) {
            case "Amenity":
                for(String img: imgPaths) {
                    chkCnt = 0L;

                    chkCnt = amenityImgRepository.findImg_AmenityId(mapId);

                    if(!chkCnt.equals(mainCnt)) {
                        mainCnt = chkCnt++;
                    }
                    AmenityImg amenityImg = new AmenityImg(img, amenity, mainCnt);
                    amenityImgRepository.save(amenityImg);
                }
                break;
            case "Room":
                for(Room room: roomList) {
                    chkCnt = roomImgRepository.findImg_AmenityId(room.getRoomId());
                    if(!chkCnt.equals(mainCnt)) {
                        mainCnt = chkCnt++;
                    }
                    RoomImg roomImg = new RoomImg(imgPaths.get(imgCnt), room, mainCnt);
                    roomImgRepository.save(roomImg);
                    imgCnt++;
                }
                break;
            case  "Review":
                for(Review review: reviewList) {
                    chkCnt = reviewImgRepository.findImg_ReviewId(review.getReviewId());
                    if(!chkCnt.equals(mainCnt)) {
                        mainCnt = chkCnt++;
                    }
                    ReviewImg reviewImg = new ReviewImg(imgPaths.get(imgCnt), review, mainCnt);
                    reviewImgRepository.save(reviewImg);
                    imgCnt++;
                }
                break;
        }

        Message message = Message.setSuccess(StatusEnum.OK, "사진 업로드 성공");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
