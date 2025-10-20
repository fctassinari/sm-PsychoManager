/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.iidh.psmng.util.chinese;

import java.util.GregorianCalendar;

/**
 *
 * @author tassinari
 */
public interface CronoacupunturaInt {

    public GregorianCalendar getDataGregoriana();

    public void setDataGregoriana(GregorianCalendar dataGregoriana);

    public ChineseCalendar getDataChinesa();

    public void setDataChinesa(ChineseCalendar dataChinesa);

    /**
     *
     * @param tronco numero do Tronco Celeste
     * @param info Cronoacupuntura.NOME, Cronoacupuntura.ELEMENTO,
     * Cronoacupuntura.POLARIDADE, Cronoacupuntura.IDEOGRAMA,
     * Cronoacupuntura..HOROSCOPO
     * @return
     */
    public String getInfoTC(int tronco, int info);

    /**
     *
     * @param ramo numero do Ramo Terrestre
     * @param info Cronoacupuntura.NOME, Cronoacupuntura.ELEMENTO,
     * Cronoacupuntura.POLARIDADE, Cronoacupuntura.IDEOGRAMA
     * @return
     */
    public String getInfoRT(int ramo, int info);

    public int getDiaChines();

    public int getMesChines();

    public int getAnoChines();

    public int getDiaDaSemanaChines();

    public int getDiaDoAnoChines();

    public int getDiaOcidental();

    public int getMesOcidental();

    public int getAnoOcidental();

    /**
     * Dia da semana no calendário Gregoriano começa no domingo = 1
     *
     * @return
     */
    public int getDiaDaSemanaOcidental();

    public String getDescricaoSemana(int dia);

    public int getDiaDoAnoOcidental();

    public int getTroncoAno();

    public int getRamoAno();

    public int getTroncoMes();

    public int getRamoMes();

    public double getJulianDate();

    public int getPosicaoDiaTabSexagesimal();

    /**
     * O dia da semana juliano começa na segunda = 1
     *
     * @return
     */
    public int getDayWeekJuliano();

    /**
     *
     * @param info Cronoacupuntura.NOME, Cronoacupuntura.ELEMENTO,
     * Cronoacupuntura.POLARIDADE, Cronoacupuntura.IDEOGRAMA
     * @return
     */
    public String getInfoTCAno(int info);

    /**
     *
     * @param info Cronoacupuntura.NOME, Cronoacupuntura.ELEMENTO,
     * Cronoacupuntura.POLARIDADE, Cronoacupuntura.IDEOGRAMA
     * @return
     */
    public String getInfoTCMes(int info);

    /**
     *
     * @param info Cronoacupuntura.NOME, Cronoacupuntura.ELEMENTO,
     * Cronoacupuntura.POLARIDADE, Cronoacupuntura.IDEOGRAMA
     * @return
     */
    public String getInfoRTAno(int info);

    /**
     *
     * @param info Cronoacupuntura.NOME, Cronoacupuntura.ELEMENTO,
     * Cronoacupuntura.POLARIDADE, Cronoacupuntura.IDEOGRAMA
     * @return
     */
    public String getInfoRTMes(int info);

    /**
     * Para encontrar o TC e RT do mês chines deve-se seguir a regra abaixo:
     * Meses que iniciam em: Tronco 1,6 Tronco 2,7 Tronco 3,8 Tronco 4,9 Tronco
     * 5,10 Primeiro mês será T3, R3 T5, R3 T7, R3 T9, R3 T1, R3 Segundo mês
     * será T4, R4 T6, R4 T8, R4 T10, R4 T2, R4 assim por diante....
     *
     * @param tcAno tronco celete do ano
     * @param mesChines mes no calendário chines
     * @return TC e RT do mês
     */
    public String getTcRtMesChines(int tcAno, int mesChines);

    public GregorianCalendar getDataAnoNovoChinesOnGregorianCalendar();

    /**
     *
     * @param info Cronoacupuntura.NOME, Cronoacupuntura.ELEMENTO,
     * Cronoacupuntura.POLARIDADE, Cronoacupuntura.IDEOGRAMA
     * @return
     */
    public String getInfoRTDia(int info);

    /**
     *
     * @param info Cronoacupuntura.NOME, Cronoacupuntura.ELEMENTO,
     * Cronoacupuntura.POLARIDADE, Cronoacupuntura.IDEOGRAMA
     * @return
     */
    public String getInfoTCDia(int info);

    public int getTroncoDia();

    public int getRamoDia();

    public int getRamoHora();

    /**
     *
     * @param info Cronoacupuntura.NOME, Cronoacupuntura.ELEMENTO,
     * Cronoacupuntura.POLARIDADE, Cronoacupuntura.IDEOGRAMA
     * @return
     */
    public String getInfoRTHora(int info);

    public int getRTHora(String range);

    public String getZangFuHora(String range);
}
