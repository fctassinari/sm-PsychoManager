package br.com.iidh.psycho.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GrupoUsuarioId implements Serializable {
    
    @Column(name = "userid")
    public String userid;
    
    @Column(name = "groupid")
    public String groupid;
    
    // Construtores
    public GrupoUsuarioId() {}
    
    public GrupoUsuarioId(String userid, String groupid) {
        this.userid = userid;
        this.groupid = groupid;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrupoUsuarioId that = (GrupoUsuarioId) o;
        return Objects.equals(userid, that.userid) &&
               Objects.equals(groupid, that.groupid);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(userid, groupid);
    }
    
    @Override
    public String toString() {
        return "GrupoUsuarioId{" +
                "userid='" + userid + '\'' +
                ", groupid='" + groupid + '\'' +
                '}';
    }
}
