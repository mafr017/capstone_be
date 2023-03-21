package com.madityafr.room.reservationservice.dto;

import com.madityafr.room.reservationservice.entity.Reservation;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ReservationEvent {
    private String message = "Status is in pending state";
    private String status = "Pending";
    private Reservation reservation;
}
