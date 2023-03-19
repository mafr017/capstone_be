package com.madityafr.roomservice.service;

import com.madityafr.roomservice.dto.TypeRoomDTO;

import java.util.List;

public interface TypeRoomService {
    void addTypeRoom(TypeRoomDTO typeRoomDTO);

    List<TypeRoomDTO> getAllTypeRoom();

    void updateTypeRoom(Long id, TypeRoomDTO typeRoomDTO);

    void deleteTypeRoom(Long id);

    TypeRoomDTO getTypeRoomByID(Long id);
}
