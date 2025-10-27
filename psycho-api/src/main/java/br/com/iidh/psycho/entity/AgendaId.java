package br.com.iidh.psycho.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class AgendaId implements Serializable {
    
    @Column(name = "id_paciente")
    public Long idPaciente;
    
    @Column(name = "dt_consulta")
    public LocalDateTime dtConsulta;
    
    // Construtores
    public AgendaId() {}
    
    public AgendaId(Long idPaciente, LocalDateTime dtConsulta) {
        this.idPaciente = idPaciente;
        this.dtConsulta = dtConsulta;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgendaId agendaId = (AgendaId) o;
        return Objects.equals(idPaciente, agendaId.idPaciente) &&
               Objects.equals(dtConsulta, agendaId.dtConsulta);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(idPaciente, dtConsulta);
    }
    
    @Override
    public String toString() {
        return "AgendaId{" +
                "idPaciente=" + idPaciente +
                ", dtConsulta=" + dtConsulta +
                '}';
    }
}
