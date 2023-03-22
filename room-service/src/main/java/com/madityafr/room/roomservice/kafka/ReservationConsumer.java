package com.madityafr.room.roomservice.kafka;

import com.madityafr.room.reservationservice.entity.ReservationEvent;
import com.madityafr.room.roomservice.dto.TimeAvailableDTO;
import com.madityafr.room.roomservice.service.TimeAvailableService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationConsumer {
    private final TimeAvailableService timeAvailableService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationConsumer.class);

    List<String> listTimeReservation = new ArrayList<>(List.of(
            "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"
    ));

    @KafkaListener(topics = "${spring.kafka.topic.reservation}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ReservationEvent event, ConsumerRecord<String, ReservationEvent> payload){
        LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
        LOGGER.info("topic = {}", payload.topic());
        LOGGER.info("value = {}", event.getReservationDTO());

        //  Mapping Data
        String startTime = event.getReservationDTO().getStartTime().substring(0,2);
        String endTime = event.getReservationDTO().getEndTime().substring(0,2);
        List<String> filterTime = listTimeReservation.subList(listTimeReservation.indexOf(startTime), listTimeReservation.indexOf(endTime));
        TimeAvailableDTO timeAvailableDTO = new TimeAvailableDTO();
        for (String clockTime: filterTime) {
            if (clockTime.equals("07")) timeAvailableDTO.setJam_07(event.getReservationDTO().getIdUser().toString());
            if (clockTime.equals("08")) timeAvailableDTO.setJam_08(event.getReservationDTO().getIdUser().toString());
            if (clockTime.equals("09")) timeAvailableDTO.setJam_09(event.getReservationDTO().getIdUser().toString());
            if (clockTime.equals("10")) timeAvailableDTO.setJam_10(event.getReservationDTO().getIdUser().toString());
            if (clockTime.equals("11")) timeAvailableDTO.setJam_11(event.getReservationDTO().getIdUser().toString());
            if (clockTime.equals("12")) timeAvailableDTO.setJam_12(event.getReservationDTO().getIdUser().toString());
            if (clockTime.equals("13")) timeAvailableDTO.setJam_13(event.getReservationDTO().getIdUser().toString());
            if (clockTime.equals("14")) timeAvailableDTO.setJam_14(event.getReservationDTO().getIdUser().toString());
            if (clockTime.equals("15")) timeAvailableDTO.setJam_15(event.getReservationDTO().getIdUser().toString());
            if (clockTime.equals("16")) timeAvailableDTO.setJam_16(event.getReservationDTO().getIdUser().toString());
            if (clockTime.equals("17")) timeAvailableDTO.setJam_17(event.getReservationDTO().getIdUser().toString());
            if (clockTime.equals("18")) timeAvailableDTO.setJam_18(event.getReservationDTO().getIdUser().toString());
            if (clockTime.equals("19")) timeAvailableDTO.setJam_19(event.getReservationDTO().getIdUser().toString());
            if (clockTime.equals("20")) timeAvailableDTO.setJam_20(event.getReservationDTO().getIdUser().toString());
        }
        timeAvailableDTO.setIdRoom(event.getReservationDTO().getIdRoom());
        timeAvailableDTO.setReservationDate(event.getReservationDTO().getReservationDate());
        timeAvailableService.addTime(timeAvailableDTO);
    }
}