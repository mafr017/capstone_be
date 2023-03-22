package com.madityafr.room.roomservice.service;

import com.madityafr.room.roomservice.dto.TimeAvailableDTO;

import java.time.LocalDate;

public interface TimeAvailableService {
    void addTime(TimeAvailableDTO timeAvailableDTO, String fillWith);

    TimeAvailableDTO getTimeByIDAndDate(Long id, LocalDate reservationDate);
}
