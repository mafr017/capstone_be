package com.madityafr.room.roomservice.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TypeRoomDTO implements Serializable {
    private Integer id;
    private String name;
    private String description;
}
