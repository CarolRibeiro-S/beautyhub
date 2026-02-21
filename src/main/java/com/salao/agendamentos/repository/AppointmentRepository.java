package com.salao.agendamentos.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salao.agendamentos.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDataHoraInicioBetween(LocalDateTime inicio, LocalDateTime fim);
}
