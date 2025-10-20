package br.com.iidh.psmng.view.handler;

import br.com.iidh.psmng.control.business.AgendaBusiness;
import br.com.iidh.psmng.control.business.ProdutoBusiness;
import br.com.iidh.psmng.model.entities.TbAgenda;
import br.com.iidh.psmng.model.entities.TbPaciente;
import br.com.iidh.psmng.model.entities.TbProduto;
import br.com.iidh.psmng.util.Padroes;
import br.com.iidh.psmng.util.SelectOneMenuAux;
import br.com.iidh.psmng.view.handler.util.CalendarPeriodBean;
import br.com.iidh.psmng.view.handler.util.JsfUtil;
import br.com.iidh.psmng.view.handler.util.PacientesPartNameBean;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

@ManagedBean(name = "administracaoHandler")
@ViewScoped
public class AdministracaoHandler extends AbstractHandlerFacade implements Serializable {

    private final AgendaBusiness ageBuss = getContext().getBean(AgendaBusiness.class);
    private final ProdutoBusiness prodBuss = getContext().getBean(ProdutoBusiness.class);
    private PacientesPartNameBean pacientesPartNameBean;
    private CalendarPeriodBean calendarPeriodBean;
    private int idProduto;
    private List<TbAgenda> consultas;
    private int pago;
    private int presenca;
    private int ordenarPor;
    private String idPacienteAtualizar;
    private String dtConsultaAtualizar;
    private List<TbProduto> listaProdutos;
    private List<SelectItem> filtroPaciente;
    private List<SelectItem> filtroObservacao;
    private List<TbAgenda> filteredAgenda;
    private boolean consultaRealizada = false;
    private String resultadoConsulta;
    
    public AdministracaoHandler() {
        super(AdministracaoHandler.class);
        pacientesPartNameBean = new PacientesPartNameBean();
        calendarPeriodBean = new CalendarPeriodBean();
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public List<TbProduto> carregarProdutos() {
        if (listaProdutos == null) {
            listaProdutos = prodBuss.findAll();
            listaProdutos.add(new TbProduto(0, getLabelMessages("Todos")));
        }
        return listaProdutos;
    }

    public List<TbProduto> carregarOpcoesPago() {
        List<SelectOneMenuAux> aux = new ArrayList<>();
        aux.add(new SelectOneMenuAux(0, getLabelMessages("AReceber")));
        aux.add(new SelectOneMenuAux(1, getLabelMessages("Pago")));
        aux.add(new SelectOneMenuAux(2, getLabelMessages("Abonado")));
        aux.add(new SelectOneMenuAux(3, getLabelMessages("Calote")));
        return (List) aux;
    }

    public List<TbProduto> carregarStatusPago() {
        List<SelectOneMenuAux> aux = new ArrayList<>();
        aux.add(new SelectOneMenuAux(0, getLabelMessages("AReceber")));
        aux.add(new SelectOneMenuAux(1, getLabelMessages("Pago")));
        aux.add(new SelectOneMenuAux(2, getLabelMessages("Abonado")));
        aux.add(new SelectOneMenuAux(3, getLabelMessages("Calote")));
        aux.add(new SelectOneMenuAux(99, getLabelMessages("Todos")));
        return (List) aux;
    }

    public List<TbProduto> carregarOpcoesPresenca() {
        List<SelectOneMenuAux> aux = new ArrayList<>();
        aux.add(new SelectOneMenuAux(0, getLabelMessages("Todos")));
        aux.add(new SelectOneMenuAux(1, getLabelMessages("Compareceu")));
        aux.add(new SelectOneMenuAux(2, getLabelMessages("NaoCompareceu")));
        return (List) aux;
    }

    public List<TbProduto> ordenarPor() {
        List<SelectOneMenuAux> aux = new ArrayList<>();
        aux.add(new SelectOneMenuAux(0, getLabelMessages("DataENome")));
        aux.add(new SelectOneMenuAux(1, getLabelMessages("NomeEData")));
        return (List) aux;
    }

    public PacientesPartNameBean getPacientesPartNameBean() {
        return pacientesPartNameBean;
    }

    public void setPacientesPartNameBean(PacientesPartNameBean pacientesPartNameBean) {
        this.pacientesPartNameBean = pacientesPartNameBean;
    }

    public CalendarPeriodBean getCalendarPeriodBean() {
        return calendarPeriodBean;
    }

    public void setCalendarPeriodBean(CalendarPeriodBean calendarPeriodBean) {
        this.calendarPeriodBean = calendarPeriodBean;
    }

    public int getOrdenarPor() {
        return ordenarPor;
    }

    public void setOrdenarPor(int ordenarPor) {
        this.ordenarPor = ordenarPor;
    }

    public int getPago() {
        return pago;
    }

    public void setPago(int pago) {
        this.pago = pago;
    }

    public int getPresenca() {
        return presenca;
    }

    public void setPresenca(int presenca) {
        this.presenca = presenca;
    }

    public String getIdPacienteAtualizar() {
        return idPacienteAtualizar;
    }

    public void setIdPacienteAtualizar(String idPacienteAtualizar) {
        this.idPacienteAtualizar = idPacienteAtualizar;
    }

    public String getDtConsultaAtualizar() {
        return dtConsultaAtualizar;
    }

    public void setDtConsultaAtualizar(String dtConsultaAtualizar) {
        this.dtConsultaAtualizar = dtConsultaAtualizar;
    }

    public void consultar() {
        Date de = calendarPeriodBean.getSelectedDateDe();
        GregorianCalendar gcDe = new GregorianCalendar();
        gcDe.setTime(de);
        gcDe.set(GregorianCalendar.HOUR, 0);
        gcDe.set(GregorianCalendar.MINUTE, 0);
        gcDe.set(GregorianCalendar.SECOND, 0);

        Date ate = calendarPeriodBean.getSelectedDateAte();
        GregorianCalendar gcAte = new GregorianCalendar();
        gcAte.setTime(ate);
        gcAte.set(GregorianCalendar.HOUR, 23);
        gcAte.set(GregorianCalendar.MINUTE, 59);
        gcAte.set(GregorianCalendar.SECOND, 59);

        String idsProds;
        if (idProduto == 0) { //todos
            List<TbProduto> lstProd = prodBuss.findAll();
            Iterator iterator = lstProd.iterator();
            StringBuffer produtos = new StringBuffer();

            while (iterator.hasNext()) {
                TbProduto prod = (TbProduto) iterator.next();
                produtos.append(prod.getIdProduto());
                produtos.append(",");
            }
            idsProds = produtos.substring(0, produtos.length() - 1);
        } else {
            idsProds = String.valueOf(idProduto);
        }

        consultas = ageBuss.findConsultaGenerica(pacientesPartNameBean.getIdPaciente(),
                gcDe.getTime(),
                gcAte.getTime(),
                idsProds,
                presenca,
                pago,
                ordenarPor);
        criarListaPacientesObservacaoParaFiltro();

        if (consultas.size() <= 0) {
            JsfUtil.addWarningMessage(getLabelMessages("SemResultadoParaEstaPesquisa"));
            resultadoConsulta = "";
            return;
        }

        Double total = ageBuss.findConsultaGenericaSomatoria(pacientesPartNameBean.getIdPaciente(),
                gcDe.getTime(),
                gcAte.getTime(),
                idsProds,
                presenca,
                pago,
                ordenarPor);

        resultadoConsulta = (consultas.size() + " "
                + getLabelMessages("RegistrosEncontradosParaEstaBusca") + "... "
                + getLabelMessages("$")
                + getFormatacaoDecimal().format(total));
        consultaRealizada = true;
        
    }

    public List<TbAgenda> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<TbAgenda> consultas) {
        this.consultas = consultas;
    }

