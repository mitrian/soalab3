package com.soa.shopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleListResponseDTO {
    private List<VehicleResponseDTO> vehicles;
    private Long totalCount;
    private Integer totalPages;
    private Integer currentPage;
    private Integer pageSize;
}