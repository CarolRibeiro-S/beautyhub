package com.beautyhub.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beautyhub.dto.AppointmentRequest;
import com.beautyhub.entity.Appointment;
import com.beautyhub.service.AppointmentService;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody AppointmentRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        Appointment appointment = appointmentService.createAppointment(
                userEmail,
                request.getServiceId(),
                request.getDataHoraInicio()
        );
        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/my")
    public ResponseEntity<List<Appointment>> getMyAppointments() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        List<Appointment> appointments = appointmentService.getAppointmentsByUser(userEmail);
        return ResponseEntity.ok(appointments);
    }
}