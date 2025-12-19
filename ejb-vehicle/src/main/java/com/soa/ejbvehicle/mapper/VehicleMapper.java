package com.soa.ejbvehicle.mapper;

import com.soa.ejbvehicle.data.entities.Coordinates;
import com.soa.ejbvehicle.data.entities.Vehicle;
import com.soa.ejbvehicle.dto.CoordinatesResponseDTO;
import com.soa.ejbvehicle.dto.VehicleRequestDTO;
import com.soa.ejbvehicle.dto.VehicleResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class VehicleMapper {

    public Vehicle toEntity(VehicleRequestDTO dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setName(dto.getName());
        vehicle.setCoordinates(new Coordinates(dto.getCoordinates().getX(), dto.getCoordinates().getY()));
        vehicle.setEnginePower(dto.getEnginePower());
        vehicle.setType(dto.getType());
        vehicle.setNumberOfWheels(dto.getNumberOfWheels());
        vehicle.setFuelType(dto.getFuelType());
        return vehicle;
    }

    public VehicleResponseDTO toDTO(Vehicle entity) {
        CoordinatesResponseDTO coordinatesDTO = null;
        if (entity.getCoordinates() != null) {
            coordinatesDTO = new CoordinatesResponseDTO(
                    entity.getCoordinates().getX(),
                    entity.getCoordinates().getY()
            );
        }
        return new VehicleResponseDTO(
                entity.getId(),
                entity.getName(),
                coordinatesDTO,
                entity.getCreationDate(),
                entity.getEnginePower(),
                entity.getType(),
                entity.getNumberOfWheels(),
                entity.getFuelType()
        );
    }

    public Vehicle updateEntity(Vehicle entity, VehicleRequestDTO dto) {
        entity.setName(dto.getName());
        entity.setCoordinates(new Coordinates(dto.getCoordinates().getX(), dto.getCoordinates().getY()));
        entity.setEnginePower(dto.getEnginePower());
        entity.setType(dto.getType());
        entity.setNumberOfWheels(dto.getNumberOfWheels());
        entity.setFuelType(dto.getFuelType());
        return entity;
    }

    public List<VehicleResponseDTO> toDTOList(List<Vehicle> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

