package com.madityafr.room.roomservice.controller;

import com.madityafr.room.roomservice.dto.ResponseDTO;
import com.madityafr.room.roomservice.dto.TypeRoomDTO;
import com.madityafr.room.roomservice.service.TypeRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/type-room")
@RequiredArgsConstructor
@Slf4j
public class TypeRoomController {
    private final TypeRoomService typeRoomService;

//    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ResponseEntity<ResponseDTO<TypeRoomDTO>> addTypeRoom(@RequestBody TypeRoomDTO typeRoomDTO) {
        log.info("Hit Controller Add TypeRoom");
        typeRoomService.addTypeRoom(typeRoomDTO);
        return new ResponseEntity<>(ResponseDTO.<TypeRoomDTO>builder()
                .httpStatus(HttpStatus.CREATED)
                .message("Success to Add TypeRoom")
                .data(typeRoomDTO).build(), HttpStatus.CREATED);
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<ResponseDTO<List<TypeRoomDTO>>> getAllTypeRoom() {
        log.info("Hit Controller Get List TypeRoom");
        List<TypeRoomDTO> listTypeRoom = typeRoomService.getAllTypeRoom();
        return new ResponseEntity<>(ResponseDTO.<List<TypeRoomDTO>>builder()
                .httpStatus(HttpStatus.OK)
                .message("Success to Get List TypeRoom")
                .data(listTypeRoom).build(), HttpStatus.OK);
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<TypeRoomDTO>> updateTypeRoom(@RequestBody TypeRoomDTO typeRoomDTO, @PathVariable Long id) {
        log.info("Hit Controller Update TypeRoom with id: {}",id);
        typeRoomService.updateTypeRoom(id, typeRoomDTO);
        return new ResponseEntity<>(ResponseDTO.<TypeRoomDTO>builder()
                .httpStatus(HttpStatus.OK)
                .message("Success to Update TypeRoom with id " + id)
                .data(typeRoomDTO).build(), HttpStatus.OK);
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<TypeRoomDTO>> deleteTypeRoom(@PathVariable Long id) {
        log.info("Hit Controller Delete TypeRoom with id: {}",id);
        typeRoomService.deleteTypeRoom(id);
        return new ResponseEntity<>(ResponseDTO.<TypeRoomDTO>builder()
                .httpStatus(HttpStatus.ACCEPTED)
                .message("Success to Delete TypeRoom with id " + id)
                .build(), HttpStatus.ACCEPTED);
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<TypeRoomDTO>> getTypeRoomByID(@PathVariable Long id) {
        log.info("Hit Controller Get TypeRoom with id: {}",id);
        TypeRoomDTO result = typeRoomService.getTypeRoomByID(id);
        return new ResponseEntity<>(ResponseDTO.<TypeRoomDTO>builder()
                .httpStatus(HttpStatus.OK)
                .message("Success to Get TypeRoom with id: " + id)
                .data(result).build(), HttpStatus.OK);
    }
}
