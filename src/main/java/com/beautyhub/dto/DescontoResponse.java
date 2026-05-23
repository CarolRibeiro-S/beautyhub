package com.beautyhub.dto;

public class DescontoResponse {
    private double percentual;
    private String mensagem;
    private boolean gratuito;
    private int numeroAgendamento;

    public DescontoResponse(double percentual, String mensagem, boolean gratuito, int numeroAgendamento) {
        this.percentual = percentual;
        this.mensagem = mensagem;
        this.gratuito = gratuito;
        this.numeroAgendamento = numeroAgendamento;
    }

    public double getPercentual() { return percentual; }
    public String getMensagem() { return mensagem; }
    public boolean isGratuito() { return gratuito; }
    public int getNumeroAgendamento() { return numeroAgendamento; }
}