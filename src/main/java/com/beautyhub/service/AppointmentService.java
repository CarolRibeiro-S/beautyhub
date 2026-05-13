package com.beautyhub.service;

import com.beautyhub.entity.Appointment;
import com.beautyhub.entity.BeautyService;
import com.beautyhub.entity.User;
import com.beautyhub.repository.AppointmentRepository;
import com.beautyhub.repository.BeautyServiceRepository;
import com.beautyhub.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private static final Logger log = LoggerFactory.getLogger(AppointmentService.class);

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
        log.info("Buscando usuario: {}", userEmail);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        log.info("Buscando servico: {}", serviceId);
        BeautyService service = beautyServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        log.info("Parseando data: {}", dataHoraInicioStr);
        LocalDateTime dataHora = LocalDateTime.parse(dataHoraInicioStr);

        log.info("Salvando agendamento...");
        Appointment appointment = new Appointment(user, service, dataHora);
        Appointment saved = appointmentRepository.save(appointment);
        log.info("Agendamento salvo com id: {}", saved.getId());
        return saved;
    }

    public List<Appointment> getAppointmentsByUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return appointmentRepository.findByClient(user);
    }
}
