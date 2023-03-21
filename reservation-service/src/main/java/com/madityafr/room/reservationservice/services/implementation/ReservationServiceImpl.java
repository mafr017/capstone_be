package com.madityafr.room.reservationservice.services.implementation;

import com.madityafr.room.reservationservice.dto.PaginateDTO;
import com.madityafr.room.reservationservice.dto.ReservationDTO;
import com.madityafr.room.reservationservice.dto.ReservationListDTO;
import com.madityafr.room.reservationservice.entity.Reservation;
import com.madityafr.room.reservationservice.entity.Room;
import com.madityafr.room.reservationservice.exception.NotFoundException;
import com.madityafr.room.reservationservice.repository.ReservationRepository;
import com.madityafr.room.reservationservice.repository.RoomRepository;
import com.madityafr.room.reservationservice.services.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ModelMapper modelMapper;
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public void addReservation(ReservationDTO reservationDTO) {
        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        Optional<Room> optionalRoom = roomRepository.findById(Long.valueOf(reservationDTO.getIdRoom()));
        if (optionalRoom.isEmpty()) {
            log.error("Room with id : {} not found!", reservationDTO.getIdRoom(), new NotFoundException("Room not found"));
            throw new NotFoundException("Room Not Found");
        }
        reservation.setRoomEntity(optionalRoom.get());
        reservationRepository.save(reservation);
        log.info("Success add Reservation: {}", reservationRepository);
//            TODO: GET RANGE FROM (END TIME - START TIME) ex (08 - 07) 1 so only set jam_07
    }

    @Override
    public PaginateDTO<List<ReservationListDTO>> getAllRerservation(Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findAll(pageable);
        List<ReservationListDTO> reservationListDTOArrayList = new ArrayList<>();
        for (Reservation reservation : reservations.getContent()) {
            ReservationListDTO reservationListDTO = modelMapper.map(reservation, ReservationListDTO.class);
            reservationListDTOArrayList.add(reservationListDTO);
        }
        log.info("Success get List Reservation");
        return PaginateDTO.<List<ReservationListDTO>>builder().data(reservationListDTOArrayList)
                .totalOfItems(reservations.getTotalElements())
                .totalOfPages(reservations.getTotalPages())
                .currentPage(reservations.getNumber())
                .build();
    }

    @Override
    @Transactional
    public void updateReservation(Long id, ReservationDTO reservationDTO) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isEmpty()) {
            log.error("Reservation with id : {} not found!", id, new NotFoundException("Reservation not found"));
            throw new NotFoundException("Reservation Not Found");
        }

        Reservation reservation = optionalReservation.get();
        if (reservationDTO.getReservationDate() != null) reservation.setReservationDate(reservationDTO.getReservationDate());
        if (reservationDTO.getIdRoom() != null && reservationDTO.getIdRoom() > 0) {
            Optional<Room> optionalRoom = roomRepository.findById(Long.valueOf(reservationDTO.getIdRoom()));
            if (optionalRoom.isEmpty()) {
                log.error("Room with id : {} not found!", reservationDTO.getIdRoom(), new NotFoundException("Room not found"));
                throw new NotFoundException("Room Not Found");
            }
            reservation.setRoomEntity(optionalRoom.get());
        }
        if (reservationDTO.getStartTime() != null && !reservationDTO.getStartTime().isBlank()) reservation.setStartTime(reservationDTO.getStartTime());
        if (reservationDTO.getReservationDate() != null && !reservationDTO.getEndTime().isBlank()) reservation.setEndTime(reservationDTO.getEndTime());
        reservationRepository.save(reservation);
        log.info("Success update Reservation: {}", reservation);
    }

    @Override
    @Transactional
    public void cancelReservation(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isEmpty()) {
            log.error("Reservation with id : {} not found!", id, new NotFoundException("Reservation not found"));
            throw new NotFoundException("Reservation Not Found");
        }
        optionalReservation.get().setStatus("Canceled");
        reservationRepository.save(optionalReservation.get());
        log.info("Success Cancel Room: {}", optionalReservation.get());
    }

    @Override
    public ReservationListDTO getReservationByID(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isEmpty()) {
            log.error("Reservation with id : {} not found!", id, new NotFoundException("Reservation not found"));
            throw new NotFoundException("Reservation Not Found");
        }
        ReservationListDTO reservationListDTO = modelMapper.map(optionalReservation.get(), ReservationListDTO.class);
        log.info("Success get Reservation: {}", optionalReservation.get());
        return reservationListDTO;
    }
}
