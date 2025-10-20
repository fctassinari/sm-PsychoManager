package br.com.iidh.psmng.view.handler;

import br.com.iidh.psmng.control.business.AgendaBusiness;
import br.com.iidh.psmng.model.entities.TbAgenda;
import br.com.iidh.psmng.util.Padroes;
import br.com.iidh.psmng.view.handler.util.CalendarPeriodBean;
import br.com.iidh.psmng.view.handler.util.JsfUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Tassinari
 */
@ManagedBean(name = "consultasAbertasPeriodoHandler")
@ViewScoped
public class ConsultasAbertasPeriodoHandler extends AbstractHandlerFacade implements Serializable {

    @ManagedProperty(value = "#{navigationHandler}")
    private NavigationHandler navigationHandler;

    private AgendaBusiness ageBuss = getContext().getBean(AgendaBusiness.class);

    private CalendarPeriodBean calendarPeriodBean;

    private StreamedContent reportFile;

    public ConsultasAbertasPeriodoHandler() {
        super(ConsultasAbertasPeriodoHandler.class);
        calendarPeriodBean = new CalendarPeriodBean();
    }

    public CalendarPeriodBean getCalendarPeriodBean() {
        return calendarPeriodBean;
    }

    public void setCalendarPeriodBean(CalendarPeriodBean calendarPeriodBean) {
        this.calendarPeriodBean = calendarPeriodBean;
    }

    public void consultar() throws Exception {

        killReportFiles();

        Date de = calendarPeriodBean.getSelectedDateDe();
        GregorianCalendar gcDe = new GregorianCalendar();
        gcDe.setTime(de);
        gcDe.set(GregorianCalendar.HOUR, 0);
        gcDe.set(GregorianCalendar.MINUTE, 0);
        gcDe.set(GregorianCalendar.SECOND, 0);

        Date ate = calendarPeriodBean.getSelectedDateAte();
        GregorianCalendar gcAte = new GregorianCalendar();
        gcAte.setTime(ate);
        gcAte.set(GregorianCalendar.HOUR, 23);
        gcAte.set(GregorianCalendar.MINUTE, 59);
        gcAte.set(GregorianCalendar.SECOND, 59);

        List<TbAgenda> consultas = ageBuss.findConsultasEmAbertoPorPeriodo(gcDe.getTime(), gcAte.getTime());
//        for (TbAgenda agenda : consultas) {
//            System.out.println("\n" + agenda.getTbAgendaPK().getIdPaciente());
//            System.out.println(agenda.getTbAgendaPK().getDtConsulta());
//            System.out.println(agenda.getTbPaciente().getDsNome());
//        }

        if(consultas == null || consultas.size() <= 0){
            JsfUtil.addWarningMessage(getLabelMessages("NaoExistemInformacoesParaEstePeriodo"));
            return;
        }
            

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(consultas);
        JasperPrint jasperPrint;
        HashMap map;

        map = new HashMap();
        map.put("logo", FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/default/1_0/images/logo_tranparente.gif"));
        map.put("dtDe", Padroes.formatoData.format(calendarPeriodBean.getSelectedDateDe()));
        map.put("dtAte", Padroes.formatoData.format(calendarPeriodBean.getSelectedDateAte()));

        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/ConsultasEmAbertoPorPeriodo.jasper");
        jasperPrint = JasperFillManager.fillReport(reportPath, map, ds);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();


        /*
        * Este trecho funciona qdo a tela chama o pdf desta forma
        * <h:commandLink type="button" value="#{psychoManager.Consultar}" action="#{consultasAbertasPeriodoHandler.consultar}"  target="_blank"/>

        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        response.reset();
        response.setHeader("Content-Type", "application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"ConsultasEmAbertoPorPeriodo\"");

        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        servletOutputStream.close();
        facesContext.responseComplete();
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh_mm_ss_SSS");

        String reportName = getLabelMessages("ConsultasEmAberto") + sdf.format(new Date()) + ".pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint, Padroes.config.getString("file_report_path") + "/" + reportName);

        File arq = new File(Padroes.config.getString("file_report_path") + "/" + reportName);
        InputStream input = new FileInputStream(arq);

        reportFile = new DefaultStreamedContent(input, externalContext.getMimeType(arq.getName()), arq.getName());

    }

    public StreamedContent getReportFile() {
        return reportFile;
    }

    public void setReportFile(StreamedContent reportFile) {
        this.reportFile = reportFile;
    }

    private void killReportFiles() {
        File f = new File(Padroes.config.getString("file_report_path"));
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            for (File file : files) {
                file.delete();
            }
        }

    }

    public void fechar() {
        navigationHandler.goHome();
    }

    public void setNavigationHandler(NavigationHandler navigationHandler) {
        this.navigationHandler = navigationHandler;
    }
}
