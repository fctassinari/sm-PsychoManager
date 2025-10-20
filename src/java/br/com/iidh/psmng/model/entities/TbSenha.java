/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.iidh.psmng.model.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author ftassinari.smanager
 */
@Entity
@Table(name = "tb_senha")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "TbSenha.findAll", query = "SELECT t FROM TbSenha t"),
//    @NamedQuery(name = "TbSenha.findByIdPaciente", query = "SELECT t FROM TbSenha t WHERE t.idPaciente = :idPaciente"),
//    @NamedQuery(name = "TbSenha.findByDsSenha", query = "SELECT t FROM TbSenha t WHERE t.dsSenha = :dsSenha")})
public class TbSenha implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PACIENTE")
    private Integer idPaciente;
    
    @Column(name = "DS_SENHA", length = 8, nullable = false)
    private String dsSenha;

    @JoinColumn(name = "ID_PACIENTE", referencedColumnName = "ID_PACIENTE", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private TbPaciente tbPaciente;

    public TbSenha() {
    }

    public TbSenha(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public TbSenha(Integer idPaciente, String dsSenha) {
        this.idPaciente = idPaciente;
        this.dsSenha = dsSenha;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getDsSenha() {
        return dsSenha;
    }

    public void setDsSenha(String dsSenha) {
        this.dsSenha = dsSenha;
    }

    public TbPaciente getTbPaciente() {
        return tbPaciente;
    }

    public void setTbPaciente(TbPaciente tbPaciente) {
        this.tbPaciente = tbPaciente;
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
        if (!(object instanceof TbSenha)) {
            return false;
        }
        TbSenha other = (TbSenha) object;
        if ((this.idPaciente == null && other.idPaciente != null) || (this.idPaciente != null && !this.idPaciente.equals(other.idPaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.iidh.psmng.model.entities.TbSenha[ idPaciente=" + idPaciente + " ]";
    }
    
}
