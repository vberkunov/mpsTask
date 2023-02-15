package com.app.mpstask.service;

import com.app.mpstask.persistance.entity.AirplaneCharacteristic;
import com.app.mpstask.persistance.entity.TemporaryPoint;
import com.app.mpstask.persistance.entity.WayPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaneCalculation {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlaneCalculation.class);

    public List<TemporaryPoint> calculateRoute(final AirplaneCharacteristic characteristics, final List<WayPoint> wayPoints) {
        List<TemporaryPoint> result = new ArrayList<>();

        TemporaryPoint currentPoint = TemporaryPoint
                .builder()
                .longitude(2.1)
                .latitude(1.1)
                .height(3.0)
                .speed(2.1)
                .course(5.1)
                .build();
        result.add(currentPoint);

        for (int i = 0; i < wayPoints.size() - 1; i++) {

            WayPoint targetPoint = wayPoints.get(i + 1);

            LOGGER.info("New way point: " + targetPoint.toString());

            double heightDifference = calculateHeightDifference(currentPoint,targetPoint);
            double speedDifference = calculateSpeedDifference(currentPoint,targetPoint);

            while(checkIfNextTemporaryPointExist(currentPoint,targetPoint, heightDifference, speedDifference)){

                LOGGER.info(" In way to target point");

                TemporaryPoint nextTemporaryPoint = calculateTemporaryPoint(currentPoint, targetPoint, characteristics );

                LOGGER.info("New temporary point :" + nextTemporaryPoint.toString());
                LOGGER.info("Target point :" + targetPoint.toString());
                LOGGER.info("----------------------------------------------");

                result.add(nextTemporaryPoint);
                currentPoint = nextTemporaryPoint;
                heightDifference = calculateHeightDifference(currentPoint,targetPoint);
                LOGGER.info("Height diff :" + heightDifference);
                speedDifference = calculateSpeedDifference(currentPoint,targetPoint);
                LOGGER.info("Speed diff :" + speedDifference);
                LOGGER.info(".....................................");
            }

            LOGGER.info("Way point passed" + targetPoint.toString());

            result.add(TemporaryPoint
                    .builder()
                    .latitude(targetPoint.getLatitude())
                    .longitude(targetPoint.getLongitude())
                    .speed(targetPoint.getSpeed())
                    .course(result.get(result.size()-1).getCourse())
                    .build());
        }

        return result;
    }

    private boolean checkIfNextTemporaryPointExist(
            TemporaryPoint currentTemporaryPoint,
            WayPoint wayPoint,
            double heightDifference,
            double speedDifference) {

        return          wayPoint.getLatitude() > 0 ?
                        currentTemporaryPoint.getLatitude() < wayPoint.getLatitude() :
                        currentTemporaryPoint.getLatitude() > wayPoint.getLatitude()
                &&
                        wayPoint.getLongitude() > 0 ?
                        currentTemporaryPoint.getLongitude() < wayPoint.getLongitude() :
                        currentTemporaryPoint.getLongitude() > wayPoint.getLongitude()
                &&
                                heightDifference > 0.0
                &&
                                speedDifference > 0.0;
    }


    private double calculateHeightDifference(final TemporaryPoint currentPoint, final WayPoint targetPoint){
        return targetPoint.getHeight() - currentPoint.getHeight();

    }

    private double calculateSpeedDifference(final TemporaryPoint currentPoint, final WayPoint targetPoint){
        return targetPoint.getSpeed() - currentPoint.getSpeed();
    }

    private double calculateDistance(final TemporaryPoint currentPoint, final WayPoint targetPoint){
        double deltaLatitude = targetPoint.getLatitude() - currentPoint.getLatitude();
        double deltaLongitude = targetPoint.getLongitude() - currentPoint.getLongitude();
        return Math.sqrt(Math.pow(deltaLatitude, 2) + Math.pow(deltaLongitude, 2));
    }

    private double degreeToRadian(final double degree) {
        return degree * Math.PI / 180.0;
    }

    private double angleBetweenPoints(final TemporaryPoint currentPoint, final WayPoint targetPoint) {
        double currentLatitude = degreeToRadian(currentPoint.getLatitude());
        double currentLongitude = degreeToRadian(currentPoint.getLongitude());
        double targetLatitude = degreeToRadian(targetPoint.getLatitude());
        double targetLongitude = degreeToRadian(targetPoint.getLongitude());

        double deltaLongitude = targetLongitude - currentLongitude;
        double angle = Math.atan2(Math.sin(deltaLongitude) * Math.cos(targetLatitude),
                Math.cos(currentLatitude) * Math.cos(deltaLongitude));

        return angle * 180 / Math.PI;
    }

    private TemporaryPoint calculateTemporaryPoint(final TemporaryPoint temporaryPoint, final WayPoint wayPoint, final AirplaneCharacteristic airplaneCharacteristics) {
        // calculate the distance between TemporaryPoint and WayPoint
        double distance = calculateDistance(temporaryPoint,wayPoint);

        // calculate the new speed based on airplane characteristics
        double newSpeed = wayPoint.getSpeed()>temporaryPoint.getSpeed() ?
                Math.min(wayPoint.getSpeed(),
                        temporaryPoint.getSpeed() + airplaneCharacteristics.getMaxAcceleration()) :
                Math.min(wayPoint.getSpeed(),temporaryPoint.getSpeed() - airplaneCharacteristics.getMaxAcceleration());

        // calculate the new altitude based on airplane characteristics
        double newHeight = wayPoint.getHeight() > temporaryPoint.getHeight() ?
                Math.min(wayPoint.getHeight(),temporaryPoint.getHeight() + airplaneCharacteristics.getMaxHeightChangeSpeed()) :
                Math.min(wayPoint.getHeight(),temporaryPoint.getHeight() - airplaneCharacteristics.getMaxHeightChangeSpeed());

        // calculate the new course based on airplane characteristics
        double newCourse = angleBetweenPoints(temporaryPoint,wayPoint) < temporaryPoint.getCourse()?
                temporaryPoint.getCourse() - airplaneCharacteristics.getMaxCourseChangeSpeed():
                temporaryPoint.getCourse() + airplaneCharacteristics.getMaxCourseChangeSpeed();

        // calculate the new latitude and longitude based on speed and course
        double newLatitude = temporaryPoint.getLatitude() + newSpeed * Math.cos(newCourse) / distance;
        double newLongitude = temporaryPoint.getLongitude() + newSpeed * Math.sin(newCourse) / distance;

        return new TemporaryPoint(newLatitude, newLongitude, newHeight, newSpeed, newCourse);
    }

}