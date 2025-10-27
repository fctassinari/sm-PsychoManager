package br.com.iidh.psycho.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AgendaDTO {
    
    public Long idPaciente;
    
    @NotNull(message = "Data da consulta é obrigatória")
    public LocalDateTime dtConsulta;
    
    public LocalDateTime dtConsultaAte;
    
    public Boolean stPresenca;
    
    public Long idProduto;
    
    public String dsObs;
    
    public Double vlPreco;
    
    /**
     * Status de pagamento:
     * 0 = A Receber
     * 1 = Pago
     * 2 = Abonado
     * 3 = Calote
     */
    public Integer stPagamento = 0;
    
    // Dados do paciente (para exibição)
    public String nomePaciente;
    
    // Dados do produto (para exibição)
    public String nomeProduto;
    
    // Construtores
    public AgendaDTO() {}
    
    public AgendaDTO(Long idPaciente, LocalDateTime dtConsulta) {
        this.idPaciente = idPaciente;
        this.dtConsulta = dtConsulta;
    }
    
    // Métodos auxiliares
    public String getStatusPagamentoDescricao() {
        if (stPagamento == null) return "A Receber";
        return switch (stPagamento) {
            case 0 -> "A Receber";
            case 1 -> "Pago";
            case 2 -> "Abonado";
            case 3 -> "Calote";
            default -> "Desconhecido";
        };
    }
    
    public String getPresencaDescricao() {
        if (stPresenca == null) return "Não confirmada";
        return stPresenca ? "Confirmada" : "Não confirmada";
    }
}
