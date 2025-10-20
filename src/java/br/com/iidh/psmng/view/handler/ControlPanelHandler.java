package br.com.iidh.psmng.view.handler;

import br.com.iidh.psmng.view.handler.util.JsfUtil;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Tassinari
 */
@ManagedBean(name = "controlPanelHandler", eager = true)
@SessionScoped
public class ControlPanelHandler extends AbstractHandlerFacade implements Serializable {

    @ManagedProperty(value = "#{navigationHandler}")
    private NavigationHandler navigationHandler;

    public ControlPanelHandler() {
        super(ControlPanelHandler.class);
    }

    public void setNavigationHandler(NavigationHandler navigationHandler) {
        this.navigationHandler = navigationHandler;
    }

    public void fechar() {
        navigationHandler.goHome();
    }
}
