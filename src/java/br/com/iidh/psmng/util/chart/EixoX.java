package br.com.iidh.psmng.util.chart;

/**
 *
 * @author Tassinari
 */
public class EixoX {
    // id_eixox;id_informacao;ds_titulo

    private int idEixoX;             // id do dado que será utilizado na geração do arquivo
    private int idInformacao;        // codigo da informacao
    private String titulo;           // titulo do grupo

    public EixoX() {
        idEixoX = ChartControll.getIdEixo();
    }

    public int getIdInformacao() {
        return idInformacao;
    }

    public void setIdInformacao(int idInformacao) {
        this.idInformacao = idInformacao;
    }

    public int getIdEixoX() {
        return idEixoX;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLinha() {
        // id_eixox;id_informacao;ds_titulo
        return getIdEixoX() + ";" + getIdInformacao() + ";" + getTitulo();
    }
}
