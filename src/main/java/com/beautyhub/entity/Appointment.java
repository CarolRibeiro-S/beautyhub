package com.beautyhub.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Appointment {

    public enum Status { SCHEDULED, CANCELLED, COMPLETED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    @JsonIgnoreProperties({"appointments", "passwordHash"})
    private User client;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    @JsonIgnoreProperties({"appointments"})
    private BeautyService service;

    @NotNull
    private LocalDateTime dataHoraInicio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.SCHEDULED;

    public Appointment() {}

    public Appointment(User client, BeautyService service, LocalDateTime dataHoraInicio) {
        this.client = client;
        this.service = service;
        this.dataHoraInicio = dataHoraInicio;
        this.status = Status.SCHEDULED;
    }

    public Long getId() { return id; }
    public User getClient() { return client; }
    public void setClient(User client) { this.client = client; }
    public BeautyService getService() { return service; }
    public void setService(BeautyService service) { this.service = service; }
    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public void setDataHoraInicio(LocalDateTime dataHoraInicio) { this.dataHoraInicio = dataHoraInicio; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
