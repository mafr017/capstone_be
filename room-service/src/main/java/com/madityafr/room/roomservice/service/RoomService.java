package com.madityafr.room.roomservice.service;

import com.madityafr.room.roomservice.dto.PaginateDTO;
import com.madityafr.room.roomservice.dto.RoomDTO;
import com.madityafr.room.roomservice.dto.RoomListDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomService {
    PaginateDTO<List<RoomListDTO>> getAllRoomPagination(Pageable pageable);

    void addRoom(RoomDTO roomDTO);

    void updateRoom(Long id, RoomDTO roomDTO);

    void deleteRoom(Long id);

    RoomListDTO getRoomByID(Long id);

    List<RoomListDTO> getAllRoomList();

    Integer countRoom();
}
