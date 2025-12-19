package com.soa.ejbvehicle.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "vehicles")
@XmlAccessorType(XmlAccessType.FIELD)
public class VehicleListWrapper {
    @XmlElementWrapper(name = "vehicles")
    @XmlElement(name = "vehicle")
    private List<VehicleResponseDTO> vehicles;

    public VehicleListWrapper() {
    }

    public VehicleListWrapper(List<VehicleResponseDTO> vehicles) {
        this.vehicles = vehicles;
    }

    public List<VehicleResponseDTO> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleResponseDTO> vehicles) {
        this.vehicles = vehicles;
    }
}

