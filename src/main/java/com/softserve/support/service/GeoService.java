package com.softserve.support.service;

import com.softserve.support.model.Support;
import com.softserve.support.repository.SupportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeoService {

    private final SupportRepository supportRepository;

    public double calculateDistance(UUID scooterId) {
        double distance = 0;
        List<Support> geoTrip = supportRepository.getByScooterId(scooterId);
        Support support = new Support();

        if(geoTrip.isEmpty()) return 0;
        Point previousPoint = new Point(geoTrip.get(0).getActualLongitude(), geoTrip.get(0).getActualLatitude());
        Point newPoint = new Point(support.getActualLongitude(), support.getActualLatitude());
        distance = betweenTwoPoints(previousPoint, newPoint);

        return distance;
    }

    private double betweenTwoPoints(Point previous, Point next) {
        double R = 6378.137; // Radius of Earth in KM
        double dLat = next.getY() * Math.PI / 180 - previous.getY() * Math.PI / 180;
        double dLon = next.getX() * Math.PI / 180 - previous.getX() * Math.PI / 180;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(previous.getY() * Math.PI / 180) * Math.cos(next.getY() * Math.PI / 180) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d * 1000; // meters
    }
}
