package br.com.iidh.psmng.view.handler.util;

import br.com.iidh.psmng.model.entities.TbAgenda;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.primefaces.model.DefaultScheduleEvent;

/**
 *
 * @author Tassinari
 */
public class AgendaDefaultScheduleEvent extends DefaultScheduleEvent {

    private TbAgenda agenda;
    /**
     * Utilizado somente para o move
     * Qdo se move uma agenda o componente ScheduleEntryMoveEvent altera todos os campos data 
     * DE e ATE para a nova data (dedução que fiz depois de 3hr de teste)
     * Vou guardar a data como string para depois converter em Date para poder excluir o registro original
     */
    public String dtConsultaOriginal_onEventMove; 
    
    public AgendaDefaultScheduleEvent(TbAgenda agenda) {
        super(  agenda.getTbPaciente().getDsNome().split(" ")[0] + "-" + agenda.getTbProduto().getDsNome(), 
                agenda.getTbAgendaPK().getDtConsulta(), 
                agenda.getDtConsultaAte());
        this.agenda = agenda;
        dtConsultaOriginal_onEventMove = agenda.getTbAgendaPK().getDtConsulta().toString();
    }

    public AgendaDefaultScheduleEvent() {
        super();
    }

    public AgendaDefaultScheduleEvent(String title, Date start, Date end) {
        super(title, start, end);
    }

    public void setIdPaciente(int id) {
        this.agenda.getTbAgendaPK().setIdPaciente(id);
    }

    public int getIdPaciente() {
        return this.agenda.getTbAgendaPK().getIdPaciente();
    }

    public TbAgenda getAgenda() {
        return this.agenda;
    }

    public void setAgenda(TbAgenda agenda) {
        this.agenda = agenda;
        dtConsultaOriginal_onEventMove = agenda.getTbAgendaPK().getDtConsulta().toString();        
    }

    @Override
    public void setEndDate(Date endDate) {
        super.setEndDate(endDate); 
    }

    @Override
    public Date getEndDate() {
        return super.getEndDate(); 
    }

    @Override
    public void setStartDate(Date startDate) {
        super.setStartDate(startDate); 
                
    }

    @Override
    public Date getStartDate() {
        return super.getStartDate(); 
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title); 
    }

    @Override
    public String getTitle() {
        return super.getTitle(); 
    }

    public Date getDtConsultaOriginal_onEventMove() {
        
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newdate = null;
        try {
            newdate = dateformat.parse(dtConsultaOriginal_onEventMove);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return newdate;
    }

    public void setDtConsultaOriginal_onEventMove(String dtConsultaOriginal_onEventMove) {
        this.dtConsultaOriginal_onEventMove = dtConsultaOriginal_onEventMove;
    }

}
