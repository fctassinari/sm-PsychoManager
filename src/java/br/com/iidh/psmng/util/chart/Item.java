package br.com.iidh.psmng.util.chart;

import java.util.LinkedHashMap;

/**
 *
 * @author Tassinari
 */
public class Item {

    // id_item;id_informacao;ds_titulo;st_status;id_tp_dado
    private int idItem;      // id do item que será utilizado na geração do arquivo
    private int idInformacao;// codigo da informacao
    private String titulo;   // titulo do grupo
    private boolean status;  // true = ativo, false = inativo
    private int tipoDado;    // tipo de dado associado ao item
    private int idRegistro;  // id de registro associado ao dado caso seja necessário para localizacao

    LinkedHashMap<Integer, Dado> dados;

    public Item() {
        idItem = ChartControll.getIdItem();
        dados = new LinkedHashMap();
    }

    public String getTitulo() {
        return titulo;
    }

    public int getIdInformacao() {
        return idInformacao;
    }

    public void setIdInformacao(int idInformacao) {
        this.idInformacao = idInformacao;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTipoDado() {
        return tipoDado;
    }

    public void setTipoDado(int tipoDado) {
        this.tipoDado = tipoDado;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public int getIdItem() {
        return idItem;
    }

    /**
     * O idRegistro é a identificacao do registro associado e não o id do dado
     *
     * @param idRegistro
     * @param dado
     */
    public void setDado(Integer idRegistro, Dado dado) {
        dado.setIdItem(idItem);
        dados.put(idRegistro, dado);
    }

    public Dado getDado(Integer idRegistro) {
        return dados.get(idRegistro);
    }

    public void setCodigoDD(int idInformacao, int codigoAssocDado) {

        for (Dado dado : dados.values()) {
            if (dado.getIdRegistro() == codigoAssocDado) {
                dado.setIdInformacao(idInformacao);
            }
        }
    }

    public LinkedHashMap<Integer, Dado> getDados() {
        return dados;
    }

    public String getLinha() {
        // id_item;id_informacao;ds_titulo;st_status;id_tp_dado
        return getIdItem() + ";" + getIdInformacao() + ";" + getTitulo() + ";" + isStatus() + ";" + getTipoDado();
    }
}
