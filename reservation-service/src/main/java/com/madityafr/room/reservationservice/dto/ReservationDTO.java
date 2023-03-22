package com.madityafr.room.reservationservice.dto;

import lombok.*;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationDTO {
    private LocalDate reservationDate;
    private String startTime;
    private String endTime;
    private Integer idUser;
    private Integer idRoom;
    private String status = "Accepted";
}
