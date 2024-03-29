package com.madityafr.room.reservationservice.services;

import com.madityafr.room.reservationservice.dto.PaginateDTO;
import com.madityafr.room.reservationservice.dto.ReservationDTO;
import com.madityafr.room.reservationservice.dto.ReservationListDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationService {
    void addReservation(ReservationDTO reservationDTO);

    PaginateDTO<List<ReservationListDTO>> getAllRerservation(Pageable pageable);

    void updateReservation(Long id, ReservationDTO reservationDTO);

    void acceptedReservation(Long id);

    void rejectReservation(Long id);

    PaginateDTO<List<ReservationListDTO>> getReservationListByID(Pageable pageable, Long id);

    ReservationDTO getReservationByID(Long id);

    Integer countReservation();

    List<Integer> countStatus();
}
