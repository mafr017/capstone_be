package com.madityafr.roomservice.listener;

import com.madityafr.reservationservice.entity.ReservationEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ReservationConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationConsumer.class);

    @KafkaListener(topics = "${topic.name.consumer}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ReservationEvent event, ConsumerRecord<String, ReservationEvent> payload){
        LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
        LOGGER.info("topic = {}", payload.topic());
        LOGGER.info("partition = {}", payload.partition());
        LOGGER.info("key = {}", payload.key());
        LOGGER.info("offset = {}", payload.offset());
        LOGGER.info("leaderEpoch = {}", payload.leaderEpoch());
        LOGGER.info("headers = {}", payload.headers());
    }
}