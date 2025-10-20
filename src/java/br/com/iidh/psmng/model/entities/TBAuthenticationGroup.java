/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.iidh.psmng.model.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ftassinari.smanager
 */
@Entity
@Table(name = "tb_aut_group")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TBAuthenticationGroup.findAll", query = "SELECT t FROM TBAuthenticationGroup t"),
    @NamedQuery(name = "TBAuthenticationGroup.findByUserid", query = "SELECT t FROM TBAuthenticationGroup t WHERE t.tBAuthenticationGroupPK.userid = :userid"),
    @NamedQuery(name = "TBAuthenticationGroup.findByGroupid", query = "SELECT t FROM TBAuthenticationGroup t WHERE t.tBAuthenticationGroupPK.groupid = :groupid")})
public class TBAuthenticationGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected TBAuthenticationGroupPK tBAuthenticationGroupPK;
    
    @JoinColumn(name = "USERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TBAuthenticationUser tBAuthenticationUser;

    public TBAuthenticationGroup() {
    }

    public TBAuthenticationGroup(TBAuthenticationGroupPK tBAuthenticationGroupPK) {
        this.tBAuthenticationGroupPK = tBAuthenticationGroupPK;
    }

    public TBAuthenticationGroup(String userid, String groupid) {
        this.tBAuthenticationGroupPK = new TBAuthenticationGroupPK(userid, groupid);
    }

    public TBAuthenticationGroupPK getTBAuthenticationGroupPK() {
        return tBAuthenticationGroupPK;
    }

    public void setTBAuthenticationGroupPK(TBAuthenticationGroupPK tBAuthenticationGroupPK) {
        this.tBAuthenticationGroupPK = tBAuthenticationGroupPK;
    }

    public TBAuthenticationUser getTBAuthenticationUser() {
        return tBAuthenticationUser;
    }

    public void setTBAuthenticationUser(TBAuthenticationUser tBAuthenticationUser) {
        this.tBAuthenticationUser = tBAuthenticationUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tBAuthenticationGroupPK != null ? tBAuthenticationGroupPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TBAuthenticationGroup)) {
            return false;
        }
        TBAuthenticationGroup other = (TBAuthenticationGroup) object;
        if ((this.tBAuthenticationGroupPK == null && other.tBAuthenticationGroupPK != null) || (this.tBAuthenticationGroupPK != null && !this.tBAuthenticationGroupPK.equals(other.tBAuthenticationGroupPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.dwv.model.entities.TBAuthenticationGroup[ tBAuthenticationGroupPK=" + tBAuthenticationGroupPK + " ]";
    }
    
}
