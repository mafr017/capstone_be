package com.madityafr.room.roomservice.controller;

import com.madityafr.room.roomservice.dto.PaginateDTO;
import com.madityafr.room.roomservice.dto.ResponseDTO;
import com.madityafr.room.roomservice.dto.RoomDTO;
import com.madityafr.room.roomservice.dto.RoomListDTO;
import com.madityafr.room.roomservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomController {
    private final RoomService roomService;

//    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ResponseEntity<ResponseDTO<RoomDTO>> addRoom(@RequestBody RoomDTO roomDTO) {
        log.info("Hit Controller Add Room");
        roomService.addRoom(roomDTO);
        return new ResponseEntity<>(ResponseDTO.<RoomDTO>builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Success to Add Room")
                .data(roomDTO).build(), HttpStatus.CREATED);
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/pagination")
    public ResponseEntity<ResponseDTO<PaginateDTO<List<RoomListDTO>>>> getAllRoom(Pageable pageable) {
        log.info("Hit Controller Get List Rooms with pagination");
        PaginateDTO<List<RoomListDTO>> paginateDTO = roomService.getAllRoomPagination(pageable);
        return new ResponseEntity<>(ResponseDTO.<PaginateDTO<List<RoomListDTO>>>builder()
                .httpStatus(HttpStatus.OK)
                .message("Success to Get List Rooms with pagination")
                .data(paginateDTO).build(), HttpStatus.OK);
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<ResponseDTO<List<RoomListDTO>>> getAllRoomList() {
        log.info("Hit Controller Get List Rooms");
        List<RoomListDTO> roomListDTO = roomService.getAllRoomList();
        return new ResponseEntity<>(ResponseDTO.<List<RoomListDTO>>builder()
                .httpStatus(HttpStatus.OK)
                .message("Success to Get List Rooms")
                .data(roomListDTO).build(), HttpStatus.OK);
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<RoomDTO>> updateRoom(@RequestBody RoomDTO roomDTO, @PathVariable Long id) {
        log.info("Hit Controller Update Room with id: {}",id);
        roomService.updateRoom(id, roomDTO);
        return new ResponseEntity<>(ResponseDTO.<RoomDTO>builder()
                .httpStatus(HttpStatus.OK)
                .message("Success to Update Room with id "+id)
                .data(roomDTO).build(), HttpStatus.OK);
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<RoomDTO>> deleteRoom(@PathVariable Long id) {
        log.info("Hit Controller Delete Room with id: {}",id);
        roomService.deleteRoom(id);
        return new ResponseEntity<>(ResponseDTO.<RoomDTO>builder()
                .httpStatus(HttpStatus.ACCEPTED)
                .message("Success to Delete Room with id "+id)
                .build(), HttpStatus.ACCEPTED);
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<RoomListDTO>> getRoomByID(@PathVariable Long id) {
        log.info("Hit Controller Get Room with id: {}",id);
        RoomListDTO result = roomService.getRoomByID(id);
        return new ResponseEntity<>(ResponseDTO.<RoomListDTO>builder()
                .httpStatus(HttpStatus.OK)
                .message("Success to Get Room with id: "+id)
                .data(result).build(), HttpStatus.OK);
    }
}
