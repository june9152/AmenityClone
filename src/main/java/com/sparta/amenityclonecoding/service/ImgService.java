package com.sparta.amenityclonecoding.service;

import com.sparta.amenityclonecoding.dto.ResponseDto;
import com.sparta.amenityclonecoding.dto.ReviewDto;
import com.sparta.amenityclonecoding.entity.*;
import com.sparta.amenityclonecoding.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
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
    public ResponseDto uploadImg(Long mapId, List<MultipartFile> image, String chkId) throws IOException {
        List<String> imgPaths = s3Service.upload(image, chkId);
        Amenity amenity = null;
        Room room = null;
        Review review = null;

        switch (chkId) {
            case "Amenity":
                amenity = amenityRepository.findById(mapId).orElseThrow( () -> new IllegalArgumentException("매칭되는 ID가 없어요."));
                break;
            case "Room":
                room = roomRepository.findById(mapId).orElseThrow( () -> new IllegalArgumentException("매칭되는 ID가 없어요."));
                break;
            case  "Review":
                review = reviewRepository.findById(mapId).orElseThrow( () -> new IllegalArgumentException("매칭되는 ID가 없어요."));
                break;
        }
        Long mainCnt = 0L;

        for(String img: imgPaths) {
            Long chkCnt = amenityImgRepository.findImg_AmenityId(mapId);

            if(!chkCnt.equals(mainCnt)) {
                mainCnt = chkCnt++;
            }

            switch (chkId) {
                case "Amenity":
                    AmenityImg amenityImg = new AmenityImg(img, amenity, mainCnt);
                    amenityImgRepository.save(amenityImg);
                    break;
                case "Room":
                    RoomImg roomImg = new RoomImg(img, room, mainCnt);
                    break;
                case  "Review":
                    ReviewImg reviewImg = new ReviewImg(img, review, mainCnt);
                    break;
            }
        }

        return new ResponseDto("사진 업로드 성공", HttpStatus.OK);
    }

}