    public void setParamAgendaGravar() {
        Map<String, String> requestParamMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (requestParamMap.containsKey("idPaciente")) {
            this.idPacienteAtualizar = requestParamMap.get("idPaciente");
        }
        if (requestParamMap.containsKey("dtConsulta")) {
            this.dtConsultaAtualizar = requestParamMap.get("dtConsulta");
        }
    }

    public void gravar() throws ParseException {
        int idPac = Integer.valueOf(idPacienteAtualizar);
        Iterator iterator = consultas.iterator();

        while (iterator.hasNext()) {
            TbAgenda agenda = (TbAgenda) iterator.next();
            if (agenda.getTbAgendaPK().getIdPaciente() == idPac && agenda.getTbAgendaPK().getDtConsulta().toString().equals(dtConsultaAtualizar)) {
                logPersistencia(Padroes.UPDATE_CMD, agenda.toString());
                ageBuss.atualizarAgenda(agenda);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, getLabelMessages("AtualizacaoRealizadaSucesso"), ""));
                break;
            }
        }
        idPacienteAtualizar = "";
        dtConsultaAtualizar = "";
    }

    public List<SelectItem> getFiltroPaciente() {
        return filtroPaciente;
    }

    public void setFiltroPaciente(List<SelectItem> filtroPaciente) {
        this.filtroPaciente = filtroPaciente;
    }

    public List<SelectItem> getFiltroObservacao() {
        return filtroObservacao;
    }

    public void setFiltroObservacao(List<SelectItem> filtroObservacao) {
        this.filtroObservacao = filtroObservacao;
    }

    public void criarListaPacientesObservacaoParaFiltro() {
        filtroPaciente = new ArrayList<SelectItem>();

        filtroObservacao = new ArrayList<SelectItem>();

        HashSet<TbPaciente> pacs = new HashSet<TbPaciente>();
        HashSet<String> observacao = new HashSet<String>();

        Iterator iterator = consultas.iterator();
        while (iterator.hasNext()) {
            TbAgenda agenda = (TbAgenda) iterator.next();
            observacao.add(agenda.getDsObs() == null ? "" : agenda.getDsObs());
            pacs.add(agenda.getTbPaciente());
        }
        List<TbPaciente> arr = new ArrayList(pacs);
        Collections.sort(arr);

        List<String> ag = new ArrayList(observacao);
        Collections.sort(ag);

        Iterator it = arr.iterator();
        while (it.hasNext()) {
            TbPaciente pac = (TbPaciente) it.next();
            filtroPaciente.add(new SelectItem(pac.getDsNome()));
        }

        Iterator itt = ag.iterator();
        while (itt.hasNext()) {
            filtroObservacao.add(new SelectItem(itt.next()));
        }
    }

    public List<TbAgenda> getFilteredAgenda() {
        return filteredAgenda;
    }

    public void setFilteredAgenda(List<TbAgenda> filteredAgenda) {
        this.filteredAgenda = filteredAgenda;
    }

    public boolean isConsultaRealizada() {
        return consultaRealizada;
    }

    public void setConsultaRealizada(boolean consultaRealizada) {
        this.consultaRealizada = consultaRealizada;
    }

    public String getResultadoConsulta() {
        return resultadoConsulta;
    }

}
