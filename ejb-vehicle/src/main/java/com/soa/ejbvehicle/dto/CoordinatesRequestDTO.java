package com.soa.ejbvehicle.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
public class CoordinatesRequestDTO implements java.io.Serializable {
    @XmlElement
    @NotNull(message = "X coordinate cannot be null")
    @DecimalMin(value = "-979", inclusive = false, message = "X coordinate must be greater than -979")
    private Double x;

    @XmlElement
    @NotNull(message = "Y coordinate cannot be null")
    private Double y;

    public CoordinatesRequestDTO() {
    }

    public CoordinatesRequestDTO(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}

