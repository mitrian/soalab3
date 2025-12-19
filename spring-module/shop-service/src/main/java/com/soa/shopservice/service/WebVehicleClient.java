package com.soa.shopservice.service;

import com.soa.shopservice.dto.VehicleListResponseDTO;
import com.soa.shopservice.dto.VehicleResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WebVehicleClient {

    private static final Logger logger = LoggerFactory.getLogger(WebVehicleClient.class);

    private final RestTemplate restTemplate;
    private final String webVehicleUrl;

    public WebVehicleClient(RestTemplate restTemplate,
                           @Value("${web-vehicle.url:http://localhost:8082}") String webVehicleUrl) {
        this.restTemplate = restTemplate;
        this.webVehicleUrl = webVehicleUrl;
    }

    public String callWebVehicle() {
        String url = webVehicleUrl + "/api/health";
        logger.info("Sending HTTP GET request to web-vehicle: {}", url);
        
        ResponseEntity<String> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            String.class
        );
        
        logger.info("Received HTTP response from web-vehicle with status: {}", response.getStatusCode());
        return response.getBody();
    }

    public List<VehicleResponseDTO> searchByNumberOfWheels(int from, int to) {
        String url = webVehicleUrl + "/api/v1/vehicles/filter";

        String xmlBody = """
                <VehicleFilterDTO>
                    <filters>
                        <filterCondition>
                            <field>numberOfWheels</field>
                            <operator>gte</operator>
                            <value>%d</value>
                        </filterCondition>
                        <filterCondition>
                            <field>numberOfWheels</field>
                            <operator>lte</operator>
                            <value>%d</value>
                        </filterCondition>
                    </filters>
                    <pagination>
                        <page>0</page>
                        <size>100</size>
                    </pagination>
                </VehicleFilterDTO>
                """.formatted(from, to);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(List.of(MediaType.APPLICATION_XML));

        HttpEntity<String> request = new HttpEntity<>(xmlBody, headers);

        ResponseEntity<VehicleListResponseDTO> response = restTemplate.exchange(
                url, HttpMethod.POST, request, VehicleListResponseDTO.class);

        if (response.getBody() == null) {
            return List.of();
        }

        return response.getBody().getVehicles();
    }


    public VehicleResponseDTO addWheels(Long vehicleId, int numberOfWheels) {
        String url = webVehicleUrl + "/api/v1/vehicles/" + vehicleId;
        
        // Получаем текущий vehicle
        ResponseEntity<VehicleResponseDTO> getResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                VehicleResponseDTO.class
        );

        VehicleResponseDTO vehicle = getResponse.getBody();
        if (vehicle == null) {
            throw new RuntimeException("Vehicle not found with id " + vehicleId);
        }

        // Вычисляем новое количество колес
        Long current = vehicle.getNumberOfWheels() == null ? 0L : vehicle.getNumberOfWheels();
        Long newNumberOfWheels = current + numberOfWheels;

        // Создаем XML тело для PUT запроса в формате VehicleRequestDTO
        String xmlBody = """
                <VehicleRequestDTO>
                    <name>%s</name>
                    <coordinates>
                        <x>%s</x>
                        <y>%s</y>
                    </coordinates>
                    <enginePower>%d</enginePower>
                    <type>%s</type>
                    <numberOfWheels>%d</numberOfWheels>
                    <fuelType>%s</fuelType>
                </VehicleRequestDTO>
                """.formatted(
                escapeXml(vehicle.getName()),
                vehicle.getCoordinates().getX(),
                vehicle.getCoordinates().getY(),
                vehicle.getEnginePower(),
                vehicle.getType() != null ? vehicle.getType() : "",
                newNumberOfWheels,
                vehicle.getFuelType() != null ? vehicle.getFuelType() : ""
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(List.of(MediaType.APPLICATION_XML));

        HttpEntity<String> putRequest = new HttpEntity<>(xmlBody, headers);

        logger.info("Sending HTTP PUT request to web-vehicle: {} with body: {}", url, xmlBody);

        ResponseEntity<VehicleResponseDTO> putResponse = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                putRequest,
                VehicleResponseDTO.class
        );

        logger.info("Received HTTP response from web-vehicle with status: {}", putResponse.getStatusCode());
        return putResponse.getBody();
    }

    private String escapeXml(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}

