package com.madityafr.room.roomservice.repository;

import com.madityafr.room.roomservice.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findAll(Pageable pageable);

    @Query(value = "select count(*) from room", nativeQuery = true)
    Integer countRoom();
}
