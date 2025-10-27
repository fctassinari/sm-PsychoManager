package br.com.iidh.psycho.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "tb_aut_group")
public class GrupoUsuario extends PanacheEntityBase {
    
    @EmbeddedId
    public GrupoUsuarioId id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", referencedColumnName = "userid", insertable = false, updatable = false)
    @JsonIgnore
    public Usuario usuario;
    
    // Construtores
    public GrupoUsuario() {}
    
    public GrupoUsuario(String userid, String groupid) {
        this.id = new GrupoUsuarioId(userid, groupid);
    }
    
    // Métodos de busca
    public static List<GrupoUsuario> findByUsuario(String userid) {
        return find("id.userid = ?1", userid).list();
    }
    
    public static List<GrupoUsuario> findByGrupo(String groupid) {
        return find("id.groupid = ?1", groupid).list();
    }
    
    @Override
    public String toString() {
        return "GrupoUsuario{" +
                "id=" + id +
                '}';
    }
}
