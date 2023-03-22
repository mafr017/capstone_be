package com.madityafr.room.reservationservice.dto;

import lombok.*;

import java.time.LocalDate;

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
