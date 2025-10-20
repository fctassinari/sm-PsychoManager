/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.iidh.psmng.model.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author ftassinari.smanager
 */
@Embeddable
public class TBAuthenticationGroupPK implements Serializable {

    @Column(name = "USERID", length = 10, nullable = false)
    private String userid;

    @Column(name = "GROUPID",length = 10, nullable = false)
    private String groupid;

    public TBAuthenticationGroupPK() {
    }

    public TBAuthenticationGroupPK(String userid, String groupid) {
        this.userid = userid;
        this.groupid = groupid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        hash += (groupid != null ? groupid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TBAuthenticationGroupPK)) {
            return false;
        }
        TBAuthenticationGroupPK other = (TBAuthenticationGroupPK) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        if ((this.groupid == null && other.groupid != null) || (this.groupid != null && !this.groupid.equals(other.groupid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.dwv.model.entities.TBAuthenticationGroupPK[ userid=" + userid + ", groupid=" + groupid + " ]";
    }
    
}
