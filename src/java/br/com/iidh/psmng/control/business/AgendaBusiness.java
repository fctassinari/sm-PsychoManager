package br.com.iidh.psmng.control.business;

import br.com.iidh.psmng.model.dao.TbAgendaDAO;
import br.com.iidh.psmng.model.entities.TbAgenda;
import br.com.iidh.psmng.model.entities.TbAgendaPK;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AgendaBusiness {

    @Autowired
    private TbAgendaDAO dao;

    public void setBanco(int param) {
        this.dao.setBanco(param);
    }

    public void persistirAgenda(TbAgenda entity) {
        this.dao.create(entity);
    }

    public TbAgenda buscarAgenda(int id) {
        return this.dao.find(id);
    }

    public void removerAgenda(TbAgenda entity) {
        this.dao.remove(entity.getTbAgendaPK());
    }

    public void atualizarAgenda(TbAgenda entity) {
        this.dao.edit(entity);
    }

    public TbAgenda findByIdPacienteDTConsulta(TbAgendaPK agendapk) {
        return this.dao.findByIdPacienteDTConsulta(agendapk);
    }

    public List<TbAgenda> findConsultasEmAbertoPorPeriodo(Date de, Date ate) {
        return this.dao.findConsultasEmAbertoPorPeriodo(de,ate);
    }
    
    public List<TbAgenda> findConsultasEmAbertoPorPaciente(Integer idPaciente) {
        return this.dao.findConsultasEmAbertoPorPaciente(idPaciente);
    }

    public List<TbAgenda> findConsultasRealizadasPorPaciente(Integer idPaciente, Date de, Date ate) {
        return this.dao.findConsultasRealizadasPorPaciente(idPaciente, de, ate);
    }
    
    public List<TbAgenda> findConsultasRealizadasPorPeriodo(Date de, Date ate) {
        return this.dao.findConsultasRealizadasPorPeriodo(de,ate);
    }

    public List<TbAgenda> findConsultasAgendadasPorPeriodo(Date de, Date ate) {
        return this.dao.findConsultasAgendadasPorPeriodo(de,ate);
    }

    public List<TbAgenda> findConsultasAgendadasPorPeriodoPorPaciente(Integer idPaciente, Date de, Date ate) {
        return this.dao.findConsultasAgendadasPorPeriodoPorPaciente(idPaciente, de,ate);
    }
    
    public List<TbAgenda> findPresencasNaoConfirmadas(Integer idPaciente) {
        return this.dao.findPresencasNaoConfirmadas(idPaciente);
    }

    public List<TbAgenda> findConsultaGenerica(Integer idPaciente, Date de, Date ate, String idProduto, int presenca, int pago, int ordenarPor) {
        return this.dao.findConsultaGenerica(idPaciente, de, ate, idProduto, presenca, pago, ordenarPor);
    }

    public Double findConsultaGenericaSomatoria(Integer idPaciente, Date de, Date ate, String idProduto, int presenca, int pago, int ordenarPor) {
        return this.dao.findConsultaGenericaSomatoria(idPaciente, de, ate, idProduto, presenca, pago, ordenarPor);
    }

    public List<Object[]> findQtdConsutasProdutoPeriodo(Date de, Date ate){
        return this.dao.findQtdConsutasProdutoPeriodo(de, ate);
    }

    public List<Object[]> findQtdFormaPagamentoProduto(Date de, Date ate){
        return this.dao.findQtdFormaPagamentoProduto(de, ate);
    }
    public List<Object[]> findQtdConsutasProdutoPorMesNoPeriodo(GregorianCalendar de, GregorianCalendar ate, Integer idProduto){
        return this.dao.findQtdConsutasProdutoPorMesNoPeriodo(de, ate, idProduto);
    }
    
}
