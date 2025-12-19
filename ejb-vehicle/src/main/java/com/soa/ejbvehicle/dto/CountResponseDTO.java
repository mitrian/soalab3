package com.soa.ejbvehicle.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "countResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class CountResponseDTO {
    @XmlElement
    private Long count;

    public CountResponseDTO() {
    }

    public CountResponseDTO(Long count) {
        this.count = count;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}

