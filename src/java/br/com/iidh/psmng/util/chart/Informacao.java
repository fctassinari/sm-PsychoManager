package br.com.iidh.psmng.util.chart;

import java.util.LinkedHashMap;

/**
 *
 * @author Tassinari
 */
public class Informacao {

    // id_informacao;id_grupo;ds_titulo;st_status;ds_tituloChart;ds_vAxis;ds_hAxis;id_ItemCombined;id_TipoChartCombined;id_tp_chart
    private int idInformacao;           //id da informacao que será utilizado na geração do arquivo
    private int idGrupo;                //codigo do grupo
    private String titulo;              //titulo da informacao
    private boolean status;             //true = ativo, false = inativo
    private String tituloChart;         //titulo do grafico
    private String tituloEixoY;         //descrição do eixo Y
    private String tituloEixoX;         //descricao do eixo X
    private Integer idItemCombined;     //utilizado no COMBINED_CHART
    private Integer idTipoChartCombined;//utilizado no COMBINED_CHART
    private int tipoChart;              //tipo do grafico
    private int idRegistro;             // id de registro associado ao dado caso seja necessário para localizacao
    private boolean drillDown;          // true não será gravado o id do grupo, false grava o id do grupo

    private LinkedHashMap<Integer, EixoX> eixoXs;
    private LinkedHashMap<Integer, Item> itens;

    public Informacao() {
        idInformacao = ChartControll.getIdInformacao();
        eixoXs = new LinkedHashMap();
        itens = new LinkedHashMap();
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
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

    public String getTituloChart() {
        return tituloChart;
    }

    public void setTituloChart(String tituloChart) {
        this.tituloChart = tituloChart;
    }

    public String getTituloEixoY() {
        return tituloEixoY;
    }

    public void setTituloEixoY(String tituloEixoY) {
        this.tituloEixoY = tituloEixoY;
    }

    public String getTituloEixoX() {
        return tituloEixoX;
    }

    public void setTituloEixoX(String tituloEixoX) {
        this.tituloEixoX = tituloEixoX;
    }

    public Integer getIdItemCombined() {
        return idItemCombined;
    }

    public void setIdItemCombined(Integer idItemCombined) {
        this.idItemCombined = idItemCombined;
    }

    public Integer getIdTipoChartCombined() {
        return idTipoChartCombined;
    }

    public void setIdTipoChartCombined(Integer idTipoChartCombined) {
        this.idTipoChartCombined = idTipoChartCombined;
    }

    public int getTipoChart() {
        return tipoChart;
    }

    public void setTipoChart(int tipoChart) {
        this.tipoChart = tipoChart;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public int getIdInformacao() {
        return idInformacao;
    }

    public boolean isDrillDown() {
        return drillDown;
    }

    public void setDrillDown(boolean drillDown) {
        this.drillDown = drillDown;
    }

    /**
     * O idRegistro é a identificacao do registro associado e não o id do item
     *
     * @param idRegistro
     * @param item
     */
    public void setItem(Integer idRegistro, Item item) {
        item.setIdInformacao(idInformacao);
        itens.put(idRegistro, item);
    }

    public Item getItem(Integer idRegistro) {
        return itens.get(idRegistro);
    }

    /**
     * O idRegistro é a identificacao do registro associado e não o id do eixox
     *
     * @param idRegistro
     * @param eixoX
     */
    public void setEixoX(Integer idRegistro, EixoX eixoX) {
        eixoX.setIdInformacao(idInformacao);
        eixoXs.put(idRegistro, eixoX);
    }

    public EixoX getEixoX(Integer idRegistro) {
        return eixoXs.get(idRegistro);
    }

    public void setCodigoDD(int idInformacao, int idItem, int codigoAssocDado) {

        for (Item item : itens.values()) {
            if(item.getIdRegistro() == idItem)
                item.setCodigoDD(idInformacao, codigoAssocDado);
        }
    }

    public String getLinha() {
        // id_informacao;id_grupo;ds_titulo;st_status;ds_tituloChart;ds_vAxis;ds_hAxis;id_ItemCombined;id_TipoChartCombined;id_tp_chart
        return getIdInformacao()                                  + ";" + 
               (isDrillDown() == true?"null":getIdGrupo())        + ";" + 
               getTitulo()                                        + ";" + 
               isStatus()                                         + ";" + 
               getTituloChart()                                   + ";" + 
               getTituloEixoY()                                   + ";" + 
               getTituloEixoX()                                   + ";" + 
               getIdItemCombined()                                + ";" + 
               getIdTipoChartCombined()                           + ";" + 
               getTipoChart();
    }

    public LinkedHashMap<Integer, EixoX> getEixoXs() {
        return eixoXs;
    }

    public LinkedHashMap<Integer, Item> getItens() {
        return itens;
    }
    
}