package com.madityafr.room.roomservice.service.implementation;

import com.madityafr.room.roomservice.entity.TypeRoom;
import com.madityafr.room.roomservice.exception.NotFoundException;
import com.madityafr.room.roomservice.service.RoomService;
import com.madityafr.room.roomservice.dto.PaginateDTO;
import com.madityafr.room.roomservice.dto.RoomDTO;
import com.madityafr.room.roomservice.dto.RoomListDTO;
import com.madityafr.room.roomservice.entity.Room;
import com.madityafr.room.roomservice.repository.RoomRepository;
import com.madityafr.room.roomservice.repository.TypeRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final ModelMapper modelMapper;
    private final RoomRepository roomRepository;
    private final TypeRoomRepository typeRoomRepository;

    @Override
    @Transactional
    public void addRoom(RoomDTO roomDTO) {
        Room room = modelMapper.map(roomDTO, Room.class);

        Optional<TypeRoom> optionalTypeRoom = typeRoomRepository.findById(Long.valueOf(roomDTO.getIdType()));
        if (optionalTypeRoom.isEmpty()) {
            log.error("TypeRoom with id : {} not found!", Long.valueOf(roomDTO.getIdType()), new NotFoundException("TypeRoom not found"));
            throw new NotFoundException("TypeRoom Not Found");
        }
        room.setTypeRoomEntity(optionalTypeRoom.get());

        LocalDate date = LocalDate.parse(roomDTO.getAvailableYear() + "-" + roomDTO.getAvailableMonth() + "-01");
        room.setAvailableFrom(date);
        room.setAvailableTo(date.plusMonths(1).minusDays(1));
        roomRepository.save(room);
        log.info("Success add Room: {}", room);
    }

    @Override
    public PaginateDTO<List<RoomListDTO>> getAllRoomPagination(Pageable pageable) {
        Page<Room> rooms = roomRepository.findAll(pageable);
        List<RoomListDTO> roomDTOArrayList = new ArrayList<>();
        for (Room room : rooms.getContent()) {
            RoomListDTO roomDTO = modelMapper.map(room, RoomListDTO.class);
            roomDTOArrayList.add(roomDTO);
        }
        log.info("Success get List Room");
        return PaginateDTO.<List<RoomListDTO>>builder().data(roomDTOArrayList).totalOfItems(rooms.getTotalElements()).totalOfPages(rooms.getTotalPages()).currentPage(rooms.getNumber()).build();
    }

    @Override
    @Transactional
    public void updateRoom(Long id, RoomDTO roomDTO) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isEmpty()) {
            log.error("Room with id : {} not found!", id, new NotFoundException("Room not found"));
            throw new NotFoundException("Room Not Found");
        }

        Room room = optionalRoom.get();
        if (roomDTO.getNameRoom() != null && !roomDTO.getNameRoom().isBlank()) room.setNameRoom(roomDTO.getNameRoom());
        if (roomDTO.getCapacity() != null && roomDTO.getCapacity() > 0) room.setCapacity(roomDTO.getCapacity());
        if (roomDTO.getIdType() != null && roomDTO.getIdType() > 0) {
            Optional<TypeRoom> optionalTypeRoom = typeRoomRepository.findById(roomDTO.getIdType().longValue());
            if (optionalTypeRoom.isEmpty()) {
                log.error("TypeRoom with id : {} not found!", id, new NotFoundException("TypeRoom not found"));
                throw new NotFoundException("TypeRoom Not Found");
            }
            room.setTypeRoomEntity(optionalTypeRoom.get());
        }
        if ((roomDTO.getAvailableYear() != null && !roomDTO.getAvailableYear().isBlank()) &&
                (roomDTO.getAvailableMonth() != null && !roomDTO.getAvailableMonth().isBlank())) {
            LocalDate date = LocalDate.parse(roomDTO.getAvailableYear() + "-" + roomDTO.getAvailableMonth() + "-01");
            room.setAvailableFrom(date);
            room.setAvailableTo(date.plusMonths(1).minusDays(1));
        }
        roomRepository.save(room);
        log.info("Success update Room: {}", room);
    }

    @Override
    @Transactional
    public void deleteRoom(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isEmpty()) {
            log.error("Room with id : {} not found!", id, new NotFoundException("Room not found"));
            throw new NotFoundException("Room Not Found");
        }

        roomRepository.delete(optionalRoom.get());
        log.info("Success delete Room: {}", optionalRoom.get());
    }

    @Override
    public RoomListDTO getRoomByID(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isEmpty()) {
            log.error("Room with id : {} not found!", id, new NotFoundException("Room not found"));
            throw new NotFoundException("Room Not Found");
        }
        RoomListDTO roomListDTO = modelMapper.map(optionalRoom.get(), RoomListDTO.class);
        log.info("Success get Room: {}", roomListDTO);
        return roomListDTO;
    }

    @Override
    public List<RoomListDTO> getAllRoomList() {
        List<Room> rooms = roomRepository.findAll();
        List<RoomListDTO> roomDTOArrayList = new ArrayList<>();
        for (Room room : rooms) {
            RoomListDTO roomDTO = modelMapper.map(room, RoomListDTO.class);
            roomDTOArrayList.add(roomDTO);
        }
        log.info("Success get List Room");
        return roomDTOArrayList;
    }
}
