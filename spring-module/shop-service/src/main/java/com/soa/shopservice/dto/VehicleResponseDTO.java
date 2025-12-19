package com.soa.shopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponseDTO {
    private Long id;
    private String name;
    private CoordinatesDTO coordinates;
    private ZonedDateTime creationDate;
    private long enginePower;
    private String type;
    private Long numberOfWheels;
    private String fuelType;
}