/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.iidh.psmng.model.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ftassinari.smanager
 */
@Embeddable
public class TbAgendaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_PACIENTE")
    private Integer idPaciente;
    @Basic(optional = false)
    @Column(name = "DT_CONSULTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtConsulta;

    public TbAgendaPK() {
    }

    public TbAgendaPK(int idPaciente, Date dtConsulta) {
        this.idPaciente = idPaciente;
        this.dtConsulta = dtConsulta;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Date getDtConsulta() {
        return dtConsulta;
    }

    public void setDtConsulta(Date dtConsulta) {
        this.dtConsulta = dtConsulta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPaciente;
        hash += (dtConsulta != null ? dtConsulta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbAgendaPK)) {
            return false;
        }
        TbAgendaPK other = (TbAgendaPK) object;
        if (this.idPaciente != other.idPaciente) {
            return false;
        }
        if ((this.dtConsulta == null && other.dtConsulta != null) || (this.dtConsulta != null && !this.dtConsulta.equals(other.dtConsulta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.iidh.psmng.model.entities.TbAgendaPK[ idPaciente=" + idPaciente + ", dtConsulta=" + dtConsulta + " ]";
    }
    
}
