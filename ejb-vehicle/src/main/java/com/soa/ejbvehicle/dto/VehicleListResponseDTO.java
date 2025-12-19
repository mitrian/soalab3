package com.soa.ejbvehicle.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "vehicleListResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class VehicleListResponseDTO {
    @XmlElementWrapper(name = "vehicles")
    @XmlElement(name = "vehicle")
    private List<VehicleResponseDTO> vehicles;

    @XmlElement
    private Long totalCount;

    @XmlElement
    private Integer totalPages;

    @XmlElement
    private Integer currentPage;

    @XmlElement
    private Integer pageSize;

    public VehicleListResponseDTO() {
    }

    public VehicleListResponseDTO(List<VehicleResponseDTO> vehicles, Long totalCount, Integer totalPages, Integer currentPage, Integer pageSize) {
        this.vehicles = vehicles;
        this.totalCount = totalCount;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public List<VehicleResponseDTO> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleResponseDTO> vehicles) {
        this.vehicles = vehicles;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

