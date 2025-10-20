package br.com.iidh.psmng.view.handler;

import br.com.iidh.psmng.control.business.AgendaBusiness;
import br.com.iidh.psmng.control.business.PacienteBusiness;
import br.com.iidh.psmng.control.business.ProdutoBusiness;
import br.com.iidh.psmng.model.entities.TbAgenda;
import br.com.iidh.psmng.model.entities.TbAgendaPK;
import br.com.iidh.psmng.model.entities.TbProduto;
import br.com.iidh.psmng.util.Padroes;
import br.com.iidh.psmng.view.handler.util.AgendaDefaultScheduleEvent;
import br.com.iidh.psmng.view.handler.util.JsfUtil;
import br.com.iidh.psmng.view.handler.util.PacientesPartNameBean;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author Tassinari
 */
@ViewScoped
@ManagedBean(name = "scheduleViewHandler")
public class ScheduleViewHandler extends AbstractHandlerFacade implements Serializable {

    private final ProdutoBusiness prodBuss = getContext().getBean(ProdutoBusiness.class);
    private final AgendaBusiness ageBuss = getContext().getBean(AgendaBusiness.class);
    private final PacienteBusiness pacBuss = getContext().getBean(PacienteBusiness.class);
    private ScheduleModel lazyEventModel;
    private AgendaDefaultScheduleEvent event = new AgendaDefaultScheduleEvent();
    private PacientesPartNameBean pacientesPartNameBean;
    private int pago;
    private boolean presenca = false;
    private String observacao;
    private String qtdDiasRepetir;
    private String qtdVezesRepetir;
    private Double precoDigitado = 0.00;
    private boolean novo = true;
    private int idProduto = Padroes.CODIGO_TERAPIA;
    private List<TbProduto> lstProdutos;
    private Double valorProduto = 0.00;

    private boolean firstLoadIdProduto = false;
    private boolean firstLoadObservacao = false;
    private boolean firstLoadPago = false;
    private boolean firstLoadPresenca = false;
    private boolean firstLoadPrecoDigitado = false;

    public ScheduleViewHandler() {
        super(ScheduleViewHandler.class);
        pacientesPartNameBean = new PacientesPartNameBean(this);
    }

    @PostConstruct
    public void init() {
        lazyEventModel = new LazyScheduleModel() {
            @Override
            public void loadEvents(Date start, Date end) {
                List<TbAgenda> agendas = ageBuss.findConsultasAgendadasPorPeriodo(start, end);
                for (TbAgenda age : agendas) {
                    addEvent(new AgendaDefaultScheduleEvent(age));
                }
            }
        };
    }

    public void addEvent(ActionEvent actionEvent) {
        if (!validarDados(event) == true) {
            return;
        }

        if (event.getId() == null) {
            inserirAgenda();
            lazyEventModel.addEvent(event);
            JsfUtil.addSuccessMessage(getLabelMessages("InformacoesGravadasComSucesso"));
        } else {
            atualizarAgenda(new TbAgenda(event.getAgenda().getTbAgendaPK()), false);
            lazyEventModel.updateEvent(event);
            JsfUtil.addSuccessMessage(getLabelMessages("AtualizacaoRealizadaSucesso"));
        }
        event = new AgendaDefaultScheduleEvent();
    }

    public void deleteEvent(ActionEvent actionEvent) {
        if (event.getId() != null) {
            TbAgenda age = event.getAgenda();
            logPersistencia(Padroes.DELETE_CMD, age.toString());
            ageBuss.removerAgenda(age);
            lazyEventModel.deleteEvent(event);
        }
    }

    public void closeEvent(ActionEvent actionEvent) {

    }

    public void onEventSelect(SelectEvent selectEvent) {
        System.out.println("onEventSelect");
        event = (AgendaDefaultScheduleEvent) selectEvent.getObject();
        pacientesPartNameBean.carregarPaciente(event.getIdPaciente());
        novo = false;
        firstLoadIdProduto = true;
        firstLoadObservacao = true;
        firstLoadPago = true;
        firstLoadPresenca = true;
        firstLoadPrecoDigitado = true;
    }

    /**
     * Na alteração o preco não é mostrado na tela por seguranca para que um
     * possivel paciente veja o valor de outro
     *
     * @param selectEvent
     */
    public void onDateSelect(SelectEvent selectEvent) {

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime((Date) selectEvent.getObject());
        gc.add(java.util.Calendar.HOUR, 1);

        event = new AgendaDefaultScheduleEvent("", (Date) selectEvent.getObject(), gc.getTime());
        pacientesPartNameBean = new PacientesPartNameBean(this);
        novo = true;
        setQtdDiasRepetir("");
        setQtdVezesRepetir("");
        setIdProduto(Padroes.CODIGO_TERAPIA);
        setObservacao("");
        setPago(0);
        setPresenca(false);
        setPrecoDigitado(0.00);
        setValorProduto(0.00);
    }

