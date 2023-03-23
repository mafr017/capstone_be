package com.madityafr.room.reservationservice.controller;

import com.madityafr.room.reservationservice.dto.*;
import com.madityafr.room.reservationservice.entity.ReservationEvent;
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
    public String sendRequest(@RequestBody ReservationDTO param){
        ReservationEvent event = new ReservationEvent();
        event.setReservationDTO(param);
        reservationProducer.sendMessage(event);
        log.info("Reservation Meeting Room successfully...");
        return "Reservation Meeting Room successfully ...";
    }

    // CRUD
    @PostMapping
    public ResponseEntity<ResponseDTO<ReservationDTO>> addReservation(@RequestBody ReservationDTO reservationDTO) {
        log.info("Hit Controller Add Reservation");
        reservationService.addReservation(reservationDTO);

        // KAFKA PRODUCER
        log.info("Kafka process...");
        String resultKafka = sendRequest(reservationDTO);
        log.info("Status Kafka addReservation: {}",resultKafka);

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
        log.info("Hit Controller Update Reservation with id: {}", id);
        reservationService.updateReservation(id, reservationDTO);
        return new ResponseEntity<>(ResponseDTO.<ReservationDTO>builder()
                .httpStatus(HttpStatus.OK)
                .message("Success to Update Reservation with id " + id)
                .data(reservationDTO).build(), HttpStatus.OK);
    }

    @GetMapping("/accept/{id}")
    public ResponseEntity<ResponseDTO<ReservationDTO>> acceptReservation(@PathVariable Long id) {
        log.info("Hit Controller Accept Reservation with id: {}", id);

        ReservationDTO reservationDTO = reservationService.getReservationByID(id);
        reservationDTO.setStatus("Accepted");
        // KAFKA PRODUCER
        log.info("Kafka process...");
        String resultKafka = sendRequest(reservationDTO);
        log.info("Status Kafka addReservation: {}",resultKafka);

        reservationService.acceptedReservation(id);
        return new ResponseEntity<>(ResponseDTO.<ReservationDTO>builder()
                .httpStatus(HttpStatus.ACCEPTED)
                .message("Success to Accept Reservation with id " + id)
                .build(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/reject/{id}")
    public ResponseEntity<ResponseDTO<ReservationDTO>> rejectReservation(@PathVariable Long id) {
        log.info("Hit Controller Reject Reservation with id: {}", id);

        ReservationDTO reservationDTO = reservationService.getReservationByID(id);
        reservationDTO.setStatus("Rejected");
        // KAFKA PRODUCER
        log.info("Kafka process...");
        String resultKafka = sendRequest(reservationDTO);
        log.info("Status Kafka addReservation: {}",resultKafka);

        reservationService.rejectReservation(id);
        return new ResponseEntity<>(ResponseDTO.<ReservationDTO>builder()
                .httpStatus(HttpStatus.ACCEPTED)
                .message("Success to Reject Reservation with id " + id)
                .build(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<PaginateDTO<List<ReservationListDTO>>>> getReservationByID(Pageable pageable, @PathVariable Long id) {
        log.info("Hit Controller Get all List Reservation with id_User");
        PaginateDTO<List<ReservationListDTO>> paginateDTO = reservationService.getReservationListByID(pageable, id);
        return new ResponseEntity<>(ResponseDTO.<PaginateDTO<List<ReservationListDTO>>>builder()
                .httpStatus(HttpStatus.OK)
                .message("Success to Get all List Reservation")
                .data(paginateDTO).build(), HttpStatus.OK);
    }

    @GetMapping("/count-reservation")
    public ResponseEntity<DashboardDataDTO> countReservation() {
        log.info("count reservation");
        DashboardDataDTO dataDTO = new DashboardDataDTO();
        dataDTO.setListStatus(reservationService.countStatus());
        dataDTO.setTotalReservation(reservationService.countReservation());
        return new ResponseEntity<>(dataDTO, HttpStatus.OK);
    }


}