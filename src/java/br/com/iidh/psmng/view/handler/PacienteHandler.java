package br.com.iidh.psmng.view.handler;

import br.com.iidh.psmng.control.business.PacienteBusiness;
import br.com.iidh.psmng.model.entities.TbFone;
import br.com.iidh.psmng.model.entities.TbPaciente;
import br.com.iidh.psmng.util.Padroes;
import br.com.iidh.psmng.view.handler.util.JsfUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "pacienteHandler", eager = true)
@SessionScoped
public class PacienteHandler extends AbstractHandlerFacade implements Serializable, ICadastroHandlerFacade{

    private final PacienteBusiness pacBuss = getContext().getBean(PacienteBusiness.class);

    private DataModel resultado;
    private TbPaciente regCorrente;
    private final String nomePagina = "paciente";
    private int operacao;
    private String txtPesquisa;
    UploadedFile uploadedFile = null;

    private DataModel resultadoFone;
    private String tpNovoFone = "R";
    private String nrNovoFone;
    private String dsFichaOriginal = "";

    @ManagedProperty(value = "#{navigationHandler}")
    private NavigationHandler navigationHandler;

    public PacienteHandler() {
        super(PacienteHandler.class);
    }

    public String getTxtPesquisa() {
        return txtPesquisa;
    }

    public void setTxtPesquisa(String txtPesquisa) {
        this.txtPesquisa = txtPesquisa;
    }

    public void pesquisarNome() {
        List<TbPaciente> lista = pacBuss.findLikeNome(txtPesquisa.trim());
        Collections.sort(lista);
        resultado = new ListDataModel(lista);
    }

    public DataModel getResultado() {
        return resultado;
    }

    public TbPaciente getSelected() {
        if (regCorrente == null) {
            regCorrente = new TbPaciente();
        }
        if (this.dsFichaOriginal == null) {
            this.dsFichaOriginal = regCorrente.getDsFicha();
        }
        return regCorrente;
    }

    @Override
    public void visualizar() {
        regCorrente = (TbPaciente) resultado.getRowData();
        operacao = Padroes.OPERACAO_VISUALIZAR;
        navigationHandler.setActivePage(nomePagina);
    }

    @Override
    public void editar() {
        regCorrente = (TbPaciente) resultado.getRowData();
        operacao = Padroes.OPERACAO_ATUALIZAR;
        navigationHandler.setActivePage(nomePagina);
    }

    @Override
    public void excluir() {
        regCorrente = (TbPaciente) resultado.getRowData();
        performDestroy();
        resultado = null;
        resultado = null;
    }

    @Override
    public void voltar() {
        resultado = null;
        navigationHandler.setActivePage(navigationHandler.getLastPage());
    }

    @Override
    public void fechar() {
        navigationHandler.goHome();
    }

    /**
     * define o registro da lista que foi clicado para download do pdf Tela list
     * de paciente
     *
     * @throws java.lang.Exception
     */
    public void downloadPDFLista() throws Exception {
        regCorrente = (TbPaciente) resultado.getRowData();
        downloadPDF();
    }

    private void performDestroy() {
        try {
            logPersistencia(Padroes.DELETE_CMD, regCorrente.toString());
            pacBuss.removerPaciente(regCorrente);
            JsfUtil.addSuccessMessage(getLabelMessages("InformacaoExcluidaComSucesso"));
        } catch (Exception e) {
            logger.error(e.getMessage());
            JsfUtil.addErrorMessage(e, getLabelMessages("ErroNaPersistencia"));
        }
    }

    public String prepareList() {
        resultado = null;
        return "list";
    }

    @Override
    public void novo() {
        regCorrente = new TbPaciente();
        operacao = Padroes.OPERACAO_NOVO;
        navigationHandler.setActivePage(nomePagina);

    }

