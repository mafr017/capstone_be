package com.madityafr.roomservice.repository;

import com.madityafr.roomservice.entity.TypeRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRoomRepository extends JpaRepository<TypeRoom, Long> {
}
