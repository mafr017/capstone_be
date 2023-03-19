package com.madityafr.roomservice.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomDTO implements Serializable {
    private Integer id;
    private String nameRoom;
    private Integer capacity;
    private Integer idType;
    private String availableYear; // yyyy (ex:2023)
    private String availableMonth; // MM (ex:03)
//    private LocalDate availableFrom;
//    private LocalDate availableTo;
    private Integer byAdmin;
}
