package com.madityafr.room.roomservice.kafka;

import com.madityafr.room.reservationservice.entity.ReservationEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class ReservationProducer {
    @Value("${spring.kafka.topic.room}")
    private String topicName;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationProducer.class);
    private NewTopic topic;
    private KafkaTemplate<String, ReservationEvent> kafkaTemplate;

    public ReservationProducer(NewTopic topic, KafkaTemplate<String, ReservationEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(ReservationEvent event){
        LOGGER.info(String.format("Reservation event => %s", topicName));
        Message<ReservationEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();
        kafkaTemplate.send(message);
    }
}