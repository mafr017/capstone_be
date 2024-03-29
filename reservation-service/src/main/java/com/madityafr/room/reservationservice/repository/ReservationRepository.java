package com.madityafr.room.reservationservice.repository;

import com.madityafr.room.reservationservice.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query(value = "SELECT * FROM reservation WHERE id_user = ?1", nativeQuery = true)
    Page<Reservation> findByidUser(Long idUser, Pageable pageable);
    @Query(value = "select count(*) from reservation", nativeQuery = true)
    Integer countReservation();
    @Query(value = "select count(*) from reservation r group by r.status order by status asc", nativeQuery = true)
    List<Integer> countStatus();
}
