package com.madityafr.roomservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nameRoom;
    private Integer capacity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idType")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TypeRoom typeRoomEntity;
    private LocalDate availableFrom;
    private LocalDate availableTo;
    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updatedAt")
    private Date updatedAt;
    private Integer byAdmin;
}
