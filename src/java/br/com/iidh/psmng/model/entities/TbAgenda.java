/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.iidh.psmng.model.entities;

import br.com.iidh.psmng.util.Padroes;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ftassinari.smanager
 */
@Entity
@Table(name = "tb_agenda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbAgenda.findByIdDtConsulta", query = "SELECT t FROM TbAgenda t WHERE t.tbAgendaPK.idPaciente = :idPaciente and t.tbAgendaPK.dtConsulta = :dtConsulta"),
    @NamedQuery(name = "TbAgenda.findConsultasEmAbertoPorPeriodo", query = "SELECT t FROM TbAgenda t WHERE t.tbAgendaPK.dtConsulta >= :dtDe and t.tbAgendaPK.dtConsulta <= :dtAte and t.stPagamento = 0 and t.stPresenca = 1 ORDER BY t.tbPacienteAgenda.dsNome, t.tbAgendaPK.dtConsulta"),
    @NamedQuery(name = "TbAgenda.findByIdPacienteConsultaAberta", query = "SELECT t FROM TbAgenda t WHERE t.tbAgendaPK.idPaciente = :idPaciente and t.stPagamento = 0 and t.stPresenca = 1 ORDER BY t.tbAgendaPK.dtConsulta"),
    @NamedQuery(name = "TbAgenda.findByIdPacientePresencasNaoConfirmadas", query = "SELECT t FROM TbAgenda t WHERE t.tbAgendaPK.idPaciente = :idPaciente and t.stPresenca = 'false' ORDER BY t.tbAgendaPK.dtConsulta"),
    @NamedQuery(name = "TbAgenda.findConsultasRealizadasPorPaciente", query = "SELECT t FROM TbAgenda t WHERE t.tbAgendaPK.idPaciente = :idPaciente and t.tbAgendaPK.dtConsulta >= :dtDe and t.tbAgendaPK.dtConsulta <= :dtAte  and t.stPresenca = 1 ORDER BY t.tbPacienteAgenda.dsNome, t.tbAgendaPK.dtConsulta"),
    @NamedQuery(name = "TbAgenda.findConsultasRealizadasPorPeriodo", query = "SELECT t FROM TbAgenda t WHERE t.tbAgendaPK.dtConsulta >= :dtDe and t.tbAgendaPK.dtConsulta <= :dtAte and t.stPresenca = 1 ORDER BY t.tbPacienteAgenda.dsNome, t.tbAgendaPK.dtConsulta"),  
    @NamedQuery(name = "TbAgenda.findConsultasAgendadasPorPeriodo", query = "SELECT t FROM TbAgenda t WHERE t.tbAgendaPK.dtConsulta >= :dtDe and t.tbAgendaPK.dtConsulta <= :dtAte ORDER BY t.tbAgendaPK.dtConsulta"),
    @NamedQuery(name = "TbAgenda.findConsultasAgendadasPorPeriodoPorPaciente", query = "SELECT t FROM TbAgenda t WHERE t.tbAgendaPK.idPaciente = :idPaciente and t.tbAgendaPK.dtConsulta >= :dtDe and t.tbAgendaPK.dtConsulta <= :dtAte ORDER BY t.tbAgendaPK.dtConsulta")})


public class TbAgenda implements Serializable, Cloneable, Comparable<TbAgenda> {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected TbAgendaPK tbAgendaPK;

    @Column(name = "ST_PRESENCA", length = 1)
    private Boolean stPresenca;

