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
@Table(name = "tb_produto")
public class Produto extends PanacheEntityBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    public Long idProduto;
    
    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Column(name = "ds_nome", length = 100, nullable = false)
    public String dsNome;
    
    @Column(name = "vl_preco1")
    public Double vlPreco1;
    
    @Column(name = "vl_preco2")
    public Double vlPreco2;
    
    @Column(name = "vl_preco3")
    public Double vlPreco3;
    
    @Column(name = "vl_preco4")
    public Double vlPreco4;
    
    // Relacionamentos
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    public List<Agenda> agendas = new ArrayList<>();
    
    // Construtores
    public Produto() {}
    
    public Produto(String dsNome) {
        this.dsNome = dsNome;
    }
    
    // Métodos de busca
    public static List<Produto> findByNomeContaining(String nome) {
        return find("dsNome ilike ?1", "%" + nome + "%").list();
    }
    
    public static List<Produto> findAllOrderedById() {
        return find("order by idProduto").list();
    }
    
    public static List<Produto> findAllOrderedByNome() {
        return find("order by dsNome").list();
    }
    
    // Métodos auxiliares
    public Double getPrecoByCategoria(int categoria) {
        return (Double) switch (categoria) {
            case 1 -> vlPreco1;
            case 2 -> vlPreco2;
            case 3 -> vlPreco3;
            case 4 -> vlPreco4;
            default -> 0.0;
        };
    }
    
    public void setPrecoByCategoria(int categoria, Double valor) {
        switch (categoria) {
            case 1 -> this.vlPreco1 = valor;
            case 2 -> this.vlPreco2 = valor;
            case 3 -> this.vlPreco3 = valor;
            case 4 -> this.vlPreco4 = valor;
        }
    }
    
    @Override
    public String toString() {
        return "Produto{" +
                "idProduto=" + idProduto +
                ", dsNome='" + dsNome + '\'' +
                '}';
    }
}
