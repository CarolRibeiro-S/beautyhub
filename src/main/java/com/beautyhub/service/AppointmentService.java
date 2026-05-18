package com.beautyhub.service;

import com.beautyhub.dto.AppointmentResponse;
import com.beautyhub.entity.Appointment;
import com.beautyhub.entity.BeautyService;
import com.beautyhub.entity.User;
import com.beautyhub.repository.AppointmentService;
import com.beautyhub.repository.BeautyServiceRepository;
import com.beautyhub.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private static final Logger log = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentService appointmentRepository;
    private final BeautyServiceRepository beautyServiceRepository;
    private final UserRepository userRepository;

    public AppointmentService(AppointmentService appointmentRepository,
                              BeautyServiceRepository beautyServiceRepository,
                              UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.beautyServiceRepository = beautyServiceRepository;
        this.userRepository = userRepository;
    }

    public AppointmentResponse createAppointment(String userEmail, Long serviceId, String dataHoraInicioStr) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        BeautyService service = beautyServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        LocalDateTime dataHoraInicio = LocalDateTime.parse(dataHoraInicioStr);
        LocalDateTime dataHoraFim = dataHoraInicio.plusMinutes(service.getDuracaoMinutos());

        // Verifica conflito de horário no mesmo serviço
        List<Appointment> conflitos = appointmentRepository
                .findByServiceAndDataHoraInicioBetween(service, dataHoraInicio.minusMinutes(service.getDuracaoMinutos() - 1), dataHoraFim);

        boolean temConflito = conflitos.stream()
                .anyMatch(a -> a.getStatus() != Appointment.Status.CANCELLED);

        if (temConflito) {
            throw new RuntimeException("Horário já ocupado! Escolha outro horário para este serviço.");
        }

        Appointment appointment = new Appointment(user, service, dataHoraInicio);
        Appointment saved = appointmentRepository.save(appointment);
        return new AppointmentResponse(
                saved.getId(),
                saved.getStatus().name(),
                saved.getDataHoraInicio().toString(),
                service.getNome(),
                service.getPreco().doubleValue(),
                service.getId()
        );
    }

    public List<AppointmentResponse> getAppointmentsByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return appointmentRepository.findByClient(user)
                .stream()
                .map(a -> new AppointmentResponse(
                        a.getId(),
                        a.getStatus().name(),
                        a.getDataHoraInicio().toString(),
                        a.getService().getNome(),
                        a.getService().getPreco().doubleValue(),
                        a.getService().getId()
                ))
                .collect(Collectors.toList());
    }

    public void cancelAppointment(Long appointmentId, String userEmail) {
        log.info("Cancelando agendamento {} para {}", appointmentId, userEmail);
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado: " + appointmentId));
        log.info("Agendamento encontrado: {}", appointment.getId());
        log.info("Cliente do agendamento: {}", appointment.getClient().getEmail());
        if (!appointment.getClient().getEmail().equals(userEmail)) {
            throw new RuntimeException("Sem permissão para cancelar este agendamento");
        }
        appointment.setStatus(Appointment.Status.CANCELLED);
        appointmentRepository.save(appointment);
        log.info("Agendamento {} cancelado com sucesso", appointmentId);
    }
}