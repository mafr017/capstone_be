package com.madityafr.room.roomservice.service.implementation;

import com.madityafr.room.roomservice.dto.TimeAvailableDTO;
import com.madityafr.room.roomservice.entity.Room;
import com.madityafr.room.roomservice.entity.TimeAvailable;
import com.madityafr.room.roomservice.exception.NotFoundException;
import com.madityafr.room.roomservice.repository.RoomRepository;
import com.madityafr.room.roomservice.repository.TimeAvailableRepository;
import com.madityafr.room.roomservice.service.TimeAvailableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TimeAvailableServiceImpl implements TimeAvailableService {
    private final ModelMapper modelMapper;
    private final TimeAvailableRepository timeAvailableRepository;
    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public void addTime(TimeAvailableDTO timeAvailableDTO, String fillWith) {
        log.info("Add Time: {}", timeAvailableDTO);
        TimeAvailable timeAvailable = modelMapper.map(timeAvailableDTO, TimeAvailable.class);
        TimeAvailableDTO check = getTimeByIDAndDate(timeAvailableDTO.getIdRoom().longValue(), timeAvailableDTO.getReservationDate());
        if (check != null) {
            timeAvailable.setId(check.getId());
            if ((check.getJam_07() != null && check.getJam_07() == "0" ) && !fillWith.equals("0")) timeAvailable.setJam_07(check.getJam_07());
            if ((check.getJam_08() != null && check.getJam_08() == "0" ) && !fillWith.equals("0")) timeAvailable.setJam_08(check.getJam_08());
            if ((check.getJam_09() != null && check.getJam_09() == "0" ) && !fillWith.equals("0")) timeAvailable.setJam_09(check.getJam_09());
            if ((check.getJam_10() != null && check.getJam_10() == "0" ) && !fillWith.equals("0")) timeAvailable.setJam_10(check.getJam_10());
            if ((check.getJam_11() != null && check.getJam_11() == "0" ) && !fillWith.equals("0")) timeAvailable.setJam_11(check.getJam_11());
            if ((check.getJam_12() != null && check.getJam_12() == "0" ) && !fillWith.equals("0")) timeAvailable.setJam_12(check.getJam_12());
            if ((check.getJam_13() != null && check.getJam_13() == "0" ) && !fillWith.equals("0")) timeAvailable.setJam_13(check.getJam_13());
            if ((check.getJam_14() != null && check.getJam_14() == "0" ) && !fillWith.equals("0")) timeAvailable.setJam_14(check.getJam_14());
            if ((check.getJam_15() != null && check.getJam_15() == "0" ) && !fillWith.equals("0")) timeAvailable.setJam_15(check.getJam_15());
            if ((check.getJam_16() != null && check.getJam_16() == "0" ) && !fillWith.equals("0")) timeAvailable.setJam_16(check.getJam_16());
            if ((check.getJam_17() != null && check.getJam_17() == "0" ) && !fillWith.equals("0")) timeAvailable.setJam_17(check.getJam_17());
            if ((check.getJam_18() != null && check.getJam_18() == "0" ) && !fillWith.equals("0")) timeAvailable.setJam_18(check.getJam_18());
            if ((check.getJam_19() != null && check.getJam_19() == "0" ) && !fillWith.equals("0")) timeAvailable.setJam_19(check.getJam_19());
            if ((check.getJam_20() != null && check.getJam_20() == "0" ) && !fillWith.equals("0")) timeAvailable.setJam_20(check.getJam_20());
        }
        Optional<Room> optionalRoom = roomRepository.findById(Long.valueOf(timeAvailableDTO.getIdRoom()));
        if (optionalRoom.isEmpty()) {
            log.error("Time with id : {} not found!", Long.valueOf(timeAvailableDTO.getIdRoom()), new NotFoundException("Time not found"));
            throw new NotFoundException("Time Not Found");
        }
        timeAvailable.setRoomEntity(optionalRoom.get());
        timeAvailableRepository.save(timeAvailable);
        log.info("Success add Time: {}", timeAvailable);
    }

    @Override
    public TimeAvailableDTO getTimeByIDAndDate(Long idRoom, LocalDate reservationDate) {
        Optional<TimeAvailable> timeAvailables = timeAvailableRepository.findByidRoomAndReservationDate(reservationDate, idRoom);
        if (timeAvailables.isEmpty()) return null;
        return modelMapper.map(timeAvailables.get(), TimeAvailableDTO.class);
    }
}
