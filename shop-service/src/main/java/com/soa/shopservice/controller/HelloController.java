package com.soa.shopservice.controller;

import com.soa.shopservice.service.WebVehicleClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    private final WebVehicleClient webVehicleClient;

    @Autowired
    public HelloController(WebVehicleClient webVehicleClient) {
        this.webVehicleClient = webVehicleClient;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
//        return ResponseEntity.ok("Hello World");
        try {
            String response = webVehicleClient.callWebVehicle();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error calling web-vehicle: " + e.getMessage());
        }
    }
}

