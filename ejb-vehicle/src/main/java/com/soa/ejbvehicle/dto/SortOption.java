package com.soa.ejbvehicle.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sortOption")
@XmlAccessorType(XmlAccessType.FIELD)
public class SortOption {
    @XmlElement
    private String field;

    @XmlElement
    private String direction = "asc";

    public SortOption() {
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}

