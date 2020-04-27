package com.softserve.support.converter;

import com.softserve.support.dto.ScooterStatusDto;
import com.softserve.support.model.Support;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

@Component
public class SupportToScooterStatusDto implements Converter<Support, ScooterStatusDto> {

    @Override
    public ScooterStatusDto convert(Support support) {
        ScooterStatusDto dto = new ScooterStatusDto();
        dto.setId(support.getScooterId());
        dto.setStatus(support.getStatus());
        dto.setGpsPoint(new Point(support.getActualLatitude(), support.getActualLongitude()));
        dto.setBattery(support.getBattery());
        return dto;
    }
}
