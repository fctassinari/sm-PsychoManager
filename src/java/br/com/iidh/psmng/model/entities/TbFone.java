/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.iidh.psmng.model.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Tassinari
 */
@Entity
@Table(name = "tb_fone")
//@XmlRootElement
//@NamedQueries({
//    @NamedQuery(name = "TbFone.findAll", query = "SELECT t FROM TbFone t"),
//    @NamedQuery(name = "TbFone.findByIdFone", query = "SELECT t FROM TbFone t WHERE t.idFone = :idFone"),
//    @NamedQuery(name = "TbFone.findByNrFone", query = "SELECT t FROM TbFone t WHERE t.nrFone = :nrFone"),
//    @NamedQuery(name = "TbFone.findByTpFone", query = "SELECT t FROM TbFone t WHERE t.tpFone = :tpFone")})
public class TbFone implements Serializable {

    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(name = "ID_FONE")
//    private Integer idFone;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FONE")
    private Integer idFone;

    @Column(name = "NR_FONE", length = 17, nullable = false)
    private String nrFone;

    @Column(name = "TP_FONE", length = 17, nullable = false)
    private Character tpFone;

    @JoinColumn(name = "ID_PACIENTE", referencedColumnName = "ID_PACIENTE")
    @ManyToOne(optional = false)
    private TbPaciente tbPacienteFone;

    public TbFone() {
    }

    public TbFone(Integer idFone) {
        this.idFone = idFone;
    }

    public TbFone(Integer idFone, String nrFone, Character tpFone) {
        this.idFone = idFone;
        this.nrFone = nrFone;
        this.tpFone = tpFone;
    }

    public TbFone(String nrFone, Character tpFone, TbPaciente paciente) {
        this.nrFone = nrFone;
        this.tpFone = tpFone;
        this.tbPacienteFone = paciente;
    }

    public Integer getIdFone() {
        return idFone;
    }

    public void setIdFone(Integer idFone) {
        this.idFone = idFone;
    }

    public String getNrFone() {
        return nrFone;
    }

    public void setNrFone(String nrFone) {
        this.nrFone = nrFone;
    }

    public Character getTpFone() {
        return tpFone;
    }

    public void setTpFone(Character tpFone) {
        this.tpFone = tpFone;
    }

    public TbPaciente getPaciente() {
        return tbPacienteFone;
    }

    public void setPaciente(TbPaciente paciente) {
        this.tbPacienteFone = paciente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFone != null ? idFone.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbFone)) {
            return false;
        }
        TbFone other = (TbFone) object;
        if ((this.idFone == null && other.idFone != null) || (this.idFone != null && !this.idFone.equals(other.idFone))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.iidh.psmng.model.entities.TbFone[ idFone=" + idFone + " ]";
    }

}
