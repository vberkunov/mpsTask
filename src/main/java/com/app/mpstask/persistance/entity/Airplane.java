package com.app.mpstask.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Airplane {

    @Id
    private long id;
    private AirplaneCharacteristic characteristic;
    private TemporaryPoint position;
    private List<Flight> flights;
}
