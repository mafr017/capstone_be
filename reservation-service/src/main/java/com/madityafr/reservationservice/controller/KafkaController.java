package com.madityafr.reservationservice.controller;

import com.madityafr.reservationservice.broker.producer.TopicProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {
    private final TopicProducer topicProducer;
    @GetMapping(value = "/send")
    public void send(){
//        topicProducer.send("Mensagem de teste enviada ao tópico");
        IntStream.range(0, 3)
                .forEach(i -> this.topicProducer.send("Mensagem de teste enviada ao tópico ke-"+i));
    }
}
