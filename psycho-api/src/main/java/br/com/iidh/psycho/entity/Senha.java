package br.com.iidh.psycho.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_senha")
public class Senha extends PanacheEntityBase {
    
    @Id
    @Column(name = "id_paciente")
    public Long idPaciente;
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(max = 8, message = "Senha deve ter no máximo 8 caracteres")
    @Column(name = "ds_senha", length = 8, nullable = false)
    public String dsSenha;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", referencedColumnName = "id_paciente", insertable = false, updatable = false)
    @JsonIgnore
    public Paciente paciente;
    
    // Construtores
    public Senha() {}
    
    public Senha(Long idPaciente, String dsSenha) {
        this.idPaciente = idPaciente;
        this.dsSenha = dsSenha;
    }
    
    // Métodos de busca
    public static Senha findByPaciente(Long idPaciente) {
        return find("idPaciente = ?1", idPaciente).firstResult();
    }
    
    @Override
    public String toString() {
        return "Senha{" +
                "idPaciente=" + idPaciente +
                '}';
    }
}
