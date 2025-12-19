package com.soa.ejbvehicle.service.impl;

import com.soa.ejbvehicle.data.entities.Vehicle;
import com.soa.ejbvehicle.dto.*;
import com.soa.ejbvehicle.exception.VehicleNotFoundException;
import com.soa.ejbvehicle.mapper.VehicleMapper;
import com.soa.ejbvehicle.repository.VehicleRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.time.ZonedDateTime;
import java.util.List;

@Stateless
public class VehicleServiceImpl implements com.soa.ejbvehicle.service.VehicleService {

    @EJB
    private VehicleRepository vehicleRepository;

    private final VehicleMapper vehicleMapper = new VehicleMapper();

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VehicleResponseDTO createVehicle(VehicleRequestDTO requestDTO) {
        try {
            System.out.println("=== VehicleServiceImpl.createVehicle ===");
            System.out.println("RequestDTO: " + requestDTO);
            System.out.println("RequestDTO name: " + (requestDTO != null ? requestDTO.getName() : "null"));
            
            Vehicle vehicle = vehicleMapper.toEntity(requestDTO);
            System.out.println("Vehicle created: " + vehicle);
            
            vehicle.setCreationDate(ZonedDateTime.now());
            System.out.println("Creation date set");
            
            Vehicle savedVehicle = vehicleRepository.save(vehicle);
            System.out.println("Vehicle saved with ID: " + savedVehicle.getId());
            
            
            
            return vehicleMapper.toDTO(savedVehicle);
        } catch (Exception e) {
            System.err.println("=== ERROR in VehicleServiceImpl.createVehicle ===");
            System.err.println("Exception type: " + e.getClass().getName());
            System.err.println("Exception message: " + e.getMessage());
            e.printStackTrace();
            System.err.println("==================================================");
            throw new RuntimeException("Failed to create vehicle: " + e.getMessage(), e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VehicleResponseDTO getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id);
        if (vehicle == null) {
            throw new VehicleNotFoundException("Vehicle not found with id: " + id);
        }
        return vehicleMapper.toDTO(vehicle);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VehicleResponseDTO updateVehicle(Long id, VehicleRequestDTO requestDTO) {
        Vehicle existingVehicle = vehicleRepository.findById(id);
        if (existingVehicle == null) {
            throw new VehicleNotFoundException("Vehicle not found with id: " + id);
        }

        Vehicle updatedVehicle = vehicleMapper.updateEntity(existingVehicle, requestDTO);
        Vehicle savedVehicle = vehicleRepository.save(updatedVehicle);
        return vehicleMapper.toDTO(savedVehicle);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new VehicleNotFoundException("Vehicle not found with id: " + id);
        }
        vehicleRepository.deleteById(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public CountResponseDTO deleteByEnginePower(Long enginePower) {
        Integer count = vehicleRepository.deleteByEnginePower(enginePower);
        return new CountResponseDTO(count.longValue());
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Long countLessThanEnginePower(Long enginePower) {
        return vehicleRepository.countByEnginePowerLessThan(enginePower);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<VehicleResponseDTO> findByNamePrefix(String namePrefix) {
        List<Vehicle> vehicles = vehicleRepository.findByNameStartingWith(namePrefix);
        return vehicleMapper.toDTOList(vehicles);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public VehicleListResponseDTO getVehiclesWithFilter(VehicleFilterDTO filterDTO) {
        List<FilterCondition> filters = filterDTO.getFilters();
        List<SortOption> sortOptions = filterDTO.getSort();
        PaginationOptions pagination = filterDTO.getPagination() != null 
                ? filterDTO.getPagination() 
                : new PaginationOptions(0, 20);

        List<Vehicle> vehicles = vehicleRepository.findAllWithFilters(filters, sortOptions, pagination);
        Long totalCount = vehicleRepository.countWithFilters(filters);

        int totalPages = (int) Math.ceil((double) totalCount / pagination.getSize());

        return new VehicleListResponseDTO(
                vehicleMapper.toDTOList(vehicles),
                totalCount,
                totalPages,
                pagination.getPage(),
                pagination.getSize()
        );
    }
}

