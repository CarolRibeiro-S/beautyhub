package com.beautyhub.service;

import com.beautyhub.dto.AppointmentResponse;
import com.beautyhub.dto.DescontoResponse;
import com.beautyhub.entity.Appointment;
import com.beautyhub.entity.BeautyService;
import com.beautyhub.entity.User;
import com.beautyhub.repository.AppointmentRepository;
import com.beautyhub.repository.BeautyServiceRepository;
import com.beautyhub.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public DescontoResponse calcularDesconto(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        long totalAgendamentos = appointmentRepository.findByClient(user)
                .stream()
                .filter(a -> a.getStatus() != Appointment.Status.CANCELLED)
                .count();

        // Próximo agendamento será o totalAgendamentos + 1
        long proximoNumero = totalAgendamentos + 1;

        double desconto = 0.0;
        String mensagem = "";
        boolean gratuito = false;

        if (proximoNumero == 1) {
            desconto = 30.0;
            mensagem = "🎉 1° agendamento — 30% de desconto!";
        } else if (proximoNumero == 3) {
            desconto = 35.0;
            mensagem = "🎉 3° agendamento — 35% de desconto!";
        } else if (proximoNumero == 5) {
            desconto = 40.0;
            mensagem = "🎉 5° agendamento — 40% de desconto!";
        } else if (proximoNumero == 10) {
            desconto = 100.0;
            mensagem = "🎉 10° agendamento — Serviço GRATUITO!";
            gratuito = true;
        }

        return new DescontoResponse(desconto, mensagem, gratuito, (int) proximoNumero);
    }

    public AppointmentResponse createAppointment(String userEmail, Long serviceId, String dataHoraInicioStr) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        BeautyService service = beautyServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        LocalDateTime dataHoraInicio = LocalDateTime.parse(dataHoraInicioStr);
        LocalDateTime dataHoraFim = dataHoraInicio.plusMinutes(service.getDuracaoMinutos());

        List<Appointment> conflitos = appointmentRepository
                .findByServiceAndDataHoraInicioBetween(
                        service,
                        dataHoraInicio.minusMinutes(service.getDuracaoMinutos() - 1),
                        dataHoraFim
                );

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

    public List<String> getHorariosOcupados(Long serviceId, String data) {
        BeautyService service = beautyServiceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        LocalDate localDate = LocalDate.parse(data);
        LocalDateTime inicioDia = localDate.atStartOfDay();
        LocalDateTime fimDia = localDate.atTime(23, 59, 59);

        List<Appointment> agendamentos = appointmentRepository
                .findByServiceAndDataHoraInicioBetween(service, inicioDia, fimDia);

        return agendamentos.stream()
                .filter(a -> a.getStatus() != Appointment.Status.CANCELLED)
                .map(a -> a.getDataHoraInicio().toLocalTime().toString().substring(0, 5))
                .collect(Collectors.toList());
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
        if (!appointment.getClient().getEmail().equals(userEmail)) {
            throw new RuntimeException("Sem permissão para cancelar este agendamento");
        }
        appointment.setStatus(Appointment.Status.CANCELLED);
        appointmentRepository.save(appointment);
        log.info("Agendamento {} cancelado com sucesso", appointmentId);
    }
}