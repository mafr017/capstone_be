package com.madityafr.room.roomservice.service.implementation;

import com.madityafr.room.roomservice.entity.TypeRoom;
import com.madityafr.room.roomservice.entity.TypeRoomRedis;
import com.madityafr.room.roomservice.exception.NotFoundException;
import com.madityafr.room.roomservice.repository.TypeRoomRedisRepository;
import com.madityafr.room.roomservice.service.TypeRoomService;
import com.madityafr.room.roomservice.dto.TypeRoomDTO;
import com.madityafr.room.roomservice.repository.TypeRoomRepository;
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
    private final TypeRoomRedisRepository redisRepository;
    @Override
    @Transactional
    public void addTypeRoom(TypeRoomDTO typeRoomDTO) {
        TypeRoom room = modelMapper.map(typeRoomDTO, TypeRoom.class);
        typeRoomRepository.save(room);
        log.info("Success add TypeRoom: {}", room);
    }

    @Override
    public List<TypeRoomDTO> getAllTypeRoom() {
        List<TypeRoomRedis> dataRedis = new ArrayList<>();
        redisRepository.findAll().forEach(dataRedis::add);
        if (dataRedis.size() != 0) {
            List<TypeRoomDTO> typeRoomDTOList = new ArrayList<>();
            for (TypeRoomRedis typeRoom: dataRedis) {
                TypeRoomDTO typeRoomDTO = modelMapper.map(typeRoom, TypeRoomDTO.class);
                typeRoomDTOList.add(typeRoomDTO);
            }
            log.info("Success get List TypeRoom from redis");
            return typeRoomDTOList;
        }

        List<TypeRoom> typeRoomList = typeRoomRepository.findAll(Sort.by("id"));
        List<TypeRoomDTO> typeRoomDTOList = new ArrayList<>();
        for (TypeRoom typeRoom: typeRoomList) {
            TypeRoomDTO typeRoomDTO = modelMapper.map(typeRoom, TypeRoomDTO.class);
            typeRoomDTOList.add(typeRoomDTO);
            TypeRoomRedis typeRoomRedis = new TypeRoomRedis(typeRoom.getId(), typeRoom.getName(), typeRoom.getDescription());
            redisRepository.save(typeRoomRedis);
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

        TypeRoomRedis typeRoomRedis = new TypeRoomRedis(typeRoom.getId(), typeRoom.getName(), typeRoom.getDescription());
        redisRepository.save(typeRoomRedis);

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
        TypeRoomRedis dataRedis = new TypeRoomRedis(optionalTypeRoom.get().getId(), optionalTypeRoom.get().getName(), optionalTypeRoom.get().getDescription());

        typeRoomRepository.delete(optionalTypeRoom.get());
        redisRepository.delete(dataRedis);
        log.info("Success delete TypeRoom: {}", optionalTypeRoom.get());
    }

    @Override
    public TypeRoomDTO getTypeRoomByID(Long id) {
        Optional<TypeRoomRedis> dataRedis = redisRepository.findById(id.intValue());
        if (dataRedis.isPresent()) {
            log.info("Success get TypeRoom from redis");
            return modelMapper.map(dataRedis.get(), TypeRoomDTO.class);
        }

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
