package com.sparta.amenityclonecoding.repository;

import com.sparta.amenityclonecoding.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findRoomByAmenity_AmenityId(Long amenityId);
    Optional<Room> findByRoomId(Long roomId);
    Long countRoomByAmenity_AmenityId(Long amenityId);
}
