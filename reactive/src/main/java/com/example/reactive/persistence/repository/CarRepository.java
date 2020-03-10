package com.example.reactive.persistence.repository;

import com.example.reactive.persistence.model.CarEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CarRepository extends ReactiveCrudRepository<CarEntity,Long> {
}