    public void onEventMove(ScheduleEntryMoveEvent moveEvent) {
        event = (AgendaDefaultScheduleEvent) moveEvent.getScheduleEvent();
        atualizarAgenda(new TbAgenda(new TbAgendaPK(event.getAgenda().getTbAgendaPK().getIdPaciente(), event.getDtConsultaOriginal_onEventMove())), true);

        JsfUtil.addSuccessMessage(getLabelMessages("EventoAlterado") + " " + getLabelMessages("Dias") + " " + moveEvent.getDayDelta() + "," + getLabelMessages("Minutos") + " " + moveEvent.getMinuteDelta());
    }

    public void onEventResize(ScheduleEntryResizeEvent resizeEvent) {
        event = (AgendaDefaultScheduleEvent) resizeEvent.getScheduleEvent();
        TbAgenda age = event.getAgenda();
        logger.debug(">>>> AGENDA Alterar "
                + " Pac  = " + age.getTbPaciente().getIdPaciente() + "-" + age.getTbPaciente().getDsNome()
                + " Prod = " + age.getTbProduto().getIdProduto() + "-" + age.getTbProduto().getDsNome()
                + " De   = " + age.getTbAgendaPK().getDtConsulta()
                + " Ate  = " + age.getDtConsultaAte()
                + " Pg   = " + age.getStPagamento()
                + " Pres = " + age.getStPresenca());

        ageBuss.atualizarAgenda(age);

        JsfUtil.addSuccessMessage(getLabelMessages("EventoAlterado") + " " + getLabelMessages("Dias") + " " + resizeEvent.getDayDelta() + "," + getLabelMessages("Minutos") + " " + resizeEvent.getMinuteDelta());
    }

    private void inserirAgenda() {
        int qtdDias;
        int qtdVezes;
        if (qtdDiasRepetir.trim().equals("")) {
            qtdDias = 0;
        } else {
            qtdDias = Integer.valueOf(qtdDiasRepetir);
        }
        if (qtdVezesRepetir.trim().equals("")) {
            qtdVezes = 0;
        } else {
            qtdVezes = Integer.valueOf(qtdVezesRepetir);
        }

        Calendar start = Calendar.getInstance();
        start.setTime(event.getStartDate());
        Calendar end = Calendar.getInstance();
        end.setTime(event.getEndDate());

        for (int i = 0; i <= qtdVezes; i++) {
            gravarRegistroNovo(pacientesPartNameBean.getIdPaciente(), start.getTime(), end.getTime());
            start.set(Calendar.DATE, start.get(Calendar.DATE) + qtdDias);
            end.set(Calendar.DATE, end.get(Calendar.DATE) + qtdDias);
        }
    }

    private void gravarRegistroNovo(int idPaciente, Date start, Date end) {
        Double vlConsulta;
        TbAgenda current = new TbAgenda(idPaciente, start);
        current.setIdProduto(idProduto);
        current.setDtConsultaAte(end);
        current.setStPresenca(presenca);
        current.setDsObs(observacao);
        current.setStPagamento(pago);
        current.setVlPreco(precoDigitado > 0.00 ? precoDigitado : valorProduto);
        try {
            ageBuss.persistirAgenda(current);
            logPersistencia(Padroes.INSERT_CMD, current.toString());
        } catch (DataIntegrityViolationException ex) {
            String msg = ex.getMessage() + "\n " + pacientesPartNameBean.getNomePaciente() + " - " + Padroes.formatoDataHora.format(start) + " - " + Padroes.formatoDataHora.format(end);
            logger.error(msg);
            JsfUtil.addErrorMessage(msg);
        }
    }

    /**
     *
     * @param agendaARemover
     * @param isOnEventMove
     */
    private void atualizarAgenda(TbAgenda agendaARemover, boolean isOnEventMove) {
        TbAgenda age = event.getAgenda();

        logPersistencia(Padroes.DELETE_CMD, agendaARemover.toString());

        ageBuss.removerAgenda(agendaARemover);
        if (!isOnEventMove) {
            age.getTbAgendaPK().setDtConsulta(event.getStartDate());
            age.setDtConsultaAte(event.getEndDate());
            age.setStPresenca((presenca));
            age.setIdProduto(idProduto);
            age.setDsObs(observacao);
            age.setStPagamento(pago);
            age.setVlPreco(precoDigitado > 0.00 ? precoDigitado : valorProduto);
        }
        event.setAgenda(age);
        try {
            logPersistencia(Padroes.INSERT_CMD, age.toString());
            ageBuss.persistirAgenda(age);
        } catch (DataIntegrityViolationException ex) {
            String msg = ex.getMessage() + "\n" + pacientesPartNameBean.getNomePaciente() + " - " + Padroes.formatoDataHora.format(event.getStartDate()) + " - " + Padroes.formatoDataHora.format(event.getEndDate());
            logger.error(msg);
            JsfUtil.addErrorMessage(msg);
        }
    }

