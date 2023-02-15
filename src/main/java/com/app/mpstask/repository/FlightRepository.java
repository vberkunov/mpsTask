package com.app.mpstask.repository;


import com.app.mpstask.persistance.entity.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends MongoRepository<Flight, Long> {
}
