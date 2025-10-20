package br.com.iidh.psmng.view.handler.util;

import br.com.iidh.psmng.control.business.PacienteBusiness;
import br.com.iidh.psmng.model.entities.TbPaciente;
import br.com.iidh.psmng.util.AbstractCommonFacade;
import br.com.iidh.psmng.view.handler.ScheduleViewHandler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Tassinari
 */
public class PacientesPartNameBean extends AbstractCommonFacade implements Serializable {

    private PacienteBusiness pacBuss = getContext().getBean(PacienteBusiness.class);

    private String nomePaciente;
    Integer idPaciente = 0;
    private Character stCatValor;
    private TbPaciente pacienteSelecionado;
    private ScheduleViewHandler svh;
    
    private List<TbPaciente> listaPaciente;
    
    public PacientesPartNameBean() {
        super(PacientesPartNameBean.class);
    }
    
public PacientesPartNameBean(ScheduleViewHandler svh) {
        super(PacientesPartNameBean.class);
        this.svh = svh;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public void carregarPaciente(int idPaciente) {
        TbPaciente pac = pacBuss.buscarPaciente(idPaciente);
        this.nomePaciente = pac.getDsNome();
        this.idPaciente = pac.getIdPaciente();
        this.stCatValor = pac.getStCatValor();
        listaPaciente = new ArrayList<>();
        listaPaciente.add(pac);
        pacienteSelecionado = pac;

    }

    public void onItemSelect(SelectEvent selectEvent) {
        String nome = (String) selectEvent.getObject();
        if (nome.trim().equals("")) {
            return;
        }

        for (TbPaciente pac : listaPaciente) {
            if (pac.getDsNome().equals(nome)) {
                idPaciente = pac.getIdPaciente();
                stCatValor = pac.getStCatValor();
                pacienteSelecionado = pac;
                if(svh != null)
                    svh.definirValorProdutoCliente();
                break;
            }
        }
        if (idPaciente == 0)
            JsfUtil.addErrorMessage(getLabelMessages("PacienteNaoEncontrado"));
    }
    
    public List<String> getListaPacientesPartName(String pname) {
        listaPaciente = pacBuss.findPartNome(pname);
        Iterator it = listaPaciente.iterator();
        List<String> results = new ArrayList<String>();

        while (it.hasNext()) {
            results.add(((TbPaciente) it.next()).getDsNome());
        }
        return results;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;

    }

    public Character getStCatValor() {
        return stCatValor;
    }

    public void setStCatValor(Character stCatValor) {
        this.stCatValor = stCatValor;
    }
    
    public TbPaciente getPacienteSelecionado() {
        return pacienteSelecionado;
    }   
}
