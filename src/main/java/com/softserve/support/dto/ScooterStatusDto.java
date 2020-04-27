package com.softserve.support.dto;

import com.softserve.support.model.Status;
import lombok.Data;
import org.springframework.data.geo.Point;

import java.util.UUID;

@Data
public class ScooterStatusDto {
    UUID id;
    Status status;
    Point gpsPoint;
    Integer battery;
}
