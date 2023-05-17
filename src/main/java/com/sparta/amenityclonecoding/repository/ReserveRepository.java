package com.sparta.amenityclonecoding.repository;

import com.sparta.amenityclonecoding.entity.RefreshToken;
import com.sparta.amenityclonecoding.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    List<Reserve> findByUserId(Long userId);

    List<Reserve> findReserveByAmenityIdAndUserIdOrderByCreateDateDesc(Long amenityId, Long userId);

    @Query("select r from TB_RESERVE r where r.reserveStartDate >= :startDat and r.reserveEndDate <= :endDat")
    List<Reserve> chkReserveDat(@Param("startDat") Long startDat, @Param("endDat") Long endDat);

//    @Query(value = "select exists (select 1 from TB_RESERVE r where r.reserveStartDate >= :startDat and r.reserveEndDate <= :endDat and r.amenityId = :amenityId and r.roomId = :roomId)", nativeQuery = true)
//    boolean chkReserve(@Param("startDat") Date startDat, @Param("endDat") Date endDat, @Param("amenityId") Long amenityId, @Param("roomId") Long roomId);
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM TB_RESERVE r WHERE r.reserveStartDate >= :startDat AND r.reserveEndDate <= :endDat AND r.amenityId = :amenityId AND r.roomId = :roomId")
    boolean chkReserve(
            @Param("startDat") String startDat,
            @Param("endDat") String endDat,
            @Param("amenityId") Long amenityId,
            @Param("roomId") Long roomId
    );

}
