package com.madityafr.room.roomservice.kafka;

import com.madityafr.room.reservationservice.dto.ReservationDTO;
import com.madityafr.room.reservationservice.entity.ReservationEvent;
import com.madityafr.room.roomservice.dto.TimeAvailableDTO;
import com.madityafr.room.roomservice.service.TimeAvailableService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationConsumer {
    @Autowired
    private TimeAvailableService timeAvailableService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationConsumer.class);

    List<String> listTimeReservation = new ArrayList<>(List.of(
            "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"
    ));

    @KafkaListener(topics = "${spring.kafka.topic.reservation}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ReservationEvent event, ConsumerRecord<String, ReservationEvent> payload){
        LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
        LOGGER.info("topic = {}", payload.topic());
        LOGGER.info("value = {}", event.getReservationDTO());
        ReservationDTO dataFromKafka = event.getReservationDTO();

        //  Mapping Data
        String startTime = dataFromKafka.getStartTime().substring(0,2);
        String endTime = dataFromKafka.getEndTime().substring(0,2);
        List<String> filterTime = listTimeReservation.subList(listTimeReservation.indexOf(startTime), listTimeReservation.indexOf(endTime));
        TimeAvailableDTO timeAvailableDTO = new TimeAvailableDTO();

        LOGGER.info("STATUS KAFKA DATA: {}",dataFromKafka.getStatus());
        LOGGER.info("STATUS KAFKA DATA: {}",!dataFromKafka.getStatus().equals("Accepted"));
        String fillWith = dataFromKafka.getIdUser().toString();
        if (!dataFromKafka.getStatus().equals("Accepted")) {
            fillWith = "0";
        }

        for (String clockTime: filterTime) {
            if (clockTime.equals("07")) timeAvailableDTO.setJam_07(fillWith);
            if (clockTime.equals("08")) timeAvailableDTO.setJam_08(fillWith);
            if (clockTime.equals("09")) timeAvailableDTO.setJam_09(fillWith);
            if (clockTime.equals("10")) timeAvailableDTO.setJam_10(fillWith);
            if (clockTime.equals("11")) timeAvailableDTO.setJam_11(fillWith);
            if (clockTime.equals("12")) timeAvailableDTO.setJam_12(fillWith);
            if (clockTime.equals("13")) timeAvailableDTO.setJam_13(fillWith);
            if (clockTime.equals("14")) timeAvailableDTO.setJam_14(fillWith);
            if (clockTime.equals("15")) timeAvailableDTO.setJam_15(fillWith);
            if (clockTime.equals("16")) timeAvailableDTO.setJam_16(fillWith);
            if (clockTime.equals("17")) timeAvailableDTO.setJam_17(fillWith);
            if (clockTime.equals("18")) timeAvailableDTO.setJam_18(fillWith);
            if (clockTime.equals("19")) timeAvailableDTO.setJam_19(fillWith);
            if (clockTime.equals("20")) timeAvailableDTO.setJam_20(fillWith);
        }
        timeAvailableDTO.setIdRoom(dataFromKafka.getIdRoom());
        timeAvailableDTO.setReservationDate(dataFromKafka.getReservationDate());
        try {
            System.out.println(timeAvailableDTO);
            timeAvailableService.addTime(timeAvailableDTO, fillWith);
            LOGGER.info("Kafka Success...");
        } catch (Exception e) {
            LOGGER.error("Reservation failed : {}", e.getMessage());
        }
    }
}