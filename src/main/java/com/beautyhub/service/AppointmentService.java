package com.beautyhub.service;

import com.beautyhub.entity.Appointment;
import com.beautyhub.entity.BeautyService;
import com.beautyhub.entity.User;
import com.beautyhub.repository.AppointmentRepository;
import com.beautyhub.repository.BeautyServiceRepository;
import com.beautyhub.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final BeautyServiceRepository beautyServiceRepository;
    private final UserRepository userRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              BeautyServiceRepository beautyServiceRepository,
                              UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.beautyServiceRepository = beautyServiceRepository;
        this.userRepository = userRepository;
    }

    public Appointment createAppointment(String userEmail, Long serviceId, String dataHoraInicioStr) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        BeautyService service = beautyServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        LocalDateTime dataHora = LocalDateTime.parse(dataHoraInicioStr);

        List<Appointment> conflicting = appointmentRepository.findByServiceAndDataHoraInicioBetween(
                service,
                dataHora,
                dataHora.plusMinutes(service.getDuracaoMinutos())
        );
        if (!conflicting.isEmpty()) {
            throw new RuntimeException("Horário indisponível");
        }

        Appointment appointment = new Appointment(user, service, dataHora);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return appointmentRepository.findByClient(user);
    }
}