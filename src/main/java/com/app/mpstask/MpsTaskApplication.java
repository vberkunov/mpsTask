package com.app.mpstask;

import com.app.mpstask.persistance.entity.AirplaneCharacteristic;
import com.app.mpstask.persistance.entity.Flight;
import com.app.mpstask.persistance.entity.TemporaryPoint;
import com.app.mpstask.persistance.entity.WayPoint;
import com.app.mpstask.service.PlaneCalculation;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class MpsTaskApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MpsTaskApplication.class);

    @Autowired
    PlaneCalculation routeCalculator;

    public static void main(String[] args) {
        SpringApplication.run(MpsTaskApplication.class, args);
    }

    @PostConstruct
    public void scheduleTasks() throws InterruptedException {
        for (int i = 1; i < 4; i++) {
            List<WayPoint> wayPoints = generateWayPoints(i);
            AirplaneCharacteristic characteristics = generateAirplaneCharacteristic(i);
            List<TemporaryPoint> route = routeCalculator.calculateRoute(characteristics, wayPoints);

            LOGGER.info("Start new flight");

            Flight flight = Flight
                    .builder()
                    .nodes(wayPoints)
                    .passedPoints(new ArrayList<>())
                    .build();
            for (TemporaryPoint point: route) {
                flight.getPassedPoints().add(point);

                LOGGER.info("Point passed" + point);

                Thread.sleep(1000);
            }

                LOGGER.info("The flight is finished.");
                Thread.sleep(5000);
        }
    }

    private List<WayPoint> generateWayPoints(int flightNum) {
        switch (flightNum){
            case 1:

                return List.of(WayPoint.builder().speed(7.0).latitude(12.1).longitude(17.2).height(10.0).build(),
                        WayPoint.builder().speed(9.0).latitude(12.1).longitude(17.2).height(13.0).build(),
                        WayPoint.builder().speed(11.0).latitude(17.1).longitude(21.5).height(16.0).build()
                );

            case 2:

                return List.of(WayPoint.builder().speed(7.0).latitude(12.1).longitude(17.2).height(5.0).build(),
                        WayPoint.builder().speed(8.0).latitude(12.1).longitude(17.2).height(10.0).build(),
                        WayPoint.builder().speed(11.0).latitude(17.1).longitude(21.5).height(16.0).build()
                );
            case 3:
                return List.of(WayPoint.builder().speed(3.0).latitude(12.1).longitude(17.2).height(10.0).build(),
                        WayPoint.builder().speed(9.0).latitude(12.1).longitude(17.2).height(13.0).build(),
                        WayPoint.builder().speed(12.5).latitude(17.1).longitude(21.5).height(16.0).build()
                );
            default:
                return List.of(WayPoint.builder().speed(2.0).latitude(12.1).longitude(17.2).height(10.0).build(),
                        WayPoint.builder().speed(3.0).latitude(12.1).longitude(17.2).height(13.0).build(),
                        WayPoint.builder().speed(5.0).latitude(17.1).longitude(21.5).height(16.0).build()
                );
        }
    }

    private AirplaneCharacteristic generateAirplaneCharacteristic(int flightNum) {
        switch (flightNum){
            case 1:
                AirplaneCharacteristic airplaneCharacteristics1 = AirplaneCharacteristic
                        .builder()
                        .maxSpeed(15.0)
                        .maxAcceleration(2.0)
                        .maxHeightChangeSpeed(3.0)
                        .maxCourseChangeSpeed(2.0)
                        .build();
                return airplaneCharacteristics1;
            case 2:
                AirplaneCharacteristic airplaneCharacteristics2 = AirplaneCharacteristic
                        .builder()
                        .maxSpeed(25.0)
                        .maxAcceleration(1.4)
                        .maxHeightChangeSpeed(1.0)
                        .maxCourseChangeSpeed(2.5)
                        .build();
                return airplaneCharacteristics2;
            case 3:
                AirplaneCharacteristic airplaneCharacteristics3 = AirplaneCharacteristic
                        .builder()
                        .maxSpeed(7.0)
                        .maxAcceleration(2.5)
                        .maxHeightChangeSpeed(2.0)
                        .maxCourseChangeSpeed(3.0)
                        .build();
                return airplaneCharacteristics3;
            default:
                return AirplaneCharacteristic
                        .builder()
                        .maxSpeed(7.0)
                        .maxAcceleration(2.5)
                        .maxHeightChangeSpeed(2.0)
                        .maxCourseChangeSpeed(3.0)
                        .build();
        }
    }
}

