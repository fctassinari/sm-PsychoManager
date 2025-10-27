package br.com.iidh.psycho.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_aut_user")
public class Usuario extends PanacheEntityBase {
    
    @Id
    @NotBlank(message = "User ID é obrigatório")
    @Size(max = 10, message = "User ID deve ter no máximo 10 caracteres")
    @Column(name = "userid", length = 10, nullable = false)
    public String userid;
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(max = 255, message = "Senha deve ter no máximo 255 caracteres")
    @Column(name = "password", length = 255, nullable = false)
    public String password;
    
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Column(name = "ds_nome", length = 100)
    public String dsNome;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    public List<GrupoUsuario> grupos = new ArrayList<>();
    
    /**
     * ID da última aba visualizada pelo usuário
     */
    @Column(name = "id_lastview", nullable = false, columnDefinition = "int DEFAULT 0")
    public Integer idLastView = (Integer) 0;
    
    @Column(name = "st_refreshauto", nullable = false, columnDefinition = "boolean DEFAULT true")
    public Boolean stRefreshAuto = (Boolean) true;
    
    // Construtores
    public Usuario() {}
    
    public Usuario(String userid, String password) {
        this.userid = userid;
        this.password = password;
    }
    
    public Usuario(String userid, String password, String dsNome) {
        this.userid = userid;
        this.password = password;
        this.dsNome = dsNome;
    }
    
    // Métodos de busca
    public static Usuario findByUserid(String userid) {
        return find("userid = ?1", userid).firstResult();
    }
    
    public static List<Usuario> findAllOrderedByUserid() {
        return find("order by userid").list();
    }
    
    // Métodos auxiliares
    public void addGrupo(GrupoUsuario grupo) {
        grupos.add(grupo);
        grupo.usuario = this;
    }
    
    public void removeGrupo(GrupoUsuario grupo) {
        grupos.remove(grupo);
        grupo.usuario = null;
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "userid='" + userid + '\'' +
                ", dsNome='" + dsNome + '\'' +
                '}';
    }
}
