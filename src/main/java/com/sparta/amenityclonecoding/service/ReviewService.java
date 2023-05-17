package com.sparta.amenityclonecoding.service;

import com.sparta.amenityclonecoding.dto.*;
import com.sparta.amenityclonecoding.entity.*;
import com.sparta.amenityclonecoding.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ReviewImgRepository reviewImgRepository;
    private final AmenityRepository amenityRepository;
    private final ReserveRepository reserveRepository;
    private final S3Service s3Service;

    // 리뷰 작성
    @Transactional
    public ResponseDto writeReview(Long amenityId, ReviewRequestDto requestDto, List<MultipartFile> image, User user) throws IOException {
        User member = userRepository.findUserByUserEmail(user.getUserEmail()).orElseThrow(
                () -> new IllegalArgumentException("로그인 후 이용 가능합니다.")
        );
        String reviewTitle = requestDto.getReviewTitle();
        String reviewContents = requestDto.getReviewContent();
        double reviewStar = requestDto.getReviewStar();
        double reviewScore = requestDto.getReviewScore();
        Amenity amenity = amenityRepository.findAmenityByAmenityId(amenityId);
        List<Reserve> reservesList = reserveRepository.findReserveByAmenityIdAndUserIdOrderByCreateDateDesc(amenityId, user.getUserId());
        Review review = null;
        List<ReviewImg> reviewImgList = new ArrayList<>();
        if(reservesList.size() > 0) {
            review = new Review(reviewTitle, reviewStar, reviewScore, reviewContents, user, amenity, reservesList.get(0));
            Long mainCnt = 0L;
            Long chkCnt = 0L;
            int imgCnt = 0;
            List<String> imgPaths = s3Service.upload(image, "Review");
            for (String url : imgPaths) {
                chkCnt = reviewImgRepository.findImg_ReviewId(review.getReviewId());
                if (!chkCnt.equals(mainCnt)) {
                    mainCnt = chkCnt++;
                }
                ReviewImg reviewImg = new ReviewImg(imgPaths.get(imgCnt), review, mainCnt);
                reviewImgList.add(reviewImg);
            }
            review.setReviewImgList(reviewImgList);
            reviewRepository.save(review);
            return new ResponseDto("리뷰 작성 성공", HttpStatus.OK);
        }
        else
            return new ResponseDto("리뷰 작성 권한이 없습니다", HttpStatus.BAD_REQUEST);
    }

    @Transactional(readOnly = true)
    public ReviewDetailDto getReview(Long amenityId, List<MultipartFile> image) {
        List<Review> reviewList = reviewRepository.findReviewByAmenity_AmenityId(amenityId);
        double starAvg = reviewRepository.getStarAvg(amenityId);
        double scoreAvg = reviewRepository.getScoreAvg(amenityId);
        List<ReviewDto> reviewDtoList = null;
        List<ReviewImgDto> reviewImgDtoList = null;

        for(Review review: reviewList) {
            ReviewDto reviewDto = new ReviewDto(review);
            List<ReviewImg> reviewImgList = reviewImgRepository.findReviewImgByReview_ReviewId(review.getReviewId());
            for(ReviewImg reviewImg: reviewImgList) {
                ReviewImgDto reviewImgDto = new ReviewImgDto(reviewImg);
                reviewImgDtoList.add(reviewImgDto);
            }
            reviewDto.setReviewImgList(reviewImgDtoList);
            reviewDtoList.add(reviewDto);
        }

        return new ReviewDetailDto(starAvg, scoreAvg, reviewDtoList);
    }
}


// review image 를 먼저 저장 후 review Id 와 연결
// 리뷰 저장 (id) -> 리뷰 id 값을 매개변수로 넘겨준다 리뷰 id 와 리뷰 이미지를 같이 저장