    private boolean validarDados(AgendaDefaultScheduleEvent event) {
        if (pacientesPartNameBean.getIdPaciente() == 0) {
            JsfUtil.addErrorMessage(getLabelMessages("EscolhaUmPaciente"));
            return false;
        }

        if (event.getEndDate().compareTo(event.getStartDate()) < 0) {
            JsfUtil.addErrorMessage(getLabelMessages("DataFinalNaoPodeSerAnteriorAInicial"));
            return false;
        }
        return true;
    }

    public void definirValorProdutoCliente() {
        for (TbProduto prod : lstProdutos) {
            if (prod.getIdProduto().equals(idProduto)) {
                if (pacientesPartNameBean.getPacienteSelecionado() != null) {
                    switch (pacientesPartNameBean.getStCatValor()) {
                        case '1':
                            setValorProduto(prod.getVlPreco1());
                            break;
                        case '2':
                            setValorProduto(prod.getVlPreco2());
                            break;
                        case '3':
                            setValorProduto(prod.getVlPreco3());
                            break;
                        case '4':
                            setValorProduto(prod.getVlPreco4());
                            break;
                        default:
                            setValorProduto(0.00);
                    }
                    break;
                }
            }
        }
    }

    public void onProdutoChange() {
        definirValorProdutoCliente();
        if (event != null) // estamos editando um evento e qdo o produto é alterado deve-se zerar o valor digitado
        {
            setPrecoDigitado(0.00);
        }
    }

    public Double getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(Double valorProduto) {
        this.valorProduto = valorProduto;
    }

    public List<TbProduto> carregarProdutos() {
        lstProdutos = prodBuss.findAll();
        return lstProdutos;
    }

    public String getQtdDiasRepetir() {
        return qtdDiasRepetir;
    }

    public void setQtdDiasRepetir(String qtdDiasRepetir) {
        this.qtdDiasRepetir = qtdDiasRepetir;
    }

    public String getQtdVezesRepetir() {
        return qtdVezesRepetir;
    }

    public void setQtdVezesRepetir(String qtdVezesRepetir) {
        this.qtdVezesRepetir = qtdVezesRepetir;
    }

    public boolean isNovo() {
        return novo;
    }

    public int getIdProduto() {
        if (event.getAgenda() != null && firstLoadIdProduto) {
            idProduto = event.getAgenda().getIdProduto();
            definirValorProdutoCliente();
            firstLoadIdProduto = false;
        }
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getObservacao() {
        if (event.getAgenda() != null && firstLoadObservacao) {
            observacao = event.getAgenda().getDsObs();
            firstLoadObservacao = false;
        }
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public PacientesPartNameBean getPacientesPartNameBean() {
        return pacientesPartNameBean;
    }

    public void setPaciente() {
        this.event.setTitle(pacientesPartNameBean.getNomePaciente());
        this.event.setIdPaciente(pacientesPartNameBean.getIdPaciente());
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public AgendaDefaultScheduleEvent getEvent() {
        return event;
    }

    public int getPago() {
        if (event.getAgenda() != null && firstLoadPago) {
            pago = event.getAgenda().getStPagamento();
            firstLoadPago = false;
        }

        return pago;
    }

    public void setPago(int pago) {
        this.pago = pago;
    }

    public boolean getPresenca() {
        if (event.getAgenda() != null && firstLoadPresenca) {
            presenca = event.getAgenda().getStPresenca();
            firstLoadPresenca = false;
        }
        return presenca;
    }

    public void setPresenca(boolean presenca) {
        this.presenca = presenca;
    }

    public Double getPrecoDigitado() {
        if (event.getAgenda() != null && firstLoadPrecoDigitado) {
            precoDigitado = event.getAgenda().getVlPreco();
            firstLoadPrecoDigitado = false;
        }
        return precoDigitado;
    }

    public void setPrecoDigitado(Double precoDigitado) {
        this.precoDigitado = precoDigitado;
    }

    public void setEvent(AgendaDefaultScheduleEvent event) {
        this.event = event;
    }

}
