package com.beautyhub.dto;

public class AppointmentResponse {
    private Long id;
    private String status;
    private String dataHoraInicio;
    private String servicoNome;
    private Double servicoPreco;
    private Long serviceId;

    public AppointmentResponse(Long id, String status, String dataHoraInicio, String servicoNome, Double servicoPreco) {
        this.id = id;
        this.status = status;
        this.dataHoraInicio = dataHoraInicio;
        this.servicoNome = servicoNome;
        this.servicoPreco = servicoPreco;
    }

    public AppointmentResponse(Long id, String status, String dataHoraInicio, String servicoNome, Double servicoPreco, Long serviceId) {
        this.id = id;
        this.status = status;
        this.dataHoraInicio = dataHoraInicio;
        this.servicoNome = servicoNome;
        this.servicoPreco = servicoPreco;
        this.serviceId = serviceId;
    }

    public Long getId() { return id; }
    public String getStatus() { return status; }
    public String getDataHoraInicio() { return dataHoraInicio; }
    public String getServiceoNome() { return servicoNome; }
    public Double getServiceoPreco() { return servicoPreco; }
    public Long getServiceId() { return serviceId; }
}
