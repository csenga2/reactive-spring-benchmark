package com.example.traditional.persistence.repository;

import com.example.traditional.persistence.model.CarEntity;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<CarEntity,Long> {
}