    @Override
    public void gravar() {
//        if (uploadedFile != null) {
//            gravarPDF();
//        }
        switch (operacao) {
            case Padroes.OPERACAO_NOVO:
                try {
                    pacBuss.persistirPaciente(regCorrente);
                    logPersistencia(Padroes.INSERT_CMD, regCorrente.toString());
                    JsfUtil.addSuccessMessage(getLabelMessages("InformacoesGravadasComSucesso"));
                    operacao = Padroes.OPERACAO_ATUALIZAR;
                } catch (Exception e) {
                    JsfUtil.addErrorMessage(e, getLabelMessages("ErroNaPersistencia"));
                } finally {
                    dsFichaOriginal = null;
                }
                break;
            case Padroes.OPERACAO_ATUALIZAR:
                try {
                    pacBuss.atualizarPaciente(regCorrente);
                    logPersistencia(Padroes.UPDATE_CMD, regCorrente.toString());
                    JsfUtil.addSuccessMessage(getLabelMessages("InformacoesGravadasComSucesso"));
                } catch (Exception e) {
                    JsfUtil.addErrorMessage(e, getLabelMessages("ErroNaPersistencia"));
                } finally {
                    dsFichaOriginal = null;
                }
                break;
        }
    }

    public int getOperacao() {
        return operacao;
    }

    public void setOperacao(int operacao) {
        this.operacao = operacao;
    }

    public boolean isVisualizacao() {
        return operacao == Padroes.OPERACAO_VISUALIZAR;
    }    
    
    public boolean isAtualizacao() {
        return operacao == Padroes.OPERACAO_ATUALIZAR;
    }
    
    

    final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    // Actions ------------------------------------------------------------------------------------
    public void downloadPDF() throws Exception {

        // Prepare.
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        String nomeFicha = getSelected().getDsFicha();

        File file = new File(Padroes.PDF_PATH, nomeFicha);
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open file.
            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);

            // Init servlet response.
            response.reset();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" + nomeFicha + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Finalize task.
            output.flush();
        } finally {
            // Gently close streams.
            closeDownload(output);
            closeDownload(input);
        }

        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        facesContext.responseComplete();
    }

    private void closeDownload(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }

    public String getTpNovoFone() {
        return tpNovoFone;
    }

    public void setTpNovoFone(String tpNovoFone) {
        this.tpNovoFone = tpNovoFone;
    }

    public String getNrNovoFone() {
        return nrNovoFone;
    }

    public void setNrNovoFone(String nrNovoFone) {
        this.nrNovoFone = nrNovoFone;
    }

    public void addNewPhone() {
        Collection<TbFone> collection = getSelected().getTbFoneCollection();

        if (collection == null) {
            collection = new ArrayList<TbFone>();
        }

        collection.add(new TbFone(nrNovoFone, tpNovoFone.charAt(0), getSelected()));

        getSelected().setTbFoneCollection(collection);
        nrNovoFone = "";
        tpNovoFone = "R";
    }

    public DataModel getTelefones() {
        resultadoFone = new ListDataModel((List) getSelected().getTbFoneCollection());
        return resultadoFone;
    }

    public void removePhone() {
        TbFone fone = (TbFone) resultadoFone.getRowData();
        getSelected().getTbFoneCollection().remove(fone);
    }

    public void upload(FileUploadEvent event) {
        uploadedFile = event.getFile();
        //String fileName = uploadedFile.getFileName();
        //getSelected().setDsFicha(fileName);
        getSelected().setDsFicha(String.valueOf(regCorrente.getIdPaciente()) + ".pdf");
        gravarPDF();
    }

    private void gravarPDF() {
//        String nomeFichaNovo = regCorrente.getDsFicha().trim();
//
//        //Se ja existir um nome para a ficha, e o nome novo for direfente do original, deve-se excluir o arquivo anterior (dsFichaOriginal)
//        if ((dsFichaOriginal != null || !dsFichaOriginal.equals("")) && !dsFichaOriginal.equals(nomeFichaNovo)) {
//            File arquivo = new File(getConfigPropertie("pdf_path") + "/" + dsFichaOriginal);
//            arquivo.delete();
//        }

        String nomeFichaNovo = regCorrente.getDsFicha().trim();
        
        try{
            //Tenta excluir o pdf caso ja exista e esteja sendo carregado novamente
            File arquivo = new File(getConfigPropertie("pdf_path") + "/" + nomeFichaNovo);
            arquivo.delete();
        }
        catch(Exception e){
            // nao existe arquivo para excluir
        }
        
        byte[] contents = uploadedFile.getContents(); // Or getInputStream()

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(getConfigPropertie("pdf_path") + "/" + nomeFichaNovo));
            out.write(contents);
            out.flush();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
//            JsfUtil.addErrorMessage(ex, getLabelMessages("ErroNaPersistencia"));
        }
    }

    public void setNavigationHandler(NavigationHandler navigationHandler) {
        this.navigationHandler = navigationHandler;
    }

}
