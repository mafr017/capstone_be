package com.madityafr.room.roomservice.repository;

import com.madityafr.room.roomservice.entity.TypeRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRoomRepository extends JpaRepository<TypeRoom, Long> {
}
