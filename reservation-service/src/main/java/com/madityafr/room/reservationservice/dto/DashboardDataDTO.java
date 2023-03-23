package com.madityafr.room.reservationservice.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DashboardDataDTO {
    private List<Integer> listStatus;
    private Integer totalReservation;
}
