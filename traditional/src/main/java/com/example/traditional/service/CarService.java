package com.example.traditional.service;

import com.example.traditional.persistence.model.CarEntity;
import com.example.traditional.persistence.repository.CarRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private KafkaTemplate<byte[],byte[]> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public CarEntity process() {
        final CarEntity carEntity = new CarEntity();
        carEntity.setPlateNumber(UUID.randomUUID().toString());
        carEntity.setType(UUID.randomUUID().toString());
        carEntity.setOwnerName(UUID.randomUUID().toString());

        carRepository.save(carEntity);

        byte[] carEntityBytes = null;
        try {
            carEntityBytes = objectMapper.writeValueAsString(carEntity).getBytes();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        kafkaTemplate.send("topic", carEntityBytes);

        return carEntity;
    }
}
