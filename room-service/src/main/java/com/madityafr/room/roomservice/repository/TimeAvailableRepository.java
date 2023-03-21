package com.madityafr.room.roomservice.repository;

import com.madityafr.room.roomservice.entity.TimeAvailable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TimeAvailableRepository extends JpaRepository<TimeAvailable, Long> {
    @Query(value = "SELECT * FROM time_available WHERE reservation_date = ?1 AND id_room = ?2", nativeQuery = true)
    Optional<TimeAvailable> findByidRoomAndReservationDate(LocalDate reservationDate, Long idRoom);
}
