package com.beautyhub.controller;

import com.beautyhub.dto.AppointmentRequest;
import com.beautyhub.dto.AppointmentResponse;
import com.beautyhub.service.AppointmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            AppointmentResponse response = appointmentService.createAppointment(
                    userEmail,
                    request.getServiceId(),
                    request.getDataHoraInicio()
            );
            return ResponseEntity.ok(response);
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
            List<AppointmentResponse> dtos = appointmentService.getAppointmentsByUser(userEmail);
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            log.error("Erro ao buscar agendamentos: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth != null ? auth.getName() : null;
            if (userEmail == null || userEmail.equals("anonymousUser")) {
                return ResponseEntity.status(401).body("Não autenticado");
            }
            appointmentService.cancelAppointment(id, userEmail);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Erro ao cancelar agendamento: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }
    }
}