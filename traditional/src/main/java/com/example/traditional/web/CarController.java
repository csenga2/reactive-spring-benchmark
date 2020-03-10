package com.example.traditional.web;

import com.example.traditional.service.CarService;
import com.example.traditional.web.dto.CarDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping
    public ResponseEntity<CarDto> process() {
        return ResponseEntity.ok(CarDto.fromEntity(carService.process()));
    }
}
