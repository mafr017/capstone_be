package com.madityafr.roomservice.service.implementation;

import com.madityafr.roomservice.dto.TypeRoomDTO;
import com.madityafr.roomservice.entity.TypeRoom;
import com.madityafr.roomservice.exception.NotFoundException;
import com.madityafr.roomservice.repository.TypeRoomRepository;
import com.madityafr.roomservice.service.TypeRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TypeRoomServiceImpl implements TypeRoomService {
    private final ModelMapper modelMapper;
    private final TypeRoomRepository typeRoomRepository;
    @Override
    @Transactional
    public void addTypeRoom(TypeRoomDTO typeRoomDTO) {
        TypeRoom room = modelMapper.map(typeRoomDTO, TypeRoom.class);
        typeRoomRepository.save(room);
        log.info("Success add TypeRoom: {}", room);
    }

    @Override
    public List<TypeRoomDTO> getAllTypeRoom() {
        List<TypeRoom> typeRoomList = typeRoomRepository.findAll(Sort.by("id"));
        List<TypeRoomDTO> typeRoomDTOList = new ArrayList<>();
        for (TypeRoom typeRoom: typeRoomList) {
            TypeRoomDTO typeRoomDTO = modelMapper.map(typeRoom, TypeRoomDTO.class);
            typeRoomDTOList.add(typeRoomDTO);
        }
        log.info("Success get List TypeRoom");
        return typeRoomDTOList;
    }

    @Override
    @Transactional
    public void updateTypeRoom(Long id, TypeRoomDTO typeRoomDTO) {
        Optional<TypeRoom> optionalTypeRoom = typeRoomRepository.findById(id);
        if (optionalTypeRoom.isEmpty()) {
            log.error("TypeRoom with id : {} not found!", id, new NotFoundException("TypeRoom not found"));
            throw new NotFoundException("TypeRoom Not Found");
        }
        TypeRoom typeRoom = optionalTypeRoom.get();
        if (typeRoomDTO.getName() != null && !typeRoomDTO.getName().isBlank()) typeRoom.setName(typeRoomDTO.getName());
        if (typeRoomDTO.getDescription() != null && !typeRoomDTO.getDescription().isBlank()) typeRoom.setDescription(typeRoomDTO.getDescription());
        typeRoomRepository.save(typeRoom);
        log.info("Success update TypeRoom: {}", typeRoom);
    }

    @Override
    @Transactional
    public void deleteTypeRoom(Long id) {
        Optional<TypeRoom> optionalTypeRoom = typeRoomRepository.findById(id);
        if (optionalTypeRoom.isEmpty()) {
            log.error("TypeRoom with id : {} not found!", id, new NotFoundException("TypeRoom not found"));
            throw new NotFoundException("TypeRoom Not Found");
        }

        typeRoomRepository.delete(optionalTypeRoom.get());
        log.info("Success delete TypeRoom: {}", optionalTypeRoom.get());
    }

    @Override
    public TypeRoomDTO getTypeRoomByID(Long id) {
        Optional<TypeRoom> optionalTypeRoom = typeRoomRepository.findById(id);
        if (optionalTypeRoom.isEmpty()) {
            log.error("TypeRoom with id : {} not found!", id, new NotFoundException("TypeRoom not found"));
            throw new NotFoundException("TypeRoom Not Found");
        }
        TypeRoomDTO typeRoomDTO = modelMapper.map(optionalTypeRoom, TypeRoomDTO.class);
        log.info("Success get TypeRoom: {}", typeRoomDTO);
        return typeRoomDTO;
    }
}
