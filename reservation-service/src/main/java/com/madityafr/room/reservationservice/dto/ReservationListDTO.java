package com.madityafr.room.reservationservice.dto;

import com.madityafr.room.reservationservice.entity.Room;
import com.madityafr.room.reservationservice.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationListDTO {
    private Integer id;
    private LocalDate reservationDate;
    private String startTime;
    private String endTime;
    private String nameUser;
    private String nameRoom;
    private String status;
}
