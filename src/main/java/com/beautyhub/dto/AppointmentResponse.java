package com.beautyhub.dto;

public class AppointmentResponse {
    private Long id;
    private String status;
    private String dataHoraInicio;
    private String servicoNome;
    private Double servicoPreco;

    public AppointmentResponse(Long id, String status, String dataHoraInicio, String servicoNome, Double servicoPreco) {
        this.id = id;
        this.status = status;
        this.dataHoraInicio = dataHoraInicio;
        this.servicoNome = servicoNome;
        this.servicoPreco = servicoPreco;
    }

    public Long getId() { return id; }
    public String getStatus() { return status; }
    public String getDataHoraInicio() { return dataHoraInicio; }
    public String getServicoNome() { return servicoNome; }
    public Double getServicoPreco() { return servicoPreco; }
}