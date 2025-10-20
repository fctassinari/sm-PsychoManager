package br.com.iidh.psmng.view.handler;

import br.com.iidh.psmng.control.business.UserBusiness;
import br.com.iidh.psmng.model.entities.TBAuthenticationGroup;
import br.com.iidh.psmng.model.entities.TBAuthenticationUser;
import br.com.iidh.psmng.util.Base64EncoderPSM;
import br.com.iidh.psmng.util.Padroes;
import br.com.iidh.psmng.util.Teste;
import br.com.iidh.psmng.view.handler.util.JsfUtil;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import org.springframework.dao.DataIntegrityViolationException;

@ManagedBean(name = "userHandler", eager = true)
@SessionScoped
public class UserHandler extends AbstractHandlerFacade implements Serializable, ICadastroHandlerFacade {

    @ManagedProperty(value = "#{loginHandler}")
    private LoginHandler lh;

    private final UserBusiness userBuss = (UserBusiness) getBean(UserBusiness.class);

    private DataModel resultado;
    private TBAuthenticationUser regCorrente;
//    private String nomePagina = "/application/user/user";
    private String nomePagina = "user";
    private int operacao;
    private String txtPesquisa;
    private String confirmPassword;
    private List<String> selectedProfile = new ArrayList<>();
    private TBAuthenticationUser userActive = null;
    private boolean visualizandoUserActive = false;
    private String senhaOriginal;
    private boolean stRefreshAutoUserActive;

    @ManagedProperty(value = "#{navigationHandler}")
    private NavigationHandler navigationHandler;

    @ManagedProperty(value = "#{requiredMessagesHandler}")
    private RequiredMessagesHandler requiredMessagesHandler;

    public UserHandler() {
        super(UserHandler.class);
    }

    @PostConstruct
    public void init() {
        loadUserConfig();
    }

    public void loadUserConfig() {
    }

    public void setNavigationHandler(NavigationHandler navigationHandler) {
        this.navigationHandler = navigationHandler;
    }

    public String getTxtPesquisa() {
        return txtPesquisa;
    }

