package com.soa.ejbvehicle.dto;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "VehicleFilterDTO")
@XmlAccessorType(XmlAccessType.FIELD)
public class VehicleFilterDTO {
    @XmlElementWrapper(name = "filters")
    @XmlElement(name = "filterCondition")
    @Valid
    private List<FilterCondition> filters;

    @XmlElementWrapper(name = "sort")
    @XmlElement(name = "sortOption")
    @Valid
    private List<SortOption> sort;

    @XmlElement
    @Valid
    private PaginationOptions pagination;

    public VehicleFilterDTO() {
    }

    public VehicleFilterDTO(List<FilterCondition> filters, List<SortOption> sort, PaginationOptions pagination) {
        this.filters = filters;
        this.sort = sort;
        this.pagination = pagination;
    }

    public List<FilterCondition> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterCondition> filters) {
        this.filters = filters;
    }

    public List<SortOption> getSort() {
        return sort;
    }

    public void setSort(List<SortOption> sort) {
        this.sort = sort;
    }

    public PaginationOptions getPagination() {
        return pagination;
    }

    public void setPagination(PaginationOptions pagination) {
        this.pagination = pagination;
    }
}

