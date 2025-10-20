/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.iidh.psmng.model.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ftassinari.smanager
 */
@Entity
@Table(name = "tb_aut_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TBAuthenticationUser.findAll", query = "SELECT t FROM TBAuthenticationUser t")})
public class TBAuthenticationUser implements Serializable, Comparable<TBAuthenticationUser> {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "USERID", length = 10, nullable = false)
    private String userid;

    @Column(name = "PASSWORD", length = 255, nullable = false)
    private String password;

    @Column(name = "DS_NOME", length = 100)
    private String dsNome;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tBAuthenticationUser", orphanRemoval = true, fetch = FetchType.EAGER)
    private Collection<TBAuthenticationGroup> tBAuthenticationGroupCollection;

    /**
     * ID da ultima aba visualizada pelo usuário
     */
    @Column(name = "ID_LASTVIEW", nullable = false, columnDefinition = "int DEFAULT 0")
    private int idLastView;

    @Column(name = "ST_REFRESHAUTO", nullable = false, columnDefinition = "boolean DEFAULT true")
    private boolean stRefreshAuto;

    public TBAuthenticationUser() {
    }

    public TBAuthenticationUser(String userid) {
        this.userid = userid;
    }

    public TBAuthenticationUser(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDsNome() {
        return dsNome;
    }

    public void setDsNome(String dsNome) {
        this.dsNome = dsNome;
    }

    public int getIdLastView() {
        return idLastView;
    }

    public void setIdLastView(int idLastView) {
        this.idLastView = idLastView;
    }

    public boolean isStRefreshAuto() {
        return stRefreshAuto;
    }

    public void setStRefreshAuto(boolean stRefreshAuto) {
        this.stRefreshAuto = stRefreshAuto;
    }

    @XmlTransient
    public Collection<TBAuthenticationGroup> getTBAuthenticationGroupCollection() {
        return tBAuthenticationGroupCollection;
    }

    public void setTBAuthenticationGroupCollection(Collection<TBAuthenticationGroup> tBAuthenticationGroupCollection) {
        this.tBAuthenticationGroupCollection = tBAuthenticationGroupCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TBAuthenticationUser)) {
            return false;
        }
        TBAuthenticationUser other = (TBAuthenticationUser) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.dwv.model.entities.TBAuthenticationUser[ userid=" + userid + " ]";
    }

    @Override
    public int compareTo(TBAuthenticationUser o) {
        return this.userid.toUpperCase().compareTo(o.userid.toUpperCase());
    }
}
