package com.kafkaexperimentclient.app.kafka;

import com.kafkaexperiment.app.dto.OrderEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(OrderEvent event, ConsumerRecord<String, OrderEvent> payload){
        LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
        LOGGER.info("topic = {}", payload.topic());
        LOGGER.info("partition = {}", payload.partition());
        LOGGER.info("key = {}", payload.key());
        LOGGER.info("offset = {}", payload.offset());
        LOGGER.info("leaderEpoch = {}", payload.leaderEpoch());
        LOGGER.info("headers = {}", payload.headers());
        // save the order event into the database
    }
}