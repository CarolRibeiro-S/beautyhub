package com.salao.agendamentos.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "appointments")
public class Appointment {

    public enum Status { SCHEDULED, CANCELLED, COMPLETED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "service_id")
    private Service service;

    @NotNull
    private LocalDateTime dataHoraInicio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.SCHEDULED;

    public Appointment() {}

    public Appointment(User client, Service service, LocalDateTime dataHoraInicio) {
        this.client = client;
        this.service = service;
        this.dataHoraInicio = dataHoraInicio;
        this.status = Status.SCHEDULED;
    }

    public Long getId() { return id; }
    public User getClient() { return client; }
    public void setClient(User client) { this.client = client; }
    public Service getService() { return service; }
    public void setService(Service service) { this.service = service; }
    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public void setDataHoraInicio(LocalDateTime dataHoraInicio) { this.dataHoraInicio = dataHoraInicio; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
