package com.madityafr.room.roomservice.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TimeAvailableDTO {
    private Integer id;
    private LocalDate reservationDate;
    private Integer idRoom;
    private String jam_07;
    private String jam_08;
    private String jam_09;
    private String jam_10;
    private String jam_11;
    private String jam_12;
    private String jam_13;
    private String jam_14;
    private String jam_15;
    private String jam_16;
    private String jam_17;
    private String jam_18;
    private String jam_19;
    private String jam_20;
}
