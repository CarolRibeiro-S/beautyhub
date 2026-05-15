package com.beautyhub.service;

import com.beautyhub.dto.AppointmentResponse;
import com.beautyhub.entity.Appointment;
import com.beautyhub.entity.BeautyService;
import com.beautyhub.entity.User;
import com.beautyhub.repository.AppointmentRepository;
import com.beautyhub.repository.BeautyServiceRepository;
import com.beautyhub.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public AppointmentResponse createAppointment(String userEmail, Long serviceId, String dataHoraInicioStr) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        BeautyService service = beautyServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraInicioStr);
        Appointment appointment = new Appointment(user, service, dataHora);
        Appointment saved = appointmentRepository.save(appointment);
        return new AppointmentResponse(
                saved.getId(),
                saved.getStatus().name(),
                saved.getDataHoraInicio().toString(),
                service.getNome(),
                service.getPreco().doubleValue()
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
                        a.getService().getPreco().doubleValue()
                ))
                .collect(Collectors.toList());
    }

    public void cancelAppointment(Long appointmentId, String userEmail) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        if (!appointment.getClient().getEmail().equals(userEmail)) {
            throw new RuntimeException("Sem permissão para cancelar este agendamento");
        }
        appointment.setStatus(Appointment.Status.CANCELLED);
        appointmentRepository.save(appointment);
    }
}
