package br.com.iidh.psmng.view.handler;

import br.com.iidh.psmng.util.Padroes;
import br.com.iidh.psmng.view.handler.util.JsfUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Tassinari
 */
@ManagedBean(name = "propriedadesSistemaHandler", eager = true)
@ViewScoped
public class PropriedadesSistemaHandler extends AbstractHandlerFacade implements Serializable {

    @ManagedProperty(value = "#{navigationHandler}")
    private NavigationHandler navigationHandler;

    private HashMap<String, String> propriedades = new HashMap();

    private final String qtd_cols_dashExe = "3";
    private final String file_report_pathExe = "/ sistemasIIDH / shared / appfiles / PsychoManager / reportfiles";
    private final String timeZoneExe = "GMT-3";
    private final String pdf_pathExe = "/ sistemasIIDH / shared / appfiles / PsychoManager / pdf";

    public String getPdf_pathExe() {
        return pdf_pathExe;
    }

    public String getPdf_path() {
        return propriedades.get("pdf_path");
    }

    public void setPdf_path(String pdf_path) {
        propriedades.put("pdf_path", pdf_path);
    }

    public String getQtd_cols_dashExe() {
        return qtd_cols_dashExe;
    }

    public String getQtd_cols_dash() {
        return propriedades.get("qtd_cols_dash");
    }

    public void setQtd_cols_dash(String qtd_cols_dash) {
        propriedades.put("qtd_cols_dash", qtd_cols_dash);
    }

    public String getFile_report_path() {
        return propriedades.get("file_report_path");
    }

    public void setFile_report_path(String file_report_path) {
        propriedades.put("file_report_path", file_report_path);
    }

    public String getFile_report_pathExe() {
        return file_report_pathExe;
    }

    public String getTimeZone() {
        return propriedades.get("timeZone");
    }

    public void setTimeZone(String timeZone) {
        propriedades.put("timeZone", timeZone);
    }

    public String getTimeZoneExe() {
        return timeZoneExe;
    }

    public PropriedadesSistemaHandler() {
        super(PropriedadesSistemaHandler.class);
    }

    @PostConstruct
    public void init() {
        Enumeration bundleKeys = Padroes.config.getKeys();

        while (bundleKeys.hasMoreElements()) {
            String key = (String) bundleKeys.nextElement();
            String value = Padroes.config.getString(key);
            propriedades.put(key, value);
        }
    }

    public void gravar() throws IOException {
        File file = new File(Padroes.LIB_APP_PATH + "Config.properties");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        StringBuffer content = new StringBuffer();

        for (Map.Entry<String, String> entry : propriedades.entrySet()) {
            content.append(entry.getKey());
            content.append("=");
            content.append(entry.getValue());
            content.append("\n");
        }

        bw.write(content.toString());
        bw.close();

        Padroes.loadProperties();
        Padroes.setDashConfig();

        JsfUtil.addSuccessMessage(getLabelMessages("InformacoesGravadasComSucesso"));
    }

    public void setNavigationHandler(NavigationHandler navigationHandler) {
        this.navigationHandler = navigationHandler;
    }

    public void fechar() {
        navigationHandler.goHome();
    }
}
