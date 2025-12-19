package com.soa.shopservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}

