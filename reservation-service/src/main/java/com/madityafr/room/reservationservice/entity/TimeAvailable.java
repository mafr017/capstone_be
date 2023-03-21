package com.madityafr.room.reservationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TimeAvailable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate reservationDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRoom")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room roomEntity;
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
