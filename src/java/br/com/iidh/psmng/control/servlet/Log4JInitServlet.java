package br.com.iidh.psmng.control.servlet;

import br.com.iidh.psmng.util.Padroes;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Tassinari
 */
public class Log4JInitServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(Padroes.LIB_APP_PATH + "/log4j.properties"));
            PropertyConfigurator.configure(p);
        } catch (IOException ex) {
            Logger.getLogger(Log4JInitServlet.class.getName()).error(ex);
        }

        super.init(config);
    }
}
