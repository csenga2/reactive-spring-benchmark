package com.example.reactive.service;

import com.example.reactive.persistence.model.CarEntity;
import com.example.reactive.persistence.repository.CarRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ReactiveKafkaProducerTemplate reactiveKafkaProducerTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${waitForSavedEntity}")
    private Boolean waitForSavedEntity;

    public Mono<CarEntity> process() {
        CarEntity carEntity = new CarEntity();
        carEntity.setPlateNumber(UUID.randomUUID().toString());
        carEntity.setType(UUID.randomUUID().toString());
        carEntity.setOwnerName(UUID.randomUUID().toString());

        if (waitForSavedEntity) {
            return carRepository.save(carEntity)
                           .map(carEntity1 ->
                           {
                               byte[] carEntityBytes = null;
                               try {
                                   carEntityBytes = objectMapper.writeValueAsString(carEntity).getBytes();
                               } catch (JsonProcessingException e) {
                                   throw new RuntimeException(e);
                               }
                               reactiveKafkaProducerTemplate.send("topic", carEntityBytes);
                               return carEntity;
                           });

        } else {
            carRepository.save(carEntity)
                    .subscribe(carEntity1 ->
                    {

                        byte[] carEntityBytes = null;
                        try {
                            carEntityBytes = objectMapper.writeValueAsString(carEntity).getBytes();
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        reactiveKafkaProducerTemplate.send("topic", carEntityBytes);
                    });
            return Mono.just(carEntity);
        }
    }
}
