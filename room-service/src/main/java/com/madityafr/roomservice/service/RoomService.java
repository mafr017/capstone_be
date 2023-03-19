package com.madityafr.roomservice.service;

import com.madityafr.roomservice.dto.PaginateDTO;
import com.madityafr.roomservice.dto.RoomDTO;
import com.madityafr.roomservice.dto.RoomListDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomService {
    PaginateDTO<List<RoomListDTO>> getAllRoomPagination(Pageable pageable);

    void addRoom(RoomDTO roomDTO);

    void updateRoom(Long id, RoomDTO roomDTO);

    void deleteRoom(Long id);

    RoomListDTO getRoomByID(Long id);
}
