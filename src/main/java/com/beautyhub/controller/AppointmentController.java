package com.beautyhub.controller;

import com.beautyhub.dto.AppointmentRequest;
import com.beautyhub.entity.Appointment;
import com.beautyhub.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        String userEmail = auth.getName(); // Assumindo que o principal é o email

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