package com.madityafr.room.reservationservice.services.implementation;

import com.madityafr.room.reservationservice.dto.PaginateDTO;
import com.madityafr.room.reservationservice.dto.ReservationDTO;
import com.madityafr.room.reservationservice.dto.ReservationListDTO;
import com.madityafr.room.reservationservice.entity.Reservation;
import com.madityafr.room.reservationservice.entity.Room;
import com.madityafr.room.reservationservice.entity.User;
import com.madityafr.room.reservationservice.exception.NotFoundException;
import com.madityafr.room.reservationservice.repository.ReservationRepository;
import com.madityafr.room.reservationservice.repository.RoomRepository;
import com.madityafr.room.reservationservice.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addReservation(ReservationDTO reservationDTO) {
        log.info("add Reservation: {}", reservationDTO);
        Optional<Room> optionalRoom = roomRepository.findById(Long.valueOf(reservationDTO.getIdRoom()));
        if (optionalRoom.isEmpty()) {
            log.error("Room with id : {} not found!", reservationDTO.getIdRoom(), new NotFoundException("Room not found"));
            throw new NotFoundException("Room Not Found");
        }
        log.info("get room: {}", optionalRoom.get());


        Optional<User> optionalUser = userRepository.findById(Long.valueOf(reservationDTO.getIdUser()));
        if (optionalUser.isEmpty()) {
            log.error("User with id : {} not found!", reservationDTO.getIdUser(), new NotFoundException("Room not found"));
            throw new NotFoundException("User Not Found");
        }
        log.info("get room: {}", optionalUser.get());

        Reservation reservation = new Reservation();
        reservation.setUserEntity(optionalUser.get());
        reservation.setReservationDate(reservationDTO.getReservationDate());
        reservation.setRoomEntity(optionalRoom.get());
        reservation.setStartTime(reservationDTO.getStartTime());
        reservation.setEndTime(reservationDTO.getEndTime());
        reservation.setStatus(reservationDTO.getStatus());
        log.info("mapping Reservation: {}", reservation);

        reservation.setRoomEntity(optionalRoom.get());
        reservationRepository.save(reservation);
        log.info("Success add Reservation: {}", reservation);
    }

    @Override
    public PaginateDTO<List<ReservationListDTO>> getAllRerservation(Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findAll(pageable);
        List<ReservationListDTO> reservationListDTOArrayList = new ArrayList<>();
        for (Reservation reservation : reservations.getContent()) {
            Optional<User> optionalUser = userRepository.findById(reservation.getUserEntity().getId());
            if (optionalUser.isEmpty()) {
                log.error("User with id : {} not found!", reservation.getUserEntity().getId(), new NotFoundException("Room not found"));
                throw new NotFoundException("User Not Found");
            }
            log.info("get room: {}", optionalUser.get());
            reservation.setUserEntity(optionalUser.get());

            Optional<Room> optionalRoom = roomRepository.findById(Long.valueOf(reservation.getRoomEntity().getId()));
            if (optionalRoom.isEmpty()) {
                log.error("Room with id : {} not found!", reservation.getRoomEntity(), new NotFoundException("Room not found"));
                throw new NotFoundException("Room Not Found");
            }
            log.info("get room: {}", optionalRoom.get());
            reservation.setRoomEntity(optionalRoom.get());

            ReservationListDTO reservationListDTO = new ReservationListDTO();
            reservationListDTO.setId(reservation.getId());
            reservationListDTO.setReservationDate(reservation.getReservationDate());
            reservationListDTO.setStartTime(reservation.getStartTime());
            reservationListDTO.setEndTime(reservation.getEndTime());
            reservationListDTO.setNameUser(optionalUser.get().getFirstName() + (optionalUser.get().getLastName() != null ? " " + optionalUser.get().getLastName() : ""));
            reservationListDTO.setNameRoom(optionalRoom.get().getNameRoom());
            reservationListDTO.setStatus(reservation.getStatus());
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
    public void acceptedReservation(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isEmpty()) {
            log.error("Reservation with id : {} not found!", id, new NotFoundException("Reservation not found"));
            throw new NotFoundException("Reservation Not Found");
        }
        optionalReservation.get().setStatus("Accepted");
        reservationRepository.save(optionalReservation.get());
        log.info("Success Accepted Room: {}", optionalReservation.get());
    }

    @Override
    @Transactional
    public void rejectReservation(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isEmpty()) {
            log.error("Reservation with id : {} not found!", id, new NotFoundException("Reservation not found"));
            throw new NotFoundException("Reservation Not Found");
        }
        optionalReservation.get().setStatus("Rejected");
        reservationRepository.save(optionalReservation.get());
        log.info("Success Rejected Room: {}", optionalReservation.get());
    }

    @Override
    public PaginateDTO<List<ReservationListDTO>> getReservationListByID(Pageable pageable, Long id) {
        Page<Reservation> reservations = reservationRepository.findByidUser(id, pageable);
        List<ReservationListDTO> reservationListDTOArrayList = new ArrayList<>();
        for (Reservation reservation : reservations.getContent()) {
            Optional<User> optionalUser = userRepository.findById(reservation.getUserEntity().getId());
            if (optionalUser.isEmpty()) {
                log.error("User with id : {} not found!", reservation.getUserEntity().getId(), new NotFoundException("Room not found"));
                throw new NotFoundException("User Not Found");
            }
            log.info("get room: {}", optionalUser.get());
            reservation.setUserEntity(optionalUser.get());

            Optional<Room> optionalRoom = roomRepository.findById(Long.valueOf(reservation.getRoomEntity().getId()));
            if (optionalRoom.isEmpty()) {
                log.error("Room with id : {} not found!", reservation.getRoomEntity(), new NotFoundException("Room not found"));
                throw new NotFoundException("Room Not Found");
            }
            log.info("get room: {}", optionalRoom.get());
            reservation.setRoomEntity(optionalRoom.get());

            ReservationListDTO reservationListDTO = new ReservationListDTO();
            reservationListDTO.setId(reservation.getId());
            reservationListDTO.setReservationDate(reservation.getReservationDate());
            reservationListDTO.setStartTime(reservation.getStartTime());
            reservationListDTO.setEndTime(reservation.getEndTime());
            reservationListDTO.setNameUser(optionalUser.get().getFirstName() + (optionalUser.get().getLastName() != null ? " " + optionalUser.get().getLastName() : ""));
            reservationListDTO.setNameRoom(optionalRoom.get().getNameRoom());
            reservationListDTO.setStatus(reservation.getStatus());
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
    public ReservationDTO getReservationByID(Long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isEmpty()) {
            log.error("Reservation with id : {} not found!", id, new NotFoundException("Reservation not found"));
            throw new NotFoundException("Reservation Not Found");
        }
        ReservationDTO result = new ReservationDTO();
        result.setReservationDate(optionalReservation.get().getReservationDate());
        result.setStartTime(optionalReservation.get().getStartTime());
        result.setEndTime(optionalReservation.get().getEndTime());
        result.setIdUser(optionalReservation.get().getUserEntity().getId().intValue());
        result.setIdRoom(optionalReservation.get().getRoomEntity().getId());
        return result;
    }

    @Override
    public Integer countReservation() {
        return reservationRepository.countReservation();
    }

    @Override
    public List<Integer> countStatus() {
        return reservationRepository.countStatus();
    }
}
