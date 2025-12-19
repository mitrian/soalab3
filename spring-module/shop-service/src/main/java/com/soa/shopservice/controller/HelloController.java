package com.soa.shopservice.controller;

import com.soa.shopservice.service.WebVehicleClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RefreshScope
@RestController
@RequestMapping("/api/v1")
public class HelloController {

    private final WebVehicleClient webVehicleClient;
    
    @Value("${web-vehicle.url:http://localhost:8082}")
    private String webVehicleUrl;
    
    @Value("${server.port:8084}")
    private String serverPort;

    @Autowired
    public HelloController(WebVehicleClient webVehicleClient) {
        this.webVehicleClient = webVehicleClient;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        try {
            String response = webVehicleClient.callWebVehicle();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error calling web-vehicle: " + e.getMessage());
        }
    }
    
    @GetMapping("/config")
    public ResponseEntity<Map<String, String>> getConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("web-vehicle.url", webVehicleUrl);
        config.put("server.port", serverPort);
        config.put("message", "Эти значения показывают, откуда берется конфигурация");
        return ResponseEntity.ok(config);
    }
}

