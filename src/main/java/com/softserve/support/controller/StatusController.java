package com.softserve.support.controller;

import com.softserve.support.dto.ScooterStatusDto;
import com.softserve.support.service.SupportStatusService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.kafka.core.KafkaTemplate;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/support")
@AllArgsConstructor
public class StatusController {

    private SupportStatusService supportStatusService;
    private final KafkaTemplate<String, ScooterStatusDto> kafkaTemplate;
    private final VehicleClient vehicleClient;

    @Value("${service-token}")
    private String bearerToken;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/free")
    public ResponseEntity<String> freeScooter(@PathVariable UUID id) {
        vehicleClient.freeScooter(bearerToken, id);
        log.info("free scooter");
        return ResponseEntity.ok("Successful free scooter, id " + id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/inspect")
    public ResponseEntity<String> takeScooterOnInspection(@PathVariable UUID id){
        vehicleClient.takeScooterOnInspection(bearerToken, id);
        log.info("scooter on inspection");
        return ResponseEntity.ok("Successful take scooter on inspection, id " + id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> utilizeScooter(@PathVariable UUID id){
        vehicleClient.utilizeScooter(bearerToken, id);
        log.info("Utilizing scooter with uuid = {}", id );
        return ResponseEntity.ok("Successful utilize scooter, id " + id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/charge")
    public ResponseEntity<Integer> getCurrentChargeScooter(@PathVariable UUID id){
        Integer charge = supportStatusService.getCurrentChargeScooter(id);
        return ResponseEntity.ok(charge);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/chargelow")
    public ResponseEntity<String> lowChargeScooter(@PathVariable UUID id){
        supportStatusService.lowChargeScooter(id);
        return ResponseEntity.ok("Successful retrieve scooter, id " + id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/strange")
    public ResponseEntity<UUID> strangeBehavior(@RequestBody ScooterStatusDto uiDto) {

        if (supportStatusService.strangeBehavior(uiDto.getId())){
            kafkaTemplate.send("strange-behavior-data", uiDto);
            log.info("send the strange behavior");
        }
        return ResponseEntity.ok(uiDto.getId());
    }

}
