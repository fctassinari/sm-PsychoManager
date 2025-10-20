package br.com.iidh.psmng.model.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Tassinari
 */
@Entity
@Table(name = "tb_paciente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbPaciente.findByIdPaciente", query = "SELECT t FROM TbPaciente t WHERE t.idPaciente = :idPaciente"),
    @NamedQuery(name = "TbPaciente.findAllOrderID", query = "SELECT t FROM TbPaciente t ORDER BY t.idPaciente")})
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "TbPaciente.findAll", query = "SELECT t FROM TbPaciente t"),
//    @NamedQuery(name = "TbPaciente.findByIdPaciente", query = "SELECT t FROM TbPaciente t WHERE t.idPaciente = :idPaciente"),
//    @NamedQuery(name = "TbPaciente.findByDsNome", query = "SELECT t FROM TbPaciente t WHERE t.dsNome = :dsNome"),
//    @NamedQuery(name = "TbPaciente.findByDsEmail", query = "SELECT t FROM TbPaciente t WHERE t.dsEmail = :dsEmail"),
//    @NamedQuery(name = "TbPaciente.findByDsProfissao", query = "SELECT t FROM TbPaciente t WHERE t.dsProfissao = :dsProfissao"),
//    @NamedQuery(name = "TbPaciente.findByDsEscolaridade", query = "SELECT t FROM TbPaciente t WHERE t.dsEscolaridade = :dsEscolaridade"),
//    @NamedQuery(name = "TbPaciente.findByStEstadocivil", query = "SELECT t FROM TbPaciente t WHERE t.stEstadocivil = :stEstadocivil"),
//    @NamedQuery(name = "TbPaciente.findByDtNascimento", query = "SELECT t FROM TbPaciente t WHERE t.dtNascimento = :dtNascimento"),
//    @NamedQuery(name = "TbPaciente.findByDsFilhos", query = "SELECT t FROM TbPaciente t WHERE t.dsFilhos = :dsFilhos"),
//    @NamedQuery(name = "TbPaciente.findByDsEndereco", query = "SELECT t FROM TbPaciente t WHERE t.dsEndereco = :dsEndereco"),
//    @NamedQuery(name = "TbPaciente.findByDsBairro", query = "SELECT t FROM TbPaciente t WHERE t.dsBairro = :dsBairro"),
//    @NamedQuery(name = "TbPaciente.findByNrCep", query = "SELECT t FROM TbPaciente t WHERE t.nrCep = :nrCep"),
//    @NamedQuery(name = "TbPaciente.findByDsQueixas", query = "SELECT t FROM TbPaciente t WHERE t.dsQueixas = :dsQueixas"),
//    @NamedQuery(name = "TbPaciente.findByDsProbsaude", query = "SELECT t FROM TbPaciente t WHERE t.dsProbsaude = :dsProbsaude"),
//    @NamedQuery(name = "TbPaciente.findByDsAcompmedico", query = "SELECT t FROM TbPaciente t WHERE t.dsAcompmedico = :dsAcompmedico"),
//    @NamedQuery(name = "TbPaciente.findByDsRemedios", query = "SELECT t FROM TbPaciente t WHERE t.dsRemedios = :dsRemedios"),
//    @NamedQuery(name = "TbPaciente.findByStBebe", query = "SELECT t FROM TbPaciente t WHERE t.stBebe = :stBebe"),
//    @NamedQuery(name = "TbPaciente.findByStFuma", query = "SELECT t FROM TbPaciente t WHERE t.stFuma = :stFuma"),
//    @NamedQuery(name = "TbPaciente.findByStDrogas", query = "SELECT t FROM TbPaciente t WHERE t.stDrogas = :stDrogas"),
//    @NamedQuery(name = "TbPaciente.findByStInsonia", query = "SELECT t FROM TbPaciente t WHERE t.stInsonia = :stInsonia"),
//    @NamedQuery(name = "TbPaciente.findByDsCalmante", query = "SELECT t FROM TbPaciente t WHERE t.dsCalmante = :dsCalmante"),
//    @NamedQuery(name = "TbPaciente.findByStTratpsic", query = "SELECT t FROM TbPaciente t WHERE t.stTratpsic = :stTratpsic"),
//    @NamedQuery(name = "TbPaciente.findByDsResultado", query = "SELECT t FROM TbPaciente t WHERE t.dsResultado = :dsResultado"),
//    @NamedQuery(name = "TbPaciente.findByDsObservacao", query = "SELECT t FROM TbPaciente t WHERE t.dsObservacao = :dsObservacao"),
//    @NamedQuery(name = "TbPaciente.findByDsFicha", query = "SELECT t FROM TbPaciente t WHERE t.dsFicha = :dsFicha")})
public class TbPaciente implements Serializable, Comparable<TbPaciente>  {
    
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID_PACIENTE", unique = true, nullable = false)
    private Integer idPaciente;
    
