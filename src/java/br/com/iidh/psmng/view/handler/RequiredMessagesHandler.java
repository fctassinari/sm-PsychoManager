/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.iidh.psmng.view.handler;

import java.io.Serializable;
import java.text.MessageFormat;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Tassinari
 */
@ManagedBean(name = "requiredMessagesHandler", eager = true)
@SessionScoped
public class RequiredMessagesHandler extends AbstractHandlerFacade implements Serializable {

    // Tela de login -> index.xhtml
    public final int USUARIO = 1;
    public final int SENHA = 2;
    
    // Tela de paciente - paciente.xhtml
    public final int NOME = 3;
    public final int ENDERECO = 4;
    public final int BAIRRO = 5;
    public final int CEP = 6;
    
    // Tela Cadastro de senha - senha.xhtml
    public final int CONFIRMAR = 7;
    
    // Tela Cadastro de usuários - user.xhtml
    public final int LOGIN = 8;
    
    /**
     * Creates a new instance of RequiredMessagesHandler
     */
    public RequiredMessagesHandler() {
        super(RequiredMessagesHandler.class);
    }

    public String getCampoObrigatorio(int param) {
        MessageFormat mf = new MessageFormat(getLabelMessages("CampoObrigatorio"));
        String msg = null;
        switch (param) {
            case USUARIO:
                msg = mf.format(new Object[]{getLabelMessages("Usuario")});
                break;
            case SENHA:
                msg = mf.format(new Object[]{getLabelMessages("Senha")});
                break;
            case NOME:
                msg = mf.format(new Object[]{getLabelMessages("Nome")});
                break;
            case ENDERECO:
                msg = mf.format(new Object[]{getLabelMessages("Endereco")});
                break;
            case BAIRRO:
                msg = mf.format(new Object[]{getLabelMessages("Bairro")});
                break;
            case CEP:
                msg = mf.format(new Object[]{getLabelMessages("Cep")});
                break;
            case CONFIRMAR:
                msg = mf.format(new Object[]{getLabelMessages("Confirmar")});
                break;
            case LOGIN:
                msg = mf.format(new Object[]{getLabelMessages("Login")});
                break;
        }
        return msg;
    }

    public int getUSUARIO() {
        return USUARIO;
    }

    public int getSENHA() {
        return SENHA;
    }

    public int getNOME() {
        return NOME;
    }

    public int getENDERECO() {
        return ENDERECO;
    }

    public int getBAIRRO() {
        return BAIRRO;
    }

    public int getCEP() {
        return CEP;
    }

    public int getCONFIRMAR() {
        return CONFIRMAR;
    }

    public int getLOGIN() {
        return LOGIN;
    }
    
}
