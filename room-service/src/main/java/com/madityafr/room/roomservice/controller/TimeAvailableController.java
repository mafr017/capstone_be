package com.madityafr.room.roomservice.controller;

import com.madityafr.room.roomservice.dto.ResponseDTO;
import com.madityafr.room.roomservice.dto.TimeAvailableDTO;
import com.madityafr.room.roomservice.service.TimeAvailableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/time")
@RequiredArgsConstructor
@Slf4j
public class TimeAvailableController {
    private final TimeAvailableService timeAvailableService;

//    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ResponseEntity<ResponseDTO<TimeAvailableDTO>> addTime(@RequestBody TimeAvailableDTO timeAvailableDTO) {
        log.info("Hit Controller Add Time");
        timeAvailableService.addTime(timeAvailableDTO, "1");
        return new ResponseEntity<>(ResponseDTO.<TimeAvailableDTO>builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Success to Add Room")
                .data(timeAvailableDTO).build(), HttpStatus.CREATED);
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<ResponseDTO<TimeAvailableDTO>> getTimeByID(@RequestParam Long idRoom, @RequestParam LocalDate reservationDate) {
        log.info("Hit Controller Get Time with id: {}, Date: {}", idRoom, reservationDate);
        TimeAvailableDTO result = timeAvailableService.getTimeByIDAndDate(idRoom, reservationDate);
        return new ResponseEntity<>(ResponseDTO.<TimeAvailableDTO>builder()
                .httpStatus(HttpStatus.OK)
                .message("Success to Get Time with id: "+idRoom)
                .data(result).build(), HttpStatus.OK);
    }
}
