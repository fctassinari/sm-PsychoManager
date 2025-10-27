package br.com.iidh.psycho.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_fone")
public class Telefone extends PanacheEntityBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fone")
    public Long idFone;
    
    @NotBlank(message = "Número do telefone é obrigatório")
    @Size(max = 17, message = "Número deve ter no máximo 17 caracteres")
    @Column(name = "nr_fone", length = 17, nullable = false)
    public String nrFone;
    
    @NotNull(message = "Tipo do telefone é obrigatório")
    @Column(name = "tp_fone", length = 1, nullable = false)
    public Character tpFone;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", referencedColumnName = "id_paciente", nullable = false)
    @JsonIgnore
    public Paciente paciente;
    
    // Construtores
    public Telefone() {}
    
    public Telefone(String nrFone, Character tpFone, Paciente paciente) {
        this.nrFone = nrFone;
        this.tpFone = tpFone;
        this.paciente = paciente;
    }
    
    // Métodos auxiliares
    public String getTipoDescricao() {
        if (tpFone == null) return "";
        return switch (tpFone) {
            case 'C' -> "Celular";
            case 'R' -> "Residencial";
            case 'W' -> "Comercial";
            default -> "Desconhecido";
        };
    }
    
    @Override
    public String toString() {
        return "Telefone{" +
                "idFone=" + idFone +
                ", nrFone='" + nrFone + '\'' +
                ", tpFone=" + tpFone +
                '}';
    }
}
