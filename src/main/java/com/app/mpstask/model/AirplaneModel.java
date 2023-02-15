package com.app.mpstask.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AirplaneModel {
    private Double airplaneSpeed;
    private Double Acceleration;
    private Double heightChangeSpeed;
    private Double courseChangeSpeed;
    private Double startLongitude;
    private Double startLatitude;
    private Double startHeight;
    private Double startSpeed;
}
