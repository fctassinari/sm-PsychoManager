/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.iidh.psmng.util.chinese;

import static br.com.iidh.psmng.util.chinese.JulianDate.toJulian;
import java.util.GregorianCalendar;

/**
 *
 * @author tassinari
 */
public class Cronoacupuntura implements CronoacupunturaInt {

    public static final int NOME = 0;
    public static final int ELEMENTO = 1;
    public static final int POLARIDADE = 2;
    public static final int IDEOGRAMA = 3;
    public static final int HOROSCOPO = 4;

    public static final int PONTO = 1;
    public static final int TRIGRAMA = 2;

    public static final String HORA_23_01 = "23-01";
    public static final String HORA_01_03 = "01-03";
    public static final String HORA_03_05 = "03-05";
    public static final String HORA_05_07 = "05-07";
    public static final String HORA_07_09 = "07-09";
    public static final String HORA_09_11 = "09-11";
    public static final String HORA_11_13 = "11-13";
    public static final String HORA_13_15 = "13-15";
    public static final String HORA_15_17 = "15-17";
    public static final String HORA_17_19 = "17-19";
    public static final String HORA_19_21 = "19-21";
    public static final String HORA_21_23 = "21-23";

    private static String[][] tc = {
        {"Jiǎ", "Madeira", "Yang", "甲"}, // 1
        {"Yǐ", "Madeira", "Yin", "乙"}, // 2
        {"Bǐng", "Fogo", "Yang", "丙"}, // 3
        {"Dīng", "Fogo", "Yin", "丁"}, // 4
        {"Wù", "Terra", "Yang", "戊"}, // 5
        {"Jǐ", "Terra", "Yin", "己"}, // 6
        {"Gēng", "Metal", "Yang", "庚"}, // 7
        {"Xīn", "Metal", "Yin", "辛"}, // 8
        {"Rén", "Água", "Yang", "壬"}, // 9
        {"Guǐ", "Água", "Yin", "戊"} // 10
    };

    public static String[][] rt = {
        {"Zi", "Água", "Yang", "", "Rato"}, // 1
        {"Chou", "Terra", "Yin", "", "Boi/Búfalo"}, // 2
        {"Yin", "Madeira", "Yang", "", "Tigre"}, // 3
        {"Mao", "Madeira", "Yin", "", "Coelho"}, // 4
        {"hén", "Terra", "Yang", "", "Dragão"}, // 5
        {"Si", "Fogo", "Yin", "", "Serpente"}, // 6
        {"Wu", "Fogo", "Yang", "", "Cavalo"}, // 7
        {"Wèi", "Terra", "Yin", "", "Cabra"}, // 8
        {"Shen", "Metal", "Yang", "", "Macaco"}, // 9
        {"You", "Metal", "Yin", "", "Galo"}, // 10
        {"Xu", "Terra", "Yang", "", "Cachorro"}, // 11
        {"Hài", "Água", "Yin", "", "Porco"} // 12
    };

    public static String[][] mes = {
        {"T3,R3", "T5,R3", "T7,R3", "T9,R3", "T1,R3"},
        {"T4,R4", "T6,R4", "T8,R4", "T10,R4", "T2,R4"},
        {"T5,R5", "T7,R5", "T9,R5", "T1,R5", "T3,R5"},
        {"T6,R6", "T8,R6", "T10,R6", "T2,R6", "T4,R6"},
        {"T7,R7", "T9,R7", "T1,R7", "T3,R7", "T5,R7"},
        {"T8,R8", "T10,R8", "T2,R8", "T4,R8", "T6,R8"},
        {"T9,R9", "T1,R9", "T3,R9", "T5,R9", "T7,R9"},
        {"T10,R10", "T2,R10", "T4,R10", "T6,R10", "T8,R10"},
        {"T1,R11", "T3,R11", "T5,R11", "T7,R11", "T9,R11"},
        {"T2,R12", "T4,R12", "T6,R12", "T8,R12", "T10,R12"},
        {"T3,R1", "T5,R1", "T7,R1", "T9,R1", "T1,R1"},
        {"T4,R2", "T6,R2", "T8,R2", "T10,R2", "T2,R2"}
    };

