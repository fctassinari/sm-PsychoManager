package br.com.iidh.psmng.view.handler;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Tassinari
 */
@ManagedBean(name = "navigationHandler", eager = true)
@SessionScoped
public class NavigationHandler extends AbstractHandlerFacade implements Serializable {

    public NavigationHandler() {
        super(NavigationHandler.class);
    }

    private final String homePage = "home";
    private String activePage = homePage;
    private String lastPage = "home";

    public String getActivePage() {
        return activePage;
    }

    public void setActivePage(String activePage) {
        this.lastPage = this.activePage;
        this.activePage = activePage;
    }

    public String getHomePage() {
        return homePage;
    }

    public String getLastPage() {
        return lastPage;
    }

    public void setLastPage(String lastPage) {
        this.lastPage = lastPage;
    }

    public void goHome() {
        this.activePage = this.homePage;
    }

    public void goUser() {
        this.activePage = "user";
    }

    public void goUsuarios() {
        this.activePage = "usuarios";
    }

    public void goProduto() {
        this.activePage = "produto";
    }

    public void goProdutos() {
        this.activePage = "produtos";
    }

    public void goSenhaPaciente() {
        this.activePage = "senhaPaciente";
    }

    public void goError() {
        this.activePage = "error";
    }

    public void goPacientes() {
        this.activePage = "pacientes";
    }

    public void goConsultasEmAberto() {
        this.activePage = "consultasEmAberto";
    }

    public void goAdministracao() {
        this.activePage = "administracao";
    }

    public void goPainelControle() {
        this.activePage = "painelControle";
    }

    public void goConfirmarPresenca() {
        this.activePage = "confirmarPresenca";
    }

    public void goPropriedades() {
        this.activePage = "propriedades";
    }
}
