package br.com.iidh.psmng.view.handler;

import br.com.iidh.psmng.control.business.AgendaBusiness;
import br.com.iidh.psmng.control.business.SenhaBusiness;
import br.com.iidh.psmng.model.entities.TbAgenda;
import br.com.iidh.psmng.model.entities.TbSenha;
import br.com.iidh.psmng.util.Padroes;
import br.com.iidh.psmng.view.handler.util.JsfUtil;
import br.com.iidh.psmng.view.handler.util.PacientesPartNameBean;
import java.io.Serializable;
import java.util.Iterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

@ManagedBean(name = "confirmarPresencaHandler")
@ViewScoped
public class ConfirmarPresencaHandler extends AbstractHandlerFacade implements Serializable {

    private AgendaBusiness ageBuss = getContext().getBean(AgendaBusiness.class);
    private SenhaBusiness senBuss = getContext().getBean(SenhaBusiness.class);

    private String senha = "";
    private DataModel consultas = null;
    PacientesPartNameBean pacientesPartNameBean;
    
    @ManagedProperty(value = "#{navigationHandler}")
    private NavigationHandler navigationHandler;

    public ConfirmarPresencaHandler() {
        super(ConfirmarPresencaHandler.class);
        pacientesPartNameBean = new PacientesPartNameBean();
    }

    public PacientesPartNameBean getPacientesPartNameBean() {
        return pacientesPartNameBean;
    }

    public void setPacientesPartNameBean(PacientesPartNameBean pacientesPartNameBean) {
        this.pacientesPartNameBean = pacientesPartNameBean;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void confirmPresence() {
        Integer idPaciente = pacientesPartNameBean.getIdPaciente();

        if (idPaciente == 0) {
            return;
        }

        TbSenha tbsenha = senBuss.buscarSenha(idPaciente);
        if (tbsenha == null || !tbsenha.getDsSenha().equals(senha)) {
            JsfUtil.addErrorMessage(getLabelMessages("SenhaInvalida"));
            return;
        }

        Iterator iterator = consultas.iterator();

        while (iterator.hasNext()) {
            TbAgenda agenda = (TbAgenda) iterator.next();
            logPersistencia(Padroes.UPDATE_CMD, agenda.toString());
            ageBuss.atualizarAgenda(agenda);
        }
        JsfUtil.addSuccessMessage(getLabelMessages("AtualizacaoRealizadaSucesso"));
        pacientesPartNameBean.setNomePaciente("");
        consultas = null;
    }

    public DataModel getConsultas() {
        return consultas;
    }

    public void setConsultas(DataModel consultas) {
        this.consultas = consultas;
    }

    public void consultarPresencasNaoConfirmadas() {
        consultas = new ListDataModel(ageBuss.findPresencasNaoConfirmadas(pacientesPartNameBean.getIdPaciente()));
    }

    public void fechar() {
        navigationHandler.goHome();
    }

    public void setNavigationHandler(NavigationHandler navigationHandler) {
        this.navigationHandler = navigationHandler;
    }

}
