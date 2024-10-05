package com.tourbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tourbooking.service.ConnectionTestService;

@RestController
public class ConnectionTestController {

    @Autowired
    private ConnectionTestService connectionTestService;

    @GetMapping("/test-connection")
    public String testConnection() {
        return connectionTestService.testConnection();
    }
}
