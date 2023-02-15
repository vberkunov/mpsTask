package com.app.mpstask.repository;

import com.app.mpstask.persistance.entity.Airplane;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplaneRepository extends MongoRepository<Airplane, Long> {

}
