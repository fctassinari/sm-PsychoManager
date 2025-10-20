package br.com.iidh.psmng.util.chart;

/**
 *
 * @author Tassinari
 */
public class Dado {
    // id_dado;id_item;ds_titulo;st_status;id_informacao

    private int idDado;             // id do dado que será utilizado na geração do arquivo
    private int idItem;             // codigo do item
    private String titulo;          // titulo do grupo
    private boolean status;         // true = ativo, false = inativo
    private Integer idInformacao;   // id da informacao caso tenha dril down
    private int idRegistro;         // id de registro associado ao dado caso seja necessário para localizacao

    public Dado() {
        idDado = ChartControll.getIdDado();
    }

    public int getIdDado() {
        return idDado;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getTitulo() {
        return titulo;
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

    public Integer getIdInformacao() {
        return idInformacao;
    }

    public void setIdInformacao(Integer idInformacao) {
        this.idInformacao = idInformacao;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public String getLinha() {
        // id_dado;id_item;ds_titulo;st_status;id_informacao
        return getIdDado()+ ";" + getIdItem() + ";" + getTitulo() + ";" + isStatus() + ";" + getIdInformacao();
    }
}
