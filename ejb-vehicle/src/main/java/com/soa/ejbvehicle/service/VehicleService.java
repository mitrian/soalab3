package com.soa.ejbvehicle.service;

import com.soa.ejbvehicle.dto.*;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface VehicleService {
    VehicleResponseDTO createVehicle(VehicleRequestDTO requestDTO);
    VehicleResponseDTO getVehicleById(Long id);
    VehicleResponseDTO updateVehicle(Long id, VehicleRequestDTO requestDTO);
    void deleteVehicle(Long id);
    CountResponseDTO deleteByEnginePower(Long enginePower);
    Long countLessThanEnginePower(Long enginePower);
    List<VehicleResponseDTO> findByNamePrefix(String namePrefix);
    VehicleListResponseDTO getVehiclesWithFilter(VehicleFilterDTO filterDTO);
}

