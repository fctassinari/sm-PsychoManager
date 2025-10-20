package br.com.iidh.psmng.view.handler;

import br.com.iidh.psmng.control.business.SenhaBusiness;
import br.com.iidh.psmng.model.entities.TbSenha;
import br.com.iidh.psmng.util.Padroes;
import br.com.iidh.psmng.view.handler.util.JsfUtil;
import br.com.iidh.psmng.view.handler.util.PacientesPartNameBean;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.Size;

/**
 *
 * @author Tassinari
 */
@ManagedBean(name = "senhaHandler")
@ViewScoped
public class SenhaHandler extends AbstractHandlerFacade implements Serializable {

    private SenhaBusiness senBuss = getContext().getBean(SenhaBusiness.class);

    @ManagedProperty(value = "#{navigationHandler}")
    private NavigationHandler navigationHandler;

    PacientesPartNameBean pacientesPartNameBean;

    @Size(min = 1, max = 3, message = "A Senha deve ter de {min} a {max} characters.")
    private String password = "";
    private String confirm = "";

    public SenhaHandler() {
        super(SenhaHandler.class);
        pacientesPartNameBean = new PacientesPartNameBean();
    }

    public void gravar() {
        Integer idPaciente = pacientesPartNameBean.getIdPaciente();

        TbSenha senha = senBuss.buscarSenha(idPaciente);

        if (senha == null) {
            senha = new TbSenha(idPaciente, password);
            senBuss.persistirSenha(senha);
            JsfUtil.addSuccessMessage(getLabelMessages("SenhaCriadaSucesso"));
        } else {
            senha.setDsSenha(password);
            senBuss.atualizarSenha(senha);
            JsfUtil.addSuccessMessage(getLabelMessages("SenhaAtualizadaSucesso"));
        }
        pacientesPartNameBean.setNomePaciente("");
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirm() {
        return confirm;
    }

    public PacientesPartNameBean getPacientesPartNameBean() {
        return pacientesPartNameBean;
    }

    public void setPacientesPartNameBean(PacientesPartNameBean pacientesPartNameBean) {
        this.pacientesPartNameBean = pacientesPartNameBean;
    }

    public void fechar() {
        navigationHandler.goHome();
    }

    public void setNavigationHandler(NavigationHandler navigationHandler) {
        this.navigationHandler = navigationHandler;
    }

}
