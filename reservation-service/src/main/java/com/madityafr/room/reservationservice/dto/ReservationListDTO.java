package com.madityafr.room.reservationservice.dto;

import lombok.*;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationListDTO {
    private Integer id;
    private Date reservationDate;
    private String startTime;
    private String endTime;
    private Integer idUser;
    private Integer idRoom;
    private String status;
}
