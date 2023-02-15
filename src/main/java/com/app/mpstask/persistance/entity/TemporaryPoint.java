package com.app.mpstask.persistance.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TemporaryPoint {

    private double longitude;
    private double latitude;
    private double height;
    private double speed;
    private double course;
}
