package com.madityafr.roomservice.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomListDTO implements Serializable {
    private Integer id;
    private String nameRoom;
    private Integer capacity;
    private TypeRoomDTO typeRoom;
    private LocalDate availableFrom;
    private LocalDate availableTo;
    private Integer byAdmin;
}
