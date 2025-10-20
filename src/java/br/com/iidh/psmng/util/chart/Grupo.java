package br.com.iidh.psmng.util.chart;

import java.util.LinkedHashMap;

/**
 *
 * @author Tassinari
 */
public class Grupo {
    
    // id_grupo;ds_titulo;st_status
    
    private int idGrupo;    // id do grupo que será utilizado na geração do arquivo
    private String titulo;  // titulo do grupo
    private boolean status; // true = ativo, false = inativo
    private int idRegistro; // id de registro associado ao dado caso seja necessário para localizacao
    private int idGrupoExistente = 0; 
    
    
    private LinkedHashMap<Integer,Informacao> informacoes;
    
    public Grupo() {
        idGrupo = ChartControll.getIdGrupo();
        informacoes = new LinkedHashMap();
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isStatus() {
        return status;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    /**
     * O idRegistro é a identificacao do registro associado e não o id da informacao 
     * @param idRegistro
     * @param info 
     */
    public void setInformacao(Integer idRegistro, Informacao info){
        info.setIdGrupo(idGrupo);
        informacoes.put(idRegistro, info);
    }
    
    public Informacao getInformacao(Integer idRegistro){
        return informacoes.get(idRegistro);
    }

    public LinkedHashMap<Integer, Informacao> getInformacoes() {
        return informacoes;
    }
    
    public String getLinha(){
        // id_grupo;ds_titulo;st_status
        if(idGrupoExistente != 0)
            return null;
        else
            return getIdGrupo() + ";" + getTitulo() + ";" + isStatus();
    }

    public int getIdGrupoExistente() {
        return idGrupoExistente;
    }

    public void setIdGrupoExistente(int idGrupoExistente) {
        this.idGrupoExistente = idGrupoExistente;
    }
}