    @Column(name = "DS_NOME", length = 100, nullable = false)
    private String dsNome;
    
    @Column(name = "DS_EMAIL", length = 100, nullable = true)
    private String dsEmail;

    @Column(name = "DS_PROFISSAO", length = 100, nullable = true)
    private String dsProfissao;

    @Column(name = "DS_ESCOLARIDADE", length = 100, nullable = true)
    private String dsEscolaridade;
    
    @Column(name = "ST_ESTADOCIVIL", length = 1, nullable = true)
    private Character stEstadocivil;
    
    @Column(name = "DT_NASCIMENTO")
    @Temporal(TemporalType.DATE)
    private Date dtNascimento;
    
    @Column(name = "DS_FILHOS", length = 100, nullable = true)
    private String dsFilhos;
    
    @Column(name = "DS_ENDERECO", length = 100, nullable = false)
    private String dsEndereco;
    
    @Column(name = "DS_BAIRRO", length = 100, nullable = false)
    private String dsBairro;
    
    @Column(name = "NR_CEP", length = 9, nullable = false)
    private String nrCep;
    
    @Column(name = "DS_QUEIXAS", length = 1000, nullable = true)
    private String dsQueixas;
    
    @Column(name = "DS_PROBSAUDE", length = 200, nullable = true)
    private String dsProbsaude;
    
    @Column(name = "DS_ACOMPMEDICO", length = 100, nullable = true)
    private String dsAcompmedico;
    
    @Column(name = "DS_REMEDIOS", length = 100, nullable = true)
    private String dsRemedios;
    
    @Column(name = "ST_BEBE", length = 1, nullable = true)
    private Character stBebe;
    
    @Column(name = "ST_FUMA", length = 1, nullable = true)
    private Character stFuma;
    
    @Column(name = "ST_DROGAS", length = 1, nullable = true)
    private Character stDrogas;
    
    @Column(name = "ST_INSONIA", length = 1, nullable = true)
    private Character stInsonia;
    
    @Column(name = "DS_CALMANTE", length = 100, nullable = true)
    private String dsCalmante;
    
    @Column(name = "ST_TRATPSIC", length = 1, nullable = true)
    private Character stTratpsic;
    
    @Column(name = "DS_RESULTADO", length = 1000, nullable = true)
    private String dsResultado;
    
    @Column(name = "DS_OBSERVACAO", length = 1000, nullable = true)
    private String dsObservacao;
    
    @Column(name = "DS_FICHA", length = 200, nullable = true)
    private String dsFicha;
    
