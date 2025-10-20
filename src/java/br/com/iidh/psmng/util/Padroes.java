package br.com.iidh.psmng.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Tassinari
 */
public class Padroes {


//    public static final String LIB_APP_PATH = "/sistemasIIDH/shared/libs/PsychoManager/";
    public static final String LIB_APP_PATH = "/Users/tassinari/sistemasIIDH/shared/libs/PsychoManager/";

    public static ResourceBundle config;
    public static ResourceBundle psmng;
    public static SimpleDateFormat formatoData;
    public static SimpleDateFormat formatoDataHora;

    public static void loadProperties() {
        try {
            File file = new File(LIB_APP_PATH);
            URL[] urls = {file.toURI().toURL()};
            ClassLoader loader = new URLClassLoader(urls);
            try {
                config = ResourceBundle.getBundle("Config", FacesContext.getCurrentInstance().getViewRoot().getLocale(), loader);
                
                DASH_COLUMMN_QTY = Integer.valueOf(config.getString("qtd_cols_dash"));
                PDF_PATH = config.getString("pdf_path");
                TIMEZONE = config.getString("timeZone");

            } catch (NullPointerException ex) { // se for nullpointer indica que está sendo usado fora da plataforma web isto é aplicação simples
                config = ResourceBundle.getBundle("Config", Locale.getDefault(), loader);
                psmng = ResourceBundle.getBundle("psmng/PsychoManager", Locale.getDefault());
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Padroes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static {
        loadProperties();
        psmng = ResourceBundle.getBundle("psmng/PsychoManager", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        formatoData = new SimpleDateFormat(psmng.getString("FormatoData"));
        formatoDataHora = new SimpleDateFormat(psmng.getString("FormatoDataHora"));
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(LIB_APP_PATH + "/log4j.properties"));
            PropertyConfigurator.configure(p);
        } catch (IOException ex) {
            Logger.getLogger(Padroes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setDashConfig() {
        Padroes.DASH_WIDTH_PANEL = "width: " + ((int) (((Padroes.LARGURA_TELA * 0.95625) / Padroes.DASH_COLUMMN_QTY) - 20)) + "px;";
        Padroes.DASH_WIDTH_COLUMMN = "width: " + ((int) (((Padroes.LARGURA_TELA * 0.95625) / Padroes.DASH_COLUMMN_QTY))) + "px;";
    }

    // testar depois
//    private ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");
    public static final int OPERACAO_NOVO = 1;
    public static final int OPERACAO_ATUALIZAR = 2;
    public static final int OPERACAO_APAGAR = 3;
    public static final int OPERACAO_LISTAR = 4;
    public static final int OPERACAO_VISUALIZAR = 5;

    public static String PDF_PATH;
    public static String TIMEZONE;

    //Status de pagamento
    public static final int ARECEBER = 0;
    public static final int PAGO = 1;
    public static final int ABONADO = 2;
    public static final int CALOTE = 3;

    public static final String INSERT_CMD = "INSERT";
    public static final String UPDATE_CMD = "UPDATE";
    public static final String SELECT_CMD = "SELECT";
    public static final String DELETE_CMD = "DELETE";

    public static int LARGURA_TELA = 1600;
    public static int ALTURA_TELA = 900;

    public static String DASH_WIDTH_PANEL = "";
    public static String DASH_WIDTH_COLUMMN = "";
    public static int DASH_COLUMMN_QTY;

    public static final int POSTGRES = 1;
    public static final int MYSQL = 2;
    public static final int BANCO_DEFAULT = MYSQL;
    
    public static final int GLASSFISH = 1;
    public static final int WILDFLY = 2;
    public static final int SERVER_DEFAULT = WILDFLY;
    
    public static final int CODIGO_TERAPIA = 1;
}

