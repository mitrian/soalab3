package com.soa.ejbvehicle.dto;

import com.soa.ejbvehicle.data.enums.FuelType;
import com.soa.ejbvehicle.data.enums.VehicleType;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VehicleRequestDTO")
@XmlAccessorType(XmlAccessType.FIELD)
public class VehicleRequestDTO implements java.io.Serializable {
    @XmlElement
    @NotBlank(message = "Name cannot be null or empty")
    private String name;

    @XmlElement
    @NotNull(message = "Coordinates cannot be null")
    @Valid
    private CoordinatesRequestDTO coordinates;

    @XmlElement
    @Min(value = 1, message = "Engine power must be greater than 0")
    private Long enginePower;

    @XmlElement
    @NotNull(message = "Vehicle type cannot be null")
    private VehicleType type;

    @XmlElement
    @Min(value = 0, message = "Number of wheels cannot be negative")
    private Long numberOfWheels;

    @XmlElement
    private FuelType fuelType;

    public VehicleRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordinatesRequestDTO getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesRequestDTO coordinates) {
        this.coordinates = coordinates;
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

