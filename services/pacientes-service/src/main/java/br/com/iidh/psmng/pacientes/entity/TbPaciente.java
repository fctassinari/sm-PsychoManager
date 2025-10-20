package br.com.iidh.psmng.pacientes.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_paciente")
public class TbPaciente extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PACIENTE", unique = true, nullable = false)
    public Integer idPaciente;

    @Column(name = "DS_NOME", length = 100, nullable = false)
    public String dsNome;

    @Column(name = "DS_EMAIL", length = 100)
    public String dsEmail;

    @Column(name = "DS_PROFISSAO", length = 100)
    public String dsProfissao;

    @Column(name = "DS_ESCOLARIDADE", length = 100)
    public String dsEscolaridade;

    @Column(name = "ST_ESTADOCIVIL", length = 1)
    public Character stEstadocivil;

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_NASCIMENTO")
    public Date dtNascimento;

    @Column(name = "DS_FILHOS", length = 100)
    public String dsFilhos;

    @Column(name = "DS_ENDERECO", length = 100, nullable = false)
    public String dsEndereco;

    @Column(name = "DS_BAIRRO", length = 100, nullable = false)
    public String dsBairro;

    @Column(name = "NR_CEP", length = 9, nullable = false)
    public String nrCep;

    @Column(name = "DS_QUEIXAS", length = 1000)
    public String dsQueixas;

    @Column(name = "DS_PROBSAUDE", length = 200)
    public String dsProbsaude;

    @Column(name = "DS_ACOMPMEDICO", length = 100)
    public String dsAcompmedico;

    @Column(name = "DS_REMEDIOS", length = 100)
    public String dsRemedios;

    @Column(name = "ST_BEBE", length = 1)
    public Character stBebe;

    @Column(name = "ST_FUMA", length = 1)
    public Character stFuma;

    @Column(name = "ST_DROGAS", length = 1)
    public Character stDrogas;

    @Column(name = "ST_INSONIA", length = 1)
    public Character stInsonia;

    @Column(name = "DS_CALMANTE", length = 100)
    public String dsCalmante;

    @Column(name = "ST_TRATPSIC", length = 1)
    public Character stTratpsic;

    @Column(name = "DS_RESULTADO", length = 1000)
    public String dsResultado;

    @Column(name = "DS_OBSERVACAO", length = 1000)
    public String dsObservacao;

    @Column(name = "DS_FICHA", length = 200)
    public String dsFicha;

    @Column(name = "ST_CATVALOR", length = 1)
    public Character stCatValor;
}
