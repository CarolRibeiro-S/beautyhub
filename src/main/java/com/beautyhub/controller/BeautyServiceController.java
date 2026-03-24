package com.beautyhub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beautyhub.entity.BeautyService;
import com.beautyhub.service.BeautyServiceService;

import java.util.List;

@RestController
@RequestMapping("/services")
public class BeautyServiceController {

    private final BeautyServiceService beautyServiceService;

    public BeautyServiceController(BeautyServiceService beautyServiceService) {
        this.beautyServiceService = beautyServiceService;
    }

    @GetMapping
    public ResponseEntity<List<BeautyService>> getAllServices() {
        List<BeautyService> services = beautyServiceService.findAll();
        return ResponseEntity.ok(services);
    }
}