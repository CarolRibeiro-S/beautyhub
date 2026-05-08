package com.beautyhub.dto;

import jakarta.validation.constraints.NotNull;

public class AppointmentRequest {
    @NotNull
    private Long serviceId;

    @NotNull
    private String dataHoraInicio;

    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }

    public String getDataHoraInicio() { return dataHoraInicio; }
    public void setDataHoraInicio(String dataHoraInicio) { this.dataHoraInicio = dataHoraInicio; }
}