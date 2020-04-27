package com.softserve.support.controller;

import com.softserve.support.dto.ScooterStatusDto;
import com.softserve.support.exceptions.ScooterNotFoundException;
import com.softserve.support.service.SupportStatusService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaController {

    private static final String FREE_TOPIC = "free-scooter-data";
    private static final String LOW_BATTERY_TOPIC = "low-battery-data";
    private static final String STRANGE_BEHAVIOR_TOPIC = "strange-behavior-data";

    private KafkaTemplate<String, ScooterStatusDto> template;
    private SupportStatusService supportStatusService;

    private void sendLowBatteryDataToTopic(ScooterStatusDto dto){
        if (dto.getBattery() <= 50){
            template.send(LOW_BATTERY_TOPIC, dto);
            log.info("Send data to topic '{}': {} ", LOW_BATTERY_TOPIC, dto);
        }
    }

    private void sendStrangeBehaviorDataToTopic(ScooterStatusDto dto){
        if (supportStatusService.strangeBehavior(dto.getId())){
            template.send(STRANGE_BEHAVIOR_TOPIC, dto);
            log.info("Send data to topic '{}': {} ", STRANGE_BEHAVIOR_TOPIC, dto);
        }
    }

    @KafkaListener(topics = FREE_TOPIC)
    public void listen(ScooterStatusDto dto) {
        log.info("Received from '{}' : {}", FREE_TOPIC, dto);
        try {
            sendStrangeBehaviorDataToTopic(dto);
            sendLowBatteryDataToTopic(dto);

        } catch (ScooterNotFoundException e){
            log.error("Received from non-exist scooter '{}' : {}", FREE_TOPIC, dto);
        }
    }
}
