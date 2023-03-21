package com.madityafr.room.roomservice.exception;

import com.madityafr.room.roomservice.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ResponseDTO<Object>> globalException(NotFoundException e) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("Not Found")
                .data(e.msg)
                .build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseDTO<Object>> globalException(Exception e) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("Bad Request")
                .data(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

}
