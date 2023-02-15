package com.app.mpstask.persistance.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WayPoint {

    private double longitude;
    private double latitude;
    private double height;
    private double speed;
}
