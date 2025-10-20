package br.com.iidh.psmng.view.handler;

import br.com.iidh.psmng.view.handler.util.JsfUtil;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Tassinari
 */
@ManagedBean(name = "loginHandler", eager = true)
@SessionScoped
public class LoginHandler extends AbstractHandlerFacade implements Serializable {

    private String name = "";
    private String pass = "";

    /**
     * Creates a new instance of LoginHandler
     */
    public LoginHandler() {
        super(LoginHandler.class);
    }

    @PostConstruct
    public void init() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(this.name, this.pass);
        } catch (ServletException e) {
            JsfUtil.addErrorMessage(getLabelMessages("UsuarioOuSenhaInvalidos"));
            return "/index";
        }
        return "application/home.xhtml";
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            request.logout();
        } catch (ServletException e) {
            JsfUtil.addErrorMessage("Logout failed.");
        }
        return "/application/navigation?faces-redirect=true";
    }

    public void logoutForAjax() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        ((HttpServletRequest)context.getExternalContext().getRequest()).logout();
        FacesContext.getCurrentInstance().getExternalContext().redirect("navigation.xhtml");
    }

    public void keepSessionAlive() {
        //metodo chamado somente para ressetar o session timeout
    }
}
