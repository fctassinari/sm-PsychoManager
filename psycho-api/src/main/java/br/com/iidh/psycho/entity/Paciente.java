package br.com.iidh.psycho.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_paciente")
public class Paciente extends PanacheEntityBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    public Long idPaciente;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Column(name = "ds_nome", length = 100, nullable = false)
    public String dsNome;
    
    @Email(message = "Email deve ser válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    @Column(name = "ds_email", length = 100)
    public String dsEmail;
    
    @Size(max = 100, message = "Profissão deve ter no máximo 100 caracteres")
    @Column(name = "ds_profissao", length = 100)
    public String dsProfissao;
    
    @Size(max = 100, message = "Escolaridade deve ter no máximo 100 caracteres")
    @Column(name = "ds_escolaridade", length = 100)
    public String dsEscolaridade;
    
    @Column(name = "st_estadocivil", length = 1)
    public Character stEstadocivil;
    
    @Column(name = "dt_nascimento")
    public LocalDate dtNascimento;
    
    @Size(max = 100, message = "Filhos deve ter no máximo 100 caracteres")
    @Column(name = "ds_filhos", length = 100)
    public String dsFilhos;
    
    @NotBlank(message = "Endereço é obrigatório")
    @Size(max = 100, message = "Endereço deve ter no máximo 100 caracteres")
    @Column(name = "ds_endereco", length = 100, nullable = false)
    public String dsEndereco;
    
    @NotBlank(message = "Bairro é obrigatório")
    @Size(max = 100, message = "Bairro deve ter no máximo 100 caracteres")
    @Column(name = "ds_bairro", length = 100, nullable = false)
    public String dsBairro;
    
    @NotBlank(message = "CEP é obrigatório")
    @Size(max = 9, message = "CEP deve ter no máximo 9 caracteres")
    @Column(name = "nr_cep", length = 9, nullable = false)
    public String nrCep;
    
    @Size(max = 1000, message = "Queixas deve ter no máximo 1000 caracteres")
    @Column(name = "ds_queixas", length = 1000)
    public String dsQueixas;
    
    @Size(max = 200, message = "Problemas de saúde deve ter no máximo 200 caracteres")
    @Column(name = "ds_probsaude", length = 200)
    public String dsProbsaude;
    
    @Size(max = 100, message = "Acompanhamento médico deve ter no máximo 100 caracteres")
    @Column(name = "ds_acompmedico", length = 100)
    public String dsAcompmedico;
    
    @Size(max = 100, message = "Remédios deve ter no máximo 100 caracteres")
    @Column(name = "ds_remedios", length = 100)
    public String dsRemedios;
    
    @Column(name = "st_bebe", length = 1)
    public Character stBebe;
    
    @Column(name = "st_fuma", length = 1)
    public Character stFuma;
    
    @Column(name = "st_drogas", length = 1)
    public Character stDrogas;
    
    @Column(name = "st_insonia", length = 1)
    public Character stInsonia;
    
    @Size(max = 100, message = "Calmante deve ter no máximo 100 caracteres")
    @Column(name = "ds_calmante", length = 100)
    public String dsCalmante;
    
    @Column(name = "st_tratpsic", length = 1)
    public Character stTratpsic;
    
    @Size(max = 1000, message = "Resultado deve ter no máximo 1000 caracteres")
    @Column(name = "ds_resultado", length = 1000)
    public String dsResultado;
    
    @Size(max = 1000, message = "Observação deve ter no máximo 1000 caracteres")
    @Column(name = "ds_observacao", length = 1000)
    public String dsObservacao;
    
    @Size(max = 200, message = "Ficha deve ter no máximo 200 caracteres")
    @Column(name = "ds_ficha", length = 200)
    public String dsFicha;
    
    @Column(name = "st_catvalor", length = 1)
    public Character stCatValor;
    
    // Relacionamentos
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    public List<Agenda> agendas = new ArrayList<>();
    
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    public List<Telefone> telefones = new ArrayList<>();
    
    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    public Senha senha;
    
    // Construtores
    public Paciente() {}
    
    public Paciente(String dsNome) {
        this.dsNome = dsNome;
    }
    
    // Métodos de busca
    public static List<Paciente> findByNomeContaining(String nome) {
        return find("dsNome ilike ?1", "%" + nome + "%").list();
    }
    
    public static List<Paciente> findAllOrderedById() {
        return find("order by idPaciente").list();
    }
    
    public static List<Paciente> findByEmail(String email) {
        return find("dsEmail = ?1", email).list();
    }
    
    // Métodos auxiliares
    public void addTelefone(Telefone telefone) {
        telefones.add(telefone);
        telefone.paciente = this;
    }
    
    public void removeTelefone(Telefone telefone) {
        telefones.remove(telefone);
        telefone.paciente = null;
    }
    
    @Override
    public String toString() {
        return "Paciente{" +
                "idPaciente=" + idPaciente +
                ", dsNome='" + dsNome + '\'' +
                '}';
    }
}
