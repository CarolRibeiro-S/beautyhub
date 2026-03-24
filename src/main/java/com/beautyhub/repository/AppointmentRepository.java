package com.beautyhub.repository;

import com.beautyhub.entity.Appointment;
import com.beautyhub.entity.User;
import com.beautyhub.entity.BeautyService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByClient(User client);

    List<Appointment> findByServiceAndDataHoraInicioBetween(
            BeautyService service,
            LocalDateTime start,
            LocalDateTime end
    );
}