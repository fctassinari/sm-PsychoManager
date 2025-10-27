package br.com.iidh.psycho.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_agenda")
public class Agenda extends PanacheEntityBase {
    
    @EmbeddedId
    public AgendaId id;
    
    @Column(name = "st_presenca")
    public Boolean stPresenca;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", referencedColumnName = "id_paciente", insertable = false, updatable = false)
    @JsonIgnore
    public Paciente paciente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto", referencedColumnName = "id_produto", insertable = false, updatable = false)
    @JsonIgnore
    public Produto produto;
    
    @Column(name = "ds_obs", length = 100)
    public String dsObs;
    
    @NotNull(message = "ID do produto é obrigatório")
    @Column(name = "id_produto", nullable = false)
    public Long idProduto;
    
    @Column(name = "dt_consulta_ate")
    public LocalDateTime dtConsultaAte;
    
    @Column(name = "vl_preco")
    public Double vlPreco;
    
    /**
     * Status de pagamento:
     * 0 = A Receber
     * 1 = Pago
     * 2 = Abonado
     * 3 = Calote
     */
    @Column(name = "st_pagamento")
    public Integer stPagamento = (Integer) 0;
    
    // Construtores
    public Agenda() {}
    
    public Agenda(Long idPaciente, LocalDateTime dtConsulta) {
        this.id = new AgendaId(idPaciente, dtConsulta);
    }
    
    public Agenda(Long idPaciente, LocalDateTime dtConsulta, Long idProduto) {
        this.id = new AgendaId(idPaciente, dtConsulta);
        this.idProduto = idProduto;
    }
    
    // Métodos de busca
    public static List<Agenda> findByPacienteAndData(Long idPaciente, LocalDateTime dtConsulta) {
        return find("id.idPaciente = ?1 and id.dtConsulta = ?2", idPaciente, dtConsulta).list();
    }
    
    public static List<Agenda> findConsultasEmAbertoPorPeriodo(LocalDateTime de, LocalDateTime ate) {
        return find("id.dtConsulta >= ?1 and id.dtConsulta <= ?2 and stPagamento = 0 and stPresenca = true order by paciente.dsNome, id.dtConsulta", de, ate).list();
    }
    
    public static List<Agenda> findConsultasEmAbertoPorPaciente(Long idPaciente) {
        return find("id.idPaciente = ?1 and stPagamento = 0 and stPresenca = true order by id.dtConsulta", idPaciente).list();
    }
    
    public static List<Agenda> findPresencasNaoConfirmadas(Long idPaciente) {
        return find("id.idPaciente = ?1 and stPresenca = false order by id.dtConsulta", idPaciente).list();
    }
    
    public static List<Agenda> findConsultasRealizadasPorPaciente(Long idPaciente, LocalDateTime de, LocalDateTime ate) {
        return find("id.idPaciente = ?1 and id.dtConsulta >= ?2 and id.dtConsulta <= ?3 and stPresenca = true order by paciente.dsNome, id.dtConsulta", idPaciente, de, ate).list();
    }
    
    public static List<Agenda> findConsultasRealizadasPorPeriodo(LocalDateTime de, LocalDateTime ate) {
        return find("id.dtConsulta >= ?1 and id.dtConsulta <= ?2 and stPresenca = true order by paciente.dsNome, id.dtConsulta", de, ate).list();
    }
    
    public static List<Agenda> findConsultasAgendadasPorPeriodo(LocalDateTime de, LocalDateTime ate) {
        return find("id.dtConsulta >= ?1 and id.dtConsulta <= ?2 order by id.dtConsulta", de, ate).list();
    }
    
    public static List<Agenda> findConsultasAgendadasPorPeriodoPorPaciente(Long idPaciente, LocalDateTime de, LocalDateTime ate) {
        return find("id.idPaciente = ?1 and id.dtConsulta >= ?1 and id.dtConsulta <= ?2 order by id.dtConsulta", idPaciente, de, ate).list();
    }
    
    // Métodos auxiliares
    public String getStatusPagamentoDescricao() {
        if (stPagamento == null) return "A Receber";
        return switch (stPagamento.intValue()) {
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agenda agenda = (Agenda) o;
        return Objects.equals(id, agenda.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Agenda{" +
                "id=" + id +
                ", stPresenca=" + stPresenca +
                ", stPagamento=" + stPagamento +
                ", vlPreco=" + vlPreco +
                '}';
    }
}
