package br.com.iidh.psmng.view.handler;

import br.com.iidh.psmng.util.Padroes;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Tassinari
 */
@ManagedBean(name = "mainHandler", eager = true)
@SessionScoped
public class MainHandler extends AbstractHandlerFacade implements Serializable{

    public MainHandler() {
        super(MainHandler.class);
    }

    @PostConstruct
    public void init(){
    }
    
    public void setScreen() {
        Map<String, String> requestParamMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        if (requestParamMap.containsKey("scWidth")) {
            Padroes.LARGURA_TELA = Integer.valueOf(requestParamMap.get("scWidth"));
            Padroes.setDashConfig();
        }
        if (requestParamMap.containsKey("scHeight")) {
            Padroes.ALTURA_TELA = Integer.valueOf(requestParamMap.get("scHeight"));
        }
        logger.debug("=======>>>> largura = " + Padroes.LARGURA_TELA + " - altura = " + Padroes.ALTURA_TELA);
    }
}
