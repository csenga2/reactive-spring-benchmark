package com.example.reactive.web;

import com.example.reactive.service.CarService;
import com.example.reactive.web.dto.CarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping
    public Mono<CarDto> findById() {
        return carService.process().map(CarDto::fromEntity);
    }
}
