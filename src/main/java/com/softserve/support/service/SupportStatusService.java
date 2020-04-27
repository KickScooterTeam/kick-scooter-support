package com.softserve.support.service;

import com.softserve.support.exceptions.ScooterNotFoundException;
import com.softserve.support.model.Status;
import com.softserve.support.model.Support;
import com.softserve.support.repository.SupportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class SupportStatusService {

    private SupportRepository supportRepo;
    private GeoService geoService;

    public Boolean lowChargeScooter(UUID id) {
        Support support = supportRepo.findById(id).orElseThrow(ScooterNotFoundException::new);
        return support.getBattery() < 50;
    }

    public Integer getCurrentChargeScooter(UUID id) {
        Support support = supportRepo.findById(id).orElseThrow(ScooterNotFoundException::new);
        return support.getBattery();
    }

    public Boolean strangeBehavior(UUID id) {
        Support support = supportRepo.findById(id).orElseThrow(ScooterNotFoundException::new);
        return geoService.calculateDistance(id) > 5.0 && support.getStatus() == Status.FREE;
    }

}
