package com.app.mpstask.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirplaneCharacteristic {

    private double maxSpeed;
    private double maxAcceleration;
    private double maxHeightChangeSpeed;
    private double maxCourseChangeSpeed;

}
