package com.madityafr.room.reservationservice.entity;

import com.madityafr.room.reservationservice.dto.ReservationDTO;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEvent {
    private String message = "Status is in pending state";
    private String status = "Pending";
    private ReservationDTO reservationDTO;
}