    //{hora, ramo, Zang Fu, tronco}
    public static final String[][] hora = {
        {HORA_23_01, "1", "VB","9"},
        {HORA_01_03, "2", "F","10"},
        {HORA_03_05, "3", "P","1"},
        {HORA_05_07, "4", "IG","2"},
        {HORA_07_09, "5", "E","3"},
        {HORA_09_11, "6", "BP","4"},
        {HORA_11_13, "7", "C","5"},
        {HORA_13_15, "8", "ID","6"},
        {HORA_15_17, "9", "BX","7"},
        {HORA_17_19, "10", "R","8"},
        {HORA_19_21, "11", "PC","9"},
        {HORA_21_23, "12", "TA","10"}
    };

    private int troncoAno;
    private int ramoAno;
    private int troncoMes;
    private int ramoMes;
    private int troncoDia;
    private int ramoDia;
    private int ramoHora;

    private double julianDate;
    private int posicaoDiaTabSexagesimal;
    private int dayWeekJuliano;
    private GregorianCalendar dataAnoNovoChinesOnGregorianCalendar;
    private GregorianCalendar dataGregoriana;
    private ChineseCalendar dataChinesa;

    public Cronoacupuntura(String data, String hora) {

        dataGregoriana = new GregorianCalendar(Integer.parseInt(data.substring(6, 10)),
                Integer.parseInt(data.substring(3, 5)) - 1,
                Integer.parseInt(data.substring(0, 2)));

        dataChinesa = new ChineseCalendar(dataGregoriana.get(GregorianCalendar.YEAR),
                dataGregoriana.get(GregorianCalendar.MONTH) + 1,
                dataGregoriana.get(GregorianCalendar.DAY_OF_MONTH));

        dataAnoNovoChinesOnGregorianCalendar = dataChinesa.getGcAnoNovoChinesCalendarioOcidental();
        julianDate = toJulian(new int[]{dataGregoriana.get(GregorianCalendar.YEAR), dataGregoriana.get(GregorianCalendar.MONTH) + 1, dataGregoriana.get(GregorianCalendar.DATE)});

        posicaoDiaTabSexagesimal = (int) (1 + ((julianDate - 11) % 60));

        //Ano calendario chines
        if (dataGregoriana.before(dataAnoNovoChinesOnGregorianCalendar)) {
            troncoAno = 1 + (((dataGregoriana.get(GregorianCalendar.YEAR) - 1) + 6) % 10);
            ramoAno = 1 + (((dataGregoriana.get(GregorianCalendar.YEAR) - 1) + 8) % 12);

        } else {
            troncoAno = 1 + ((dataGregoriana.get(GregorianCalendar.YEAR) + 6) % 10);
            ramoAno = 1 + ((dataGregoriana.get(GregorianCalendar.YEAR) + 8) % 12);
        }

        //Mes calendario chines
        String tcrtMesNasc = getTcRtMesChines(troncoAno, dataChinesa.getChineseMonth());
        String[] result = tcrtMesNasc.split(",");
        troncoMes = Integer.parseInt(result[0].substring(1));
        ramoMes = Integer.parseInt(result[1].substring(1));

        //Dia calendario chines
        troncoDia = (int) (1 + ((posicaoDiaTabSexagesimal - 1) % 10));
        ramoDia = (int) (1 + ((posicaoDiaTabSexagesimal - 1) % 12));
        dayWeekJuliano = (int) ((julianDate + 1) % 7);

        //Hora 
        ramoHora = getRTHora(hora);

    }

    @Override
    public GregorianCalendar getDataGregoriana() {
        return dataGregoriana;
    }

    @Override
    public void setDataGregoriana(GregorianCalendar dataGregoriana) {
        this.dataGregoriana = dataGregoriana;
    }

    @Override
    public ChineseCalendar getDataChinesa() {
        return dataChinesa;
    }

    @Override
    public void setDataChinesa(ChineseCalendar dataChinesa) {
        this.dataChinesa = dataChinesa;
    }

    @Override
    public String getInfoTC(int tronco, int info) {
        return tc[tronco - 1][info];
    }

    @Override
    public String getInfoRT(int ramo, int info) {
        return rt[ramo - 1][info];
    }

    @Override
    public int getDiaChines() {
        return dataChinesa.getChineseDate();
    }

    @Override
    public int getMesChines() {
        return dataChinesa.getChineseMonth();
    }

    @Override
    public int getAnoChines() {
        return dataChinesa.getChineseYear();
    }

    @Override
    public int getDiaDaSemanaChines() {
        return dataChinesa.getDayOfWeek();
    }

    @Override
    public int getDiaDoAnoChines() {
        return dataChinesa.getDayOfYear();
    }

