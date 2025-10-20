package br.com.iidh.psmng.view.handler;

import br.com.iidh.psmng.control.business.ProdutoBusiness;
import br.com.iidh.psmng.model.entities.TbProduto;
import br.com.iidh.psmng.util.Padroes;
import br.com.iidh.psmng.view.handler.util.JsfUtil;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

@ManagedBean(name = "produtoHandler", eager = true)
@SessionScoped
public class ProdutoHandler extends AbstractHandlerFacade implements Serializable, ICadastroHandlerFacade {

    @ManagedProperty(value = "#{navigationHandler}")
    private NavigationHandler navigationHandler;

    private final ProdutoBusiness prodBuss = getContext().getBean(ProdutoBusiness.class);

    private DataModel resultado;
    private TbProduto regCorrente;
    private final String nomePagina = "produto";
    private int operacao;
    private String txtPesquisa;

    public ProdutoHandler() {
        super(ProdutoHandler.class);
    }

    public String getTxtPesquisa() {
        return txtPesquisa;
    }

    public void setTxtPesquisa(String txtPesquisa) {
        this.txtPesquisa = txtPesquisa;
    }

    public void pesquisarNome() {
        List<TbProduto> lista = prodBuss.findLikeNome(txtPesquisa.trim());
        Collections.sort(lista);
        resultado = new ListDataModel(lista);
    }

    public DataModel getResultado() {
        return resultado;
    }

    public TbProduto getSelected() {
        if (regCorrente == null) {
            regCorrente = new TbProduto();
        }
        return regCorrente;
    }

    @Override
    public void novo() {
        regCorrente = new TbProduto();
        operacao = Padroes.OPERACAO_NOVO;
        navigationHandler.setActivePage(nomePagina);
    }

    @Override
    public void editar() {
        regCorrente = (TbProduto) resultado.getRowData();
        operacao = Padroes.OPERACAO_ATUALIZAR;
        navigationHandler.setActivePage(nomePagina);
    }

    @Override
    public void excluir() {
        regCorrente = (TbProduto) resultado.getRowData();
        performDestroy();
        resultado = null;
    }

    @Override
    public void visualizar() {
        regCorrente = (TbProduto) resultado.getRowData();
        operacao = Padroes.OPERACAO_VISUALIZAR;
        navigationHandler.setActivePage(nomePagina);
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

    public int getOperacao() {
        return operacao;
    }

    public void setOperacao(int operacao) {
        this.operacao = operacao;
    }

    public boolean isVisualizacao() {
        return operacao == Padroes.OPERACAO_VISUALIZAR;
    }

    public void setNavigationHandler(NavigationHandler navigationHandler) {
        this.navigationHandler = navigationHandler;
    }

    @Override
    public void gravar() {
        validarCampos();
        switch (operacao) {
            case Padroes.OPERACAO_NOVO:
                try {
                    logPersistencia(Padroes.INSERT_CMD, regCorrente.toString());
                    prodBuss.persistirProduto(regCorrente);
                    JsfUtil.addSuccessMessage(getLabelMessages("InformacoesGravadasComSucesso"));
                    operacao = Padroes.OPERACAO_ATUALIZAR;
                } catch (Exception e) {
                    JsfUtil.addErrorMessage(e, getLabelMessages("ErroNaPersistencia"));
                }
            break;
            case Padroes.OPERACAO_ATUALIZAR:
                try {
                    logPersistencia(Padroes.INSERT_CMD, regCorrente.toString());
                    prodBuss.atualizarProduto(regCorrente);
                    JsfUtil.addSuccessMessage(getLabelMessages("InformacoesGravadasComSucesso"));
                } catch (Exception e) {
                    JsfUtil.addErrorMessage(e, getLabelMessages("ErroNaPersistencia"));
                }
            break;
        }
    }

    private void validarCampos() {
        if (regCorrente.getVlPreco1() == null || regCorrente.getVlPreco1() < new Double(0.00)) {
            regCorrente.setVlPreco1(0.00);
        }
        if (regCorrente.getVlPreco2() == null || regCorrente.getVlPreco2() <= new Double(0.00)) {
            regCorrente.setVlPreco2(regCorrente.getVlPreco1());
        }
        if (regCorrente.getVlPreco3() == null || regCorrente.getVlPreco3() <= new Double(0.00)) {
            regCorrente.setVlPreco3(regCorrente.getVlPreco1());
        }
        if (regCorrente.getVlPreco4() == null || regCorrente.getVlPreco4() <= new Double(0.00)) {
            regCorrente.setVlPreco4(regCorrente.getVlPreco1());
        }
    }

    private void performDestroy() {
        try {
            logPersistencia(Padroes.DELETE_CMD, regCorrente.toString());
            prodBuss.removerProduto(regCorrente);
            JsfUtil.addSuccessMessage(getLabelMessages("InformacaoExcluidaComSucesso"));
        } catch (Exception e) {
            logger.error(e.getMessage());
            JsfUtil.addErrorMessage(e, getLabelMessages("ErroNaPersistencia"));
        }
    }
}
