package com.soa.ejbvehicle.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soa.ejbvehicle.data.enums.FuelType;
import com.soa.ejbvehicle.data.enums.VehicleType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.ZonedDateTime;

@XmlRootElement(name = "vehicle")
@XmlAccessorType(XmlAccessType.FIELD)
public class VehicleResponseDTO implements Serializable {
    @XmlElement(nillable = true)
    private Long id;

    @XmlElement
    private String name;

    @XmlElement
    private CoordinatesResponseDTO coordinates;

    @XmlElement
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime creationDate;

    @XmlElement
    private Long enginePower;

    @XmlElement
    private VehicleType type;

    @XmlElement
    private Long numberOfWheels;

    @XmlElement
    private FuelType fuelType;

    public VehicleResponseDTO() {
    }

    public VehicleResponseDTO(Long id, String name, CoordinatesResponseDTO coordinates, ZonedDateTime creationDate, Long enginePower, VehicleType type, Long numberOfWheels, FuelType fuelType) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.enginePower = enginePower;
        this.type = type;
        this.numberOfWheels = numberOfWheels;
        this.fuelType = fuelType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordinatesResponseDTO getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesResponseDTO coordinates) {
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Long getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(Long enginePower) {
        this.enginePower = enginePower;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public Long getNumberOfWheels() {
        return numberOfWheels;
    }

    public void setNumberOfWheels(Long numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }
}