    @Column(name = "ST_CATVALOR", length = 1, nullable = true)
    private Character stCatValor;


//    A linha abaixo só deve ser usada se precisarmos executar o programa PSQLtoMYSQL.java
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbPacienteAgenda", fetch=FetchType.EAGER)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbPacienteAgenda")
    private Collection<TbAgenda> tbAgendaCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tbPacienteFone", fetch=FetchType.EAGER)
    private Collection<TbFone> tbFoneCollection;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "tbPaciente", orphanRemoval=true, fetch=FetchType.EAGER)
    private TbSenha tbSenha;

    public TbPaciente() {
    }

    public TbPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public TbPaciente(Integer idPaciente, String dsNome) {
        this.idPaciente = idPaciente;
        this.dsNome = dsNome;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getDsNome() {
        return dsNome;
    }

    public void setDsNome(String dsNome) {
        this.dsNome = dsNome;
    }

    public String getDsEmail() {
        return dsEmail;
    }

    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }

    public String getDsProfissao() {
        return dsProfissao;
    }

    public void setDsProfissao(String dsProfissao) {
        this.dsProfissao = dsProfissao;
    }

    public String getDsEscolaridade() {
        return dsEscolaridade;
    }

    public void setDsEscolaridade(String dsEscolaridade) {
        this.dsEscolaridade = dsEscolaridade;
    }

    public Character getStEstadocivil() {
        return stEstadocivil;
    }

    public void setStEstadocivil(Character stEstadocivil) {
        this.stEstadocivil = stEstadocivil;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getDsFilhos() {
        return dsFilhos;
    }

    public void setDsFilhos(String dsFilhos) {
        this.dsFilhos = dsFilhos;
    }

    public String getDsEndereco() {
        return dsEndereco;
    }

    public void setDsEndereco(String dsEndereco) {
        this.dsEndereco = dsEndereco;
    }

    public String getDsBairro() {
        return dsBairro;
    }

    public void setDsBairro(String dsBairro) {
        this.dsBairro = dsBairro;
    }

    public String getNrCep() {
        return nrCep;
    }

    public void setNrCep(String nrCep) {
        this.nrCep = nrCep;
    }

    public String getDsQueixas() {
        return dsQueixas;
    }

    public void setDsQueixas(String dsQueixas) {
        this.dsQueixas = dsQueixas;
    }

    public String getDsProbsaude() {
        return dsProbsaude;
    }

    public void setDsProbsaude(String dsProbsaude) {
        this.dsProbsaude = dsProbsaude;
    }

    public String getDsAcompmedico() {
        return dsAcompmedico;
    }

    public void setDsAcompmedico(String dsAcompmedico) {
        this.dsAcompmedico = dsAcompmedico;
    }

    public String getDsRemedios() {
        return dsRemedios;
    }

    public void setDsRemedios(String dsRemedios) {
        this.dsRemedios = dsRemedios;
    }

    public Character getStBebe() {
        return stBebe;
    }

    public void setStBebe(Character stBebe) {
        this.stBebe = stBebe;
    }

    public Character getStFuma() {
        return stFuma;
    }

    public void setStFuma(Character stFuma) {
        this.stFuma = stFuma;
    }

    public Character getStDrogas() {
        return stDrogas;
    }

    public void setStDrogas(Character stDrogas) {
        this.stDrogas = stDrogas;
    }

    public Character getStInsonia() {
        return stInsonia;
    }

    public void setStInsonia(Character stInsonia) {
        this.stInsonia = stInsonia;
    }

    public String getDsCalmante() {
        return dsCalmante;
    }

    public void setDsCalmante(String dsCalmante) {
        this.dsCalmante = dsCalmante;
    }

    public Character getStTratpsic() {
        return stTratpsic;
    }

    public void setStTratpsic(Character stTratpsic) {
        this.stTratpsic = stTratpsic;
    }

    public String getDsResultado() {
        return dsResultado;
    }

    public void setDsResultado(String dsResultado) {
        this.dsResultado = dsResultado;
    }

    public String getDsObservacao() {
        return dsObservacao;
    }

    public void setDsObservacao(String dsObservacao) {
        this.dsObservacao = dsObservacao;
    }

    public String getDsFicha() {
        return dsFicha;
    }

    public void setDsFicha(String dsFicha) {
        this.dsFicha = dsFicha;
    }

    public Character getStCatValor() {
        return stCatValor;
    }

    public void setStCatValor(Character stCatValor) {
        this.stCatValor = stCatValor;
    }

    @XmlTransient
    public Collection<TbFone> getTbFoneCollection() {
        return tbFoneCollection;
    }

    public void setTbFoneCollection(Collection<TbFone> tbFoneCollection) {
        this.tbFoneCollection = tbFoneCollection;
    }

    public TbSenha getTbSenha() {
        return tbSenha;
    }

    public void setTbSenha(TbSenha tbSenha) {
        this.tbSenha = tbSenha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPaciente != null ? idPaciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbPaciente)) {
            return false;
        }
        TbPaciente other = (TbPaciente) object;
        if ((this.idPaciente == null && other.idPaciente != null) || (this.idPaciente != null && !this.idPaciente.equals(other.idPaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TbPaciente[ ID_PACIENTE=" + this.getIdPaciente() + 
                          " DS_NOME = "   + this.getDsNome() +
                          " ]";
    }

    @XmlTransient
    public Collection<TbAgenda> getTbAgendaCollection() {
        return tbAgendaCollection;
    }

    public void setTbAgendaCollection(Collection<TbAgenda> tbAgendaCollection) {
        this.tbAgendaCollection = tbAgendaCollection;
    }

    @Override
    public int compareTo(TbPaciente o) {
         return this.dsNome.toUpperCase().compareTo(o.dsNome.toUpperCase());
    }

}
