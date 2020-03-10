package com.example.reactive.web.dto;

import com.example.reactive.persistence.model.CarEntity;

public class CarDto {

    private long id;

    private String plateNumber;

    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static CarDto fromEntity(CarEntity carEntity) {
        CarDto carDto = new CarDto();
        carDto.setId(carEntity.getId());
        carDto.setPlateNumber(carEntity.getPlateNumber());
        carDto.setType(carEntity.getType());
        return carDto;
    }

}
