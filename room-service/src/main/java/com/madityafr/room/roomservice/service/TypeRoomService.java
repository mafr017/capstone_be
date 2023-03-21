package com.madityafr.room.roomservice.service;

import com.madityafr.room.roomservice.dto.TypeRoomDTO;

import java.util.List;

public interface TypeRoomService {
    void addTypeRoom(TypeRoomDTO typeRoomDTO);

    List<TypeRoomDTO> getAllTypeRoom();

    void updateTypeRoom(Long id, TypeRoomDTO typeRoomDTO);

    void deleteTypeRoom(Long id);

    TypeRoomDTO getTypeRoomByID(Long id);
}
