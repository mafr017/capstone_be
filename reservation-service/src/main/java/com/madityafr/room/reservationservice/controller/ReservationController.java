package com.madityafr.room.reservationservice.controller;

import com.madityafr.room.reservationservice.dto.*;
import com.madityafr.room.reservationservice.entity.Reservation;
import com.madityafr.room.reservationservice.kafka.ReservationProducer;
import com.madityafr.room.reservationservice.services.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
@Slf4j
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationProducer reservationProducer;

    public ReservationController(ReservationService reservationService, ReservationProducer orderProducer) {
        this.reservationService = reservationService;
        this.reservationProducer = orderProducer;
    }

    // KAFKA
    @PostMapping("/request")
    public String placeOrder(@RequestBody Reservation reservation){
        ReservationEvent reservationEvent = new ReservationEvent();
        reservationEvent.setStatus("PENDING");
        reservationEvent.setMessage("Request status is in pending state");
        reservationEvent.setReservation(reservation);

        try {
            reservationProducer.sendMessage(reservationEvent);
            return "Reservation Meeting Room successfully ...";
        } catch (Exception e) {
            return "Reservation Meeting Room failed ...";
        }
    }

    // CRUD
    @PostMapping
    public ResponseEntity<ResponseDTO<ReservationDTO>> addReservation(@RequestBody ReservationDTO reservationDTO) {
        log.info("Hit Controller Add Reservation");
        reservationService.addReservation(reservationDTO);
        return new ResponseEntity<>(ResponseDTO.<ReservationDTO>builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Success to Add Reservation")
                .data(reservationDTO).build(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<PaginateDTO<List<ReservationListDTO>>>> getAllRerservation(Pageable pageable) {
        log.info("Hit Controller Get all List Reservation with id_User");
        PaginateDTO<List<ReservationListDTO>> paginateDTO = reservationService.getAllRerservation(pageable);
        return new ResponseEntity<>(ResponseDTO.<PaginateDTO<List<ReservationListDTO>>>builder()
                .httpStatus(HttpStatus.OK)
                .message("Success to Get all List Reservation")
                .data(paginateDTO).build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ReservationDTO>> updateReservation(@RequestBody ReservationDTO reservationDTO, @PathVariable Long id) {
        log.info("Hit Controller Update Reservation with id: {}",id);
        reservationService.updateReservation(id, reservationDTO);
        return new ResponseEntity<>(ResponseDTO.<ReservationDTO>builder()
                .httpStatus(HttpStatus.OK)
                .message("Success to Update Reservation with id "+id)
                .data(reservationDTO).build(), HttpStatus.OK);
    }

    @GetMapping("/cancel/{id}")
    public ResponseEntity<ResponseDTO<ReservationDTO>> cancelReservation(@PathVariable Long id) {
        log.info("Hit Controller Cancel Reservation with id: {}",id);
        reservationService.cancelReservation(id);
        return new ResponseEntity<>(ResponseDTO.<ReservationDTO>builder()
                .httpStatus(HttpStatus.ACCEPTED)
                .message("Success to Cancel Reservation with id "+id)
                .build(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<PaginateDTO<List<ReservationListDTO>>>> getReservationByID(Pageable pageable, @PathVariable Long id) {
        log.info("Hit Controller Get all List Reservation with id_User");
        PaginateDTO<List<ReservationListDTO>> paginateDTO = reservationService.getReservationByID(pageable, id);
        return new ResponseEntity<>(ResponseDTO.<PaginateDTO<List<ReservationListDTO>>>builder()
                .httpStatus(HttpStatus.OK)
                .message("Success to Get all List Reservation")
                .data(paginateDTO).build(), HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ResponseDTO<ReservationListDTO>> getReservationByID(@PathVariable Long id) {
//        log.info("Hit Controller Get Reservation with id: {}",id);
//        ReservationListDTO result = reservationService.getReservationByID(id);
//        return new ResponseEntity<>(ResponseDTO.<ReservationListDTO>builder()
//                .httpStatus(HttpStatus.OK)
//                .message("Success to Get Reservation with id: "+id)
//                .data(result).build(), HttpStatus.OK);
//    }


}