    @JoinColumn(name = "ID_PACIENTE", referencedColumnName = "ID_PACIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TbPaciente tbPacienteAgenda;

    @JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID_PRODUTO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TbProduto tbProduto;

    @Column(name = "DS_OBS", length = 100, nullable = true)
    private String dsObs;

    @Column(name = "ID_PRODUTO")
    private int idProduto;

    @Column(name = "DT_CONSULTA_ATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtConsultaAte;

    @Column(name = "VL_PRECO", nullable = true)
    private Double vlPreco;

    /**
     * 0 = A Receber 1 = Pago 2 = Abonado 3 = Calote
     */
    @Column(name = "ST_PAGAMENTO")
    private int stPagamento;

    public TbAgenda() {
    }

    public TbAgenda(TbAgendaPK tbAgendaPK) {
        this.tbAgendaPK = tbAgendaPK;
    }

    public TbAgenda(int idPaciente, Date dtConsulta) {
        this.tbAgendaPK = new TbAgendaPK(idPaciente, dtConsulta);
    }

    public TbAgendaPK getTbAgendaPK() {
        return tbAgendaPK;
    }

    public void setTbAgendaPK(TbAgendaPK tbAgendaPK) {
        this.tbAgendaPK = tbAgendaPK;
    }

    public Boolean getStPresenca() {
        return stPresenca;
    }

    public void setStPresenca(Boolean stPresenca) {
        this.stPresenca = stPresenca;
    }

    public int getStPagamento() {
        return stPagamento;
    }

    public void setStPagamento(int stPagamento) {
        this.stPagamento = stPagamento;
    }

    public TbPaciente getTbPaciente() {
        return tbPacienteAgenda;
    }

    public void setTbPaciente(TbPaciente tbPaciente) {
        this.tbPacienteAgenda = tbPaciente;
    }

    public TbProduto getTbProduto() {
        return tbProduto;
    }

    public void setTbProduto(TbProduto tbProduto) {
        this.tbProduto = tbProduto;
    }

    public String getDsObs() {
        return dsObs;
    }

    public void setDsObs(String dsObs) {
        this.dsObs = dsObs;
    }

    public Date getDtConsultaAte() {
        return dtConsultaAte;
    }

    public void setDtConsultaAte(Date dtConsultaAte) {
        this.dtConsultaAte = dtConsultaAte;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public Double getVlPreco() {
        return vlPreco;
    }

    public void setVlPreco(Double vlPreco) {
        this.vlPreco = vlPreco;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tbAgendaPK != null ? tbAgendaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbAgenda)) {
            return false;
        }
        TbAgenda other = (TbAgenda) object;
        if ((this.tbAgendaPK == null && other.tbAgendaPK != null) || (this.tbAgendaPK != null && !this.tbAgendaPK.equals(other.tbAgendaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TbAgenda[ ID_PACIENTE = "     + this.tbAgendaPK.getIdPaciente() + " - " + (this.getTbPaciente()==null?"":this.getTbPaciente().getDsNome()) +
                        " DT_CONSULTA = "     + Padroes.formatoData.format(this.tbAgendaPK.getDtConsulta()) + 
                        " DT_CONSULTA_ATE = " + (this.getDtConsultaAte()==null?"":Padroes.formatoData.format(this.getDtConsultaAte())) +
                        " ID_PRODUTO = "      + this.idProduto + " - " + (this.getTbProduto()==null?"":this.getTbProduto().getDsNome()) +
                        " ST_PAGAMENTO = "    + this.getStPagamento() + " - " + (this.getStPagamento()==0?"A Receber":this.getStPagamento()==1?"Pago":this.getStPagamento()==2?"Abonado":this.getStPagamento()==3?"Calote":"") +
                        " ST_PRESENCA = "     + this.getStPresenca() + 
                        " VL_PRECO = "        + this.getVlPreco() +            
                        " DS_OBS = "          + this.getDsObs() +
                        " ]";
    }

    public Object clone() throws CloneNotSupportedException {
        return (TbAgenda) super.clone();
    }

    @Override
    public int compareTo(TbAgenda o) {
        if(this.dsObs == null)
            this.dsObs = "";
        if(o.dsObs == null)
            o.dsObs = "";
        
        return this.dsObs.toUpperCase().compareTo(o.dsObs.toUpperCase());
    }

}
