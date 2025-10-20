package br.com.iidh.psmng.util.chart;

/**
 *
 * @author Tassinari
 */
public class ChartControll {

    public static int idGrupo;
    public static int idInformacao;
    public static int idItem;
    public static int idDado;
    public static int idEixo;

    public static int getIdGrupo() {
        return ++idGrupo;
    }

    public static int getIdInformacao() {
        return ++idInformacao;
    }

    public static int getIdItem() {
        return ++idItem;
    }

    public static int getIdDado() {
        return ++idDado;
    }

    public static int getIdEixo() {
        return ++idEixo;
    }

}
