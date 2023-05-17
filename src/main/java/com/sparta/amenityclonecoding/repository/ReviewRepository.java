package com.sparta.amenityclonecoding.repository;

import com.sparta.amenityclonecoding.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewByAmenity_AmenityId(Long amenityId);

    @Query("select avg(r.reviewStar) from TB_REVIEW r where r.amenity.amenityId = :amenityId" )
    double getStarAvg(@Param("amenityId") Long amenityId);

    @Query("select avg(r.reviewScore) from TB_REVIEW r where r.amenity.amenityId = :amenityId" )
    double getScoreAvg(@Param("amenityId") Long amenityId);

}
