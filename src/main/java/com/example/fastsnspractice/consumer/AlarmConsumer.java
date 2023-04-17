package com.example.fastsnspractice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.example.fastsnspractice.model.AlarmEvent;
import com.example.fastsnspractice.service.AlarmService;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmConsumer {

    private final AlarmService alarmService;

    @KafkaListener(topics = "${spring.kafka.topic.notification}")
    public void consumeNotification(AlarmEvent event, Acknowledgment ack) {
        log.info("Consume the event {}", event);
        alarmService.send(event.getType(), event.getArgs(), event.getReceiverUserId());
        ack.acknowledge();
    }
}
