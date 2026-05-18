package com.beautyhub.repository;

import com.beautyhub.entity.Appointment;
import com.beautyhub.entity.BeautyService;
import com.beautyhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentService extends JpaRepository<Appointment, Long> {
    List<Appointment> findByClient(User client);
    List<Appointment> findByServiceAndDataHoraInicioBetween(BeautyService service, LocalDateTime start, LocalDateTime end);
}
