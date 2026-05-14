package com.beautyhub.controller;

import com.beautyhub.dto.AppointmentRequest;
import com.beautyhub.dto.AppointmentResponse;
import com.beautyhub.entity.Appointment;
import com.beautyhub.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private static final Logger log = LoggerFactory.getLogger(AppointmentController.class);
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentRequest request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth != null ? auth.getName() : null;

            if (userEmail == null || userEmail.equals("anonymousUser")) {
                return ResponseEntity.status(401).body("Não autenticado");
            }

            Appointment appointment = appointmentService.createAppointment(
                    userEmail,
                    request.getServiceId(),
                    request.getDataHoraInicio()
            );
            return ResponseEntity.ok(toResponse(appointment));
        } catch (Exception e) {
            log.error("Erro ao criar agendamento: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyAppointments() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth != null ? auth.getName() : null;

            if (userEmail == null || userEmail.equals("anonymousUser")) {
                return ResponseEntity.status(401).body("Não autenticado");
            }

            List<AppointmentResponse> appointments = appointmentService
                    .getAppointmentsByUser(userEmail)
                    .stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            log.error("Erro ao buscar agendamentos: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }
    }

    private AppointmentResponse toResponse(Appointment a) {
        return new AppointmentResponse(
                a.getId(),
                a.getStatus().name(),
                a.getDataHoraInicio().toString(),
                a.getService().getNome(),
                a.getService().getPreco().doubleValue()
        );
    }
}
