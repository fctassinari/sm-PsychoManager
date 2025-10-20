package br.com.iidh.psmng.util;

import java.text.DecimalFormat;
import java.util.Locale;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

/**
 *
 * @author Tassinari
 */
public abstract class AbstractCommonFacade<T> {

    public Logger logger;
    public Logger modelLogger;
    private ApplicationContext ctx;

    public ApplicationContext getContext() {
        if (ctx == null) {
            ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        }
        return ctx;
    }

    public AbstractCommonFacade(Class<T> entityClass) {
        logger = Logger.getLogger(entityClass);
        modelLogger = Logger.getLogger("modelLogFile");
    }

    public String getConfigPropertie(String s) {
        return Padroes.config.getString(s);
    }

    public String getLabelMessages(String s) {
        return Padroes.psmng.getString(s);
    }

    public Locale getActualLocale() {
        return FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }

    public String getTimezone() {
        return Padroes.TIMEZONE;
    }

    public DecimalFormat getFormatacaoDecimal() {
        return new DecimalFormat("0.00");
    }

    public Object getBean(Class cls) {
        return getContext().getBean(cls);
    }
}
