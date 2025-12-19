package com.soa.shopservice.controller;

import com.soa.shopservice.dto.VehicleResponseDTO;
import com.soa.shopservice.service.WebVehicleClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/api/v1")
public class ShopController {

    private final WebVehicleClient webVehicleClient;

    @Value("${web-vehicle.url:http://localhost:8082}")
    private String webVehicleUrl;

    @Value("${server.port:8084}")
    private String serverPort;

    public ShopController(WebVehicleClient webVehicleClient) {
        this.webVehicleClient = webVehicleClient;
    }

    @PostMapping(
            value = "/search/by-number-of-wheels/{from}/{to}",
            produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<List<VehicleResponseDTO>> searchByNumberOfWheels(
            @PathVariable int from,
            @PathVariable int to) {

        if (from < 0 || to < 0 || from > to) {
            return ResponseEntity.badRequest().build();
        }

        List<VehicleResponseDTO> vehicles = webVehicleClient.searchByNumberOfWheels(from, to);
        return ResponseEntity.ok(vehicles);
    }

    @PatchMapping(
            value = "/add-wheels/{vehicleId}/{numberOfWheels}",
            produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<VehicleResponseDTO> addWheels(
            @PathVariable("vehicleId") Long vehicleId,
            @PathVariable("numberOfWheels") int numberOfWheels) {

        if (vehicleId <= 0 || numberOfWheels <= 0) {
            return ResponseEntity.badRequest().build();
        }

        VehicleResponseDTO updatedVehicle = webVehicleClient.addWheels(vehicleId, numberOfWheels);
        return ResponseEntity.ok(updatedVehicle);
    }

}
