package com.softserve.support.model;

import lombok.Data;
import javax.persistence.*;
import java.util.UUID;

@Data
public class Support {

    private UUID scooterId;

    @Enumerated(EnumType.STRING)
    private Status status;
    private Double actualLongitude;
    private Double actualLatitude;
    private int battery;
}