    public void setTxtPesquisa(String txtPesquisa) {
        this.txtPesquisa = txtPesquisa;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void pesquisarNome() {
        List<TBAuthenticationUser> lista = userBuss.findLikeNome(txtPesquisa.trim());
        Collections.sort(lista);
        resultado = new ListDataModel(lista);
    }

    public TBAuthenticationUser pesquisarUsuarioLogado(String userid) {
        if (userActive == null) {
            userActive = userBuss.find(userid);
            stRefreshAutoUserActive = userActive.isStRefreshAuto();
        }
        return userActive;
    }

    public DataModel getResultado() {
        return resultado;
    }

    public TBAuthenticationUser getSelected() {
        if (regCorrente == null) {
            regCorrente = new TBAuthenticationUser();
        }
        return regCorrente;
    }

    @Override
    public void visualizar() {
        regCorrente = (TBAuthenticationUser) resultado.getRowData();
        operacao = Padroes.OPERACAO_VISUALIZAR;
        navigationHandler.setActivePage(nomePagina);
    }

    public void editar(boolean usuarioAtivo) {
        reinicializarCampos();
        if (usuarioAtivo) {
            regCorrente = userActive;
            visualizandoUserActive = true;
        } else {
            regCorrente = (TBAuthenticationUser) resultado.getRowData();
        }
        senhaOriginal = regCorrente.getPassword();
        operacao = Padroes.OPERACAO_ATUALIZAR;
        navigationHandler.setActivePage(nomePagina);
    }

    @Override
    public void fechar() {
        visualizandoUserActive = false;
        navigationHandler.goHome();
    }

    @Override
    public void excluir() {
        regCorrente = (TBAuthenticationUser) resultado.getRowData();
        performDestroy();
        resultado = null;
    }

    private void performDestroy() {
        try {
            userBuss.removerUser(regCorrente);
            JsfUtil.addSuccessMessage(getLabelMessages("InformacaoExcluidaComSucesso"));
        } catch (Exception e) {
            logger.error(e.getMessage());
            JsfUtil.addErrorMessage(e, getLabelMessages("ErroNaPersistencia"));
        }
    }

    @Override
    public void voltar() {
        resultado = null;
        navigationHandler.setActivePage(navigationHandler.getLastPage());
    }

    @Override
    public void novo() {
        reinicializarCampos();
        regCorrente = new TBAuthenticationUser();
        operacao = Padroes.OPERACAO_NOVO;
        navigationHandler.setActivePage(nomePagina);
    }

    public void gravar() {

        if (operacao == Padroes.OPERACAO_NOVO && regCorrente.getPassword().trim().equals("")) {
            JsfUtil.addErrorMessage(requiredMessagesHandler.getCampoObrigatorio(requiredMessagesHandler.SENHA));
            return;
        } else if (!regCorrente.getPassword().trim().equals(getConfirmPassword().trim())) {
            JsfUtil.addErrorMessage(getLabelMessages("ASenhaNaoConfere"));
            return;
        } else if (regCorrente.getPassword().trim().equals("")) {
            regCorrente.setPassword(senhaOriginal);
        } else {
            criptografarSenha();
        }

        List<TBAuthenticationGroup> grupos = new ArrayList<>();
        if (!selectedProfile.isEmpty()) {
            Iterator it = selectedProfile.iterator();
            while (it.hasNext()) {
                grupos.add(new TBAuthenticationGroup(regCorrente.getUserid(), (String) it.next()));
            }
        }
        regCorrente.setTBAuthenticationGroupCollection(grupos);
        switch (operacao) {
            case Padroes.OPERACAO_ATUALIZAR:
                try {
                    userBuss.atualizarUser(regCorrente);
                    JsfUtil.addSuccessMessage(getLabelMessages("InformacoesGravadasComSucesso"));
                } catch (Exception e) {
                    JsfUtil.addErrorMessage(e, getLabelMessages("ErroNaPersistencia"));
                    return;
                }
                break;
            case Padroes.OPERACAO_NOVO:
                try {
                    userBuss.persistirUser(regCorrente);
                    JsfUtil.addSuccessMessage(getLabelMessages("InformacoesGravadasComSucesso"));
                    operacao = Padroes.OPERACAO_ATUALIZAR;
                } catch (DataIntegrityViolationException ex) {
                    JsfUtil.addErrorMessage(getLabelMessages("EsteLoginJaExiste"));
                    return;
                } catch (Exception e) {
                    JsfUtil.addErrorMessage(e, getLabelMessages("ErroNaPersistencia"));
                    return;
                }
                break;
        }
        operacao = Padroes.OPERACAO_VISUALIZAR;
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

    public boolean isVisualizandoUserActive() {
        return visualizandoUserActive;
    }

    public List<String> getSelectedProfile() {
        selectedProfile = new ArrayList<>();
        if (regCorrente.getTBAuthenticationGroupCollection() != null) {
            Iterator iterator = regCorrente.getTBAuthenticationGroupCollection().iterator();
            while (iterator.hasNext()) {
                selectedProfile.add(((TBAuthenticationGroup) iterator.next()).getTBAuthenticationGroupPK().getGroupid());
            }
        }
        return selectedProfile;
    }

    public void setSelectedProfile(List<String> selectedProfile) {
        this.selectedProfile = selectedProfile;
    }

    private void reinicializarCampos() {
        visualizandoUserActive = false;
        confirmPassword = "";
        selectedProfile = new ArrayList<>();
    }

    private void criptografarSenha() {
//        MessageDigest md = null;
//        try {
//            md = MessageDigest.getInstance("SHA-256");
//            md.update(regCorrente.getPassword().getBytes("UTF-8"));
//        } catch (Exception e) {
//            JsfUtil.addErrorMessage(e, getLabelMessages("ErroNaCriptografia"));
//        }
//        byte[] digest = md.digest();
//        //Convertendo para Hex
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < digest.length; i++) {
//            String hex = Integer.toHexString(0xff & digest[i]);
//            if (hex.length() == 1) {
//                sb.append('0');
//            }
//            sb.append(hex);
//        }
//        regCorrente.setPassword(sb.toString());

        String senha = regCorrente.getPassword();
        String crypto = "";

        try {
            switch (Padroes.SERVER_DEFAULT) {
                case Padroes.GLASSFISH:
                    crypto = Base64EncoderPSM.criptografarSenhaGlassFish(senha, "SHA-256");
                    break;
                case Padroes.WILDFLY:
                    crypto = Base64EncoderPSM.criptografarSenhaWildFly(senha, "SHA-256");
            }
        } catch (Exception ex) {
            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
        }

        regCorrente.setPassword(crypto);
    }

    public void setRequiredMessagesHandler(RequiredMessagesHandler requiredMessagesHandler) {
        this.requiredMessagesHandler = requiredMessagesHandler;
    }

    public boolean isStRefreshAutoUserActive() {
        return stRefreshAutoUserActive;
    }

    public void setStRefreshAutoUserActive(boolean stRefreshAutoUserActive) {
        this.stRefreshAutoUserActive = stRefreshAutoUserActive;
    }

    public int getIdLastView() {
        return userActive.getIdLastView();
    }

    public void setIdLastView(int lastView) {
        userActive.setIdLastView(lastView);
        userBuss.atualizarUser(userActive);
    }

    public void gravarAutoRefresh() {
        try {
            userActive.setStRefreshAuto(stRefreshAutoUserActive);
            userBuss.atualizarUser(userActive);
            JsfUtil.addSuccessMessage(getLabelMessages("InformacoesGravadasComSucesso"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, getLabelMessages("ErroNaPersistencia"));
        }
    }

    public void setLh(LoginHandler lh) {
        this.lh = lh;
    }

    @Override
    public void editar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