    @Override
    public int getDiaOcidental() {
        return dataGregoriana.get(GregorianCalendar.DATE);
    }

    @Override
    public int getMesOcidental() {
        return dataGregoriana.get(GregorianCalendar.MONTH) + 1;
    }

    @Override
    public int getAnoOcidental() {
        return dataGregoriana.get(GregorianCalendar.YEAR);
    }

    @Override
    public int getDiaDaSemanaOcidental() {
        return dataGregoriana.get(GregorianCalendar.DAY_OF_WEEK);
    }

    @Override
    public String getDescricaoSemana(int dia) {
        String ret = "";

        switch (dia) {
            case 1:
                ret = "Domingo";
                break;
            case 2:
                ret = "Segunda";
                break;
            case 3:
                ret = "Terça";
                break;
            case 4:
                ret = "Quarta";
                break;
            case 5:
                ret = "Quinta";
                break;
            case 6:
                ret = "Sexta";
                break;
            case 7:
                ret = "Sabado";
                break;
        }
        return ret;
    }

    @Override
    public int getDiaDoAnoOcidental() {
        return dataGregoriana.get(GregorianCalendar.DAY_OF_YEAR);
    }

    @Override
    public int getTroncoAno() {
        return troncoAno;
    }

    @Override
    public int getRamoAno() {
        return ramoAno;
    }

    @Override
    public int getTroncoMes() {
        return troncoMes;
    }

    @Override
    public int getRamoMes() {
        return ramoMes;
    }

    @Override
    public double getJulianDate() {
        return julianDate;
    }

    @Override
    public int getPosicaoDiaTabSexagesimal() {
        return posicaoDiaTabSexagesimal;
    }

    /**
     * O dia da semana juliano começa na segunda = 1
     *
     * @return
     */
    @Override
    public int getDayWeekJuliano() {
        return dayWeekJuliano;
    }

    @Override
    public String getInfoTCAno(int info) {
        return getInfoTC(troncoAno, info);
    }

    @Override
    public String getInfoTCMes(int info) {
        return getInfoTC(troncoMes, info);
    }

    @Override
    public String getInfoRTAno(int info) {
        return getInfoRT(ramoAno, info);
    }

    @Override
    public String getInfoRTMes(int info) {
        return getInfoRT(ramoMes, info);
    }

    @Override
    public String getTcRtMesChines(int tcAno, int mesChines) {

        String ret = "";
        if(mesChines < 0)
            mesChines = mesChines*-1;
        switch (tcAno) {
            case 1:
            case 6:
                ret = mes[mesChines - 1][0];
                break;
            case 2:
            case 7:
                ret = mes[mesChines - 1][1];
                break;
            case 3:
            case 8:
                ret = mes[mesChines - 1][2];
                break;
            case 4:
            case 9:
                ret = mes[mesChines - 1][3];
                break;
            case 5:
            case 10:
                ret = mes[mesChines - 1][4];
                break;

        }
        return ret;
    }

    @Override
    public GregorianCalendar getDataAnoNovoChinesOnGregorianCalendar() {
        return dataAnoNovoChinesOnGregorianCalendar;
    }

    @Override
    public String getInfoRTDia(int info) {
        return getInfoRT(ramoDia, info);
    }

    @Override
    public String getInfoTCDia(int info) {
        return getInfoTC(troncoDia, info);
    }

    @Override
    public int getTroncoDia() {
        return troncoDia;
    }

    @Override
    public int getRamoDia() {
        return ramoDia;
    }

    /**
     *
     * @param info Cronoacupuntura.NOME, Cronoacupuntura.ELEMENTO,
     * Cronoacupuntura.POLARIDADE, Cronoacupuntura.IDEOGRAMA
     * @return
     */
    @Override
    public String getInfoRTHora(int info) {
        return getInfoRT(ramoHora, info);
    }

    @Override
    public int getRTHora(String range) {
        int ret = 0;

        for (String[] hora1 : hora) {
            if (range.equals(hora1[0])) {
                ret = Integer.parseInt(hora1[1]);
            }
        }
        return ret;
    }

    @Override
    public int getRamoHora() {
        return ramoHora;
    }

    @Override
    public String getZangFuHora(String range) {
        String ret = "";

        for (String[] hora1 : hora) {
            if (range.equals(hora1[0])) {
                ret = hora1[2];
            }
        }
        return ret;
    }

}
