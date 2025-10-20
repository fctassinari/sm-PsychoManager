package br.com.iidh.psmng.model.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ftassinari.smanager
 */
@Entity
@Table(name = "tb_produto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbProduto.findAll", query = "SELECT t FROM TbProduto t ORDER BY t.dsNome DESC"),
    @NamedQuery(name = "TbProduto.findAllOrderID", query = "SELECT t FROM TbProduto t ORDER BY t.idProduto"),
    @NamedQuery(name = "TbProduto.findByIdProduto", query = "SELECT t FROM TbProduto t WHERE t.idProduto = :idProduto"),
//    @NamedQuery(name = "TbProduto.findByDsProduto", query = "SELECT t FROM TbProduto t WHERE t.dsNome = :dsNome")
})
public class TbProduto implements Serializable , Comparable<TbProduto>  {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID_PRODUTO", unique = true, nullable = false)
    private Integer idProduto;

    @Column(name = "DS_NOME", length = 100, nullable = false)
    private String dsNome;

    @Column(name = "VL_PRECO1", nullable = true)
    private Double vlPreco1;

    @Column(name = "VL_PRECO2", nullable = true)
    private Double vlPreco2;

    @Column(name = "VL_PRECO3", nullable = true)
    private Double vlPreco3;

    @Column(name = "VL_PRECO4", nullable = true)
    private Double vlPreco4;

    public TbProduto() {
    }

    public TbProduto(String dsNome) {
        this.dsNome = dsNome;
    }

    public TbProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public TbProduto(Integer idProduto, String dsNome) {
        this.idProduto = idProduto;
        this.dsNome = dsNome;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getDsNome() {
        return dsNome;
    }

    public void setDsNome(String dsNome) {
        this.dsNome = dsNome;
    }

    public Double getVlPreco1() {
        return vlPreco1;
    }

    public void setVlPreco1(Double vlPreco1) {
        this.vlPreco1 = vlPreco1;
    }

    public Double getVlPreco2() {
        return vlPreco2;
    }

    public void setVlPreco2(Double vlPreco2) {
        this.vlPreco2 = vlPreco2;
    }

    public Double getVlPreco3() {
        return vlPreco3;
    }

    public void setVlPreco3(Double vlPreco3) {
        this.vlPreco3 = vlPreco3;
    }

    public Double getVlPreco4() {
        return vlPreco4;
    }

    public void setVlPreco4(Double vlPreco4) {
        this.vlPreco4 = vlPreco4;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProduto != null ? idProduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbProduto)) {
            return false;
        }
        TbProduto other = (TbProduto) object;
        if ((this.idProduto == null && other.idProduto != null) || (this.idProduto != null && !this.idProduto.equals(other.idProduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TbProduto[ ID_PRODUTO = " + this.getIdProduto() +
                         " DS_NOME = "    + this.getDsNome() +
                         " VL_PRECO1 = "  + this.getVlPreco1() +
                         " VL_PRECO2 = "  + this.getVlPreco2() +
                         " VL_PRECO3 = "  + this.getVlPreco3() +
                         " VL_PRECO4 = "  + this.getVlPreco4() +
                         " ]";
    }

    @Override
    public int compareTo(TbProduto o) {
        return this.dsNome.toUpperCase().compareTo(o.dsNome.toUpperCase());
    }
    
}
