package br.com.iidh.psmng.util.chinese;

import static br.com.iidh.psmng.util.chinese.JulianDate.toJulian;
import java.util.GregorianCalendar;

/**
 *
 * @author tassinari
 */
public class Crono_v2 {

    public static int NOME = 0;
    public static int ELEMENTO = 1;
    public static int POLARIDADE = 2;
    public static int IDEOGRAMA = 3;
    public static int HOROSCOPO = 3;

    public static int PONTO = 1;
    public static int TRIGRAMA = 2;
        
    public static String HORA_23_01 = "23-01";
    public static String HORA_01_03 = "01-03";
    public static String HORA_03_05 = "03-05";
    public static String HORA_05_07 = "05-07";
    public static String HORA_07_09 = "07-09";
    public static String HORA_09_11 = "09-11";
    public static String HORA_11_13 = "11-13";
    public static String HORA_13_15 = "13-15";
    public static String HORA_15_17 = "15-17";
    public static String HORA_17_19 = "17-19";
    public static String HORA_19_21 = "19-21";
    public static String HORA_21_23 = "21-23";

    private int troncoAno = 0;
    private int ramoAno = 0;
    private int troncoMes = 0;
    private int ramoMes = 0;
    private double julianDate = 0.0;
    private int posicaoDiaTabSexagesimal = 0;
    private int troncoDiaCalendario = 0;
    private int ramoDiaCalendario = 0;
    private int dayWeekJuliano = 0;
    private int troncoDiaCurso = 0;
    private int ramoDiaCurso = 0;
    private int ramoHora = 0;
    private int vasoMaravilhoso = 0;
    private int diaChines = 0;
    private int mesChines = 0;
    private int anoChines = 0;
    private int diaDaSemanaChines = 0;
    private int diaDoAnoChines = 0;
    private int diaOcidental = 0;
    private int mesOcidental = 0;
    private int anoOcidental = 0;
    private int diaDaSemanaOcidental = 0;
    private int diaDoAnoOcidental = 0;
    
    private String hr = "";
    private GregorianCalendar dataAnoNovoChinesOnGregorianCalendar;
    
    public Crono_v2(GregorianCalendar data, String hora) {
        hr = hora;
        ChineseCalendar cc = new ChineseCalendar(data.get(GregorianCalendar.YEAR),
                data.get(GregorianCalendar.MONTH) + 1,
                data.get(GregorianCalendar.DAY_OF_MONTH));

        dataAnoNovoChinesOnGregorianCalendar = cc.getGcAnoNovoChinesCalendarioOcidental();
        
        
        julianDate = toJulian(new int[]{data.get(GregorianCalendar.YEAR), data.get(GregorianCalendar.MONTH) + 1, data.get(GregorianCalendar.DATE)});

        //Ano
        
        if(data.before(dataAnoNovoChinesOnGregorianCalendar)){
            troncoAno = 1 + (((data.get(GregorianCalendar.YEAR)-1) + 6) % 10);
            ramoAno = 1 + (((data.get(GregorianCalendar.YEAR)-1) + 8) % 12);
            
        }
        else{
            troncoAno = 1 + ((data.get(GregorianCalendar.YEAR) + 6) % 10);
            ramoAno = 1 + ((data.get(GregorianCalendar.YEAR) + 8) % 12);
        }
        
        //Mes
        String tcrtMesNasc = getTcRtMesChines(troncoAno, cc.getChineseMonth());
        String[] result = tcrtMesNasc.split(",");
        troncoMes = Integer.parseInt(result[0].substring(1));
        ramoMes = Integer.parseInt(result[1].substring(1));

        //Dia calendario
        posicaoDiaTabSexagesimal = (int) (1 + ((julianDate - 11) % 60));
        troncoDiaCalendario = (int) (1 + ((posicaoDiaTabSexagesimal - 1) % 10));
        ramoDiaCalendario = (int) (1 + ((posicaoDiaTabSexagesimal - 1) % 12));
        dayWeekJuliano = (int) ((julianDate + 1) % 7);

        //Dia professor
        String diaNascProf = dia[cc.getChineseDate() - 1];
        result = diaNascProf.split(",");
        troncoDiaCurso = Integer.parseInt(result[0].substring(1));
        ramoDiaCurso = Integer.parseInt(result[1].substring(1));

        //Hora
        ramoHora = getRTHora(hora);

        //Calculo do Vaso Maravilhoso
        vasoMaravilhoso = (Integer.parseInt(subsTCDia[troncoDiaCurso - 1]) + Integer.parseInt(subsRTDia[ramoDiaCurso - 1]) + Integer.parseInt(subsRTHora[ramoHora - 1])) % (cc.getChineseDate() % 2 == 0 ? 6 : 9);
        if (vasoMaravilhoso == 0 || vasoMaravilhoso == 9) {
            vasoMaravilhoso = 1;
        }

        diaChines = cc.getChineseDate();
        mesChines = cc.getChineseMonth();
        anoChines = cc.getChineseYear();
        diaDaSemanaChines = cc.getDayOfWeek();
        diaDoAnoChines = cc.getDayOfYear();
        
        diaOcidental = data.get(GregorianCalendar.DATE);
        mesOcidental = data.get(GregorianCalendar.MONTH);
        anoOcidental = data.get(GregorianCalendar.YEAR);
        diaDaSemanaOcidental = data.get(GregorianCalendar.DAY_OF_WEEK);
        diaDoAnoOcidental = data.get(GregorianCalendar.DAY_OF_YEAR);

    }

    /**
     * Para encontrar o TC e RT do mês chines deve-se seguir a regra abaixo:
     * Meses que iniciam em: Tronco 1,6 Tronco 2,7 Tronco 3,8 Tronco 4,9 Tronco
     * 5,10 Primeiro mês será T3, R3 T5, R3 T7, R3 T9, R3 T1, R3 Segundo mês
     * será T4, R4 T6, R4 T8, R4 T10, R4 T2, R4 assim por diante....
     *
     * @param tcAno tronco celete do ano
     * @return TC e RT do mês
     */
    private String getTcRtMesChines(int tcAno, int mesChines) {

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

    private int getRTHora(String range) {
        int ret = 0;

        for (int i = 0; i < hora.length; i++) {
            if (range.equals(hora[i][0])) {
                ret = Integer.parseInt(hora[i][1]);
            }
        }
        return ret;
    }

    public String getZangFuHora(String range) {
        String ret = "";

        for (int i = 0; i < hora.length; i++) {
            if (range.equals(hora[i][0])) {
                ret = hora[i][2];
            }
        }
        return ret;
    }


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

    /**
     *
     * @param info 0 = Nome, 1 = Elemento, 2 = Polaridade, 3 - Ideograma
     * @return
     */
    public String getInfoTCAno(int info){
        return getInfoTC(troncoAno,info);
    }
 
    /**
     *
     * @param info 0 = Nome, 1 = Elemento, 2 = Polaridade, 3 - Ideograma
     * @return
     */
    public String getInfoTCMes(int info){
        return getInfoTC(troncoMes,info);
    }
 
    /**
     *
     * @param info 0 = Nome, 1 = Elemento, 2 = Polaridade, 3 - Ideograma
     * @return
     */
    public String getInfoTCDiaCurso(int info){
        return getInfoTC(troncoDiaCurso,info);
    }
 
    /**
     *
     * @param info 0 = Nome, 1 = Elemento, 2 = Polaridade, 3 - Ideograma
     * @return
     */
    public String getInfoTCDiaCalendario(int info){
        return getInfoTC(troncoDiaCalendario,info);
    }

    /**
     *
     * @param tronco numero do Tronco Celeste
     * @param info 0 = Nome, 1 = Elemento, 2 = Polaridade, 3 - Ideograma, 4 - horoscopo
     * @return
     */
    private String getInfoTC(int tronco, int info) {
        return tc[tronco - 1][info];
    }

    private static String[][] rt = {
        {"Zi", "Água", "Yang","","Rato"}, // 1
        {"Chou", "Terra", "Yin","","Boi/Búfalo"}, // 2
        {"Yin", "Madeira", "Yang","","Tigre"}, // 3
        {"Mao", "Madeira", "Yin","","Coelho"}, // 4
        {"Chén", "Terra", "Yang","","Dragão"}, // 5
        {"Si", "Fogo", "Yin","","Serpente"}, // 6
        {"Wu", "Fogo", "Yang","","Cavalo"}, // 7
        {"Wèi", "Terra", "Yin","","Cabra"}, // 8
        {"Shen", "Metal", "Yang","","Macaco"}, // 9
        {"You", "Metal", "Yin","","Galo"}, // 10
        {"Xu", "Terra", "Yang","","Cachorro"}, // 11
        {"Hài", "Água", "Yin","","Porco"} // 12
    };

    /**
     *
     * @param info 0 = Nome, 1 = Elemento, 2 = Polaridade, 3 - Ideograma
     * @return
     */
    public String getInfoRTAno(int info){
        return getInfoRT(ramoAno,info);
    }
    
    /**
     *
     * @param info 0 = Nome, 1 = Elemento, 2 = Polaridade, 3 - Ideograma
     * @return
     */
    public String getInfoRTMes(int info){
        return getInfoRT(ramoMes,info);
    }
    
    /**
     *
     * @param info 0 = Nome, 1 = Elemento, 2 = Polaridade, 3 - Ideograma
     * @return
     */
    public String getInfoRTDiaCurso(int info){
        return getInfoRT(ramoDiaCurso,info);
    }
    
    /**
     *
     * @param info 0 = Nome, 1 = Elemento, 2 = Polaridade, 3 - Ideograma
     * @return
     */
    public String getInfoRTDiaCalendario(int info){
        return getInfoRT(ramoDiaCalendario,info);
    }

    /**
     *
     * @param info 0 = Nome, 1 = Elemento, 2 = Polaridade, 3 - Ideograma
     * @return
     */
    public String getInfoRTHora(int info){
        return getInfoRT(ramoHora,info);
    }
    
    /**
     *
     * @param ramo numero do Ramo Terrestre
     * @param info 0 = Nome, 1 = Elemento, 2 = Polaridade, 3 - Ideograma
     * @return
     */
    private String getInfoRT(int ramo, int info) {
        return rt[ramo - 1][info];
    }

    private static String[][] mes = {
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

    private static String[] dia = {
        "T1,R1",
        "T2,R2",
        "T3,R3",
        "T4,R4",
        "T5,R5",
        "T6,R6",
        "T7,R7",
        "T8,R8",
        "T9,R9",
        "T10,R10",
        "T1,R11",
        "T2,R12",
        "T3,R1",
        "T4,R2",
        "T5,R3",
        "T6,R4",
        "T7,R5",
        "T8,R6",
        "T9,R7",
        "T10,R8",
        "T1,R9",
        "T2,R10",
        "T3,R11",
        "T4,R12",
        "T5,R1",
        "T6,R2",
        "T7,R3",
        "T8,R4",
        "T9,R5",
        "T10,R6",
        "T1,R7"
    };

    private static String[][] hora = {
        {"23-01", "1", "VB"},
        {"01-03", "2", "F"},
        {"03-05", "3", "P"},
        {"05-07", "4", "IG"},
        {"07-09", "5", "E"},
        {"09-11", "6", "BP"},
        {"11-13", "7", "C"},
        {"13-15", "8", "ID"},
        {"15-17", "9", "BX"},
        {"17-19", "10", "R"},
        {"19-21", "11", "PC"},
        {"21-23", "12", "TA"}
    };

    private static String[] subsTCDia = {
        "10",
        "9",
        "8",
        "7",
        "7",
        "10",
        "9",
        "7",
        "8",
        "7"
    };

    private static String[] subsRTDia = {
        "7",
        "10",
        "8",
        "8",
        "10",
        "7",
        "7",
        "10",
        "9",
        "9",
        "10",
        "7"
    };

    private static String[] subsRTHora = {
        "9",
        "8",
        "7",
        "6",
        "5",
        "4",
        "9",
        "8",
        "7",
        "6",
        "5",
        "4"
    };

    private static String[][] vasosMaravilhosos = {
        {"Chong Mai", "BP4 (Gongsun)", "Li", "3"},
        {"Yin Wei Mai", "PC6 (Neiguan)", "Gen ", "7"},
        {"Du Mai", "ID3 (Houxi)", "Qian", "1"},
        {"Yang Qiao Mai", "B62 (Shenmai)", "Zhen", "4"},
        {"Dai Mai", "VB41 (Zulinqi)", "Kan", "6"},
        {"Yang Wei Mai", "TA5 (Waiguan)", "Dui", "2"},
        {"Ren Mai", "P7 (Lieque)", "Kun", "8"},
        {"Yin Qiao Mai", "R6 (Zhaohai)", "Xun", "5"}
    };
    
    /**
     * 
     * @param info  0 = Nome, 1 = Ponto, 2 = Trigrama
     * @return 
     */
    public String getCampoVM(int info) {
        String ret = "";

        for (int i = 0; i < vasosMaravilhosos.length; i++) {
            if (String.valueOf(vasoMaravilhoso).equals(vasosMaravilhosos[i][3])) {
                ret = vasosMaravilhosos[i][info];
            }
        }
        return ret;
    }

    public int getTroncoAno() {
        return troncoAno;
    }

    public int getRamoAno() {
        return ramoAno;
    }

    public int getTroncoMes() {
        return troncoMes;
    }

    public int getRamoMes() {
        return ramoMes;
    }

    public double getJulianDate() {
        return julianDate;
    }

    public int getPosicaoDiaTabSexagesimal() {
        return posicaoDiaTabSexagesimal;
    }

    public int getTroncoDiaCalendario() {
        return troncoDiaCalendario;
    }

    public int getRamoDiaCalendario() {
        return ramoDiaCalendario;
    }

    /**
     * O dia da semana juliano começa na segunda = 1
     *
     * @return
     */
    public int getDayWeekJuliano() {
        return dayWeekJuliano;
    }

    public int getTroncoDiaCurso() {
        return troncoDiaCurso;
    }

    public int getRamoDiaCurso() {
        return ramoDiaCurso;
    }

    public int getRamoHora() {
        return ramoHora;
    }

    public int getVasoMaravilhoso() {
        return vasoMaravilhoso;
    }

    public int getDiaChines() {
        return diaChines;
    }

    public int getMesChines() {
        return mesChines;
    }

    public int getAnoChines() {
        return anoChines;
    }

    public int getDiaDaSemanaChines() {
        return diaDaSemanaChines;
    }

    public int getDiaDoAnoChines() {
        return diaDoAnoChines;
    }

    public int getDiaOcidental() {
        return diaOcidental;
    }

    public int getMesOcidental() {
        return mesOcidental + 1;
    }

    public int getAnoOcidental() {
        return anoOcidental;
    }

    /**
     * Dia da semana no calendário Gregoriano começa no domingo = 1
     *
     * @return
     */
    public int getDiaDaSemanaOcidental() {
        return diaDaSemanaOcidental;
    }

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

    public int getDiaDoAnoOcidental() {
        return diaDoAnoOcidental;
    }
    
    public GregorianCalendar getDataAnoNovoChinesOnGregorianCalendar() {
        return dataAnoNovoChinesOnGregorianCalendar;
    }

    
    public void print(){
        System.out.println("troncoAno - " + getTroncoAno() + " - " + getInfoTCAno(NOME));
        System.out.println("ramoAno - " + getRamoAno() + " - " + getInfoRTAno(NOME));
        System.out.println("troncoMes - " + getTroncoMes() + " - " + getInfoTCMes(NOME));
        System.out.println("ramoMes - " + getRamoMes() + " - " + getInfoRTMes(NOME));
        System.out.println("julianDate - " + getJulianDate());
        System.out.println("posicaoDiaTabSexagesimal - " + getPosicaoDiaTabSexagesimal());
        System.out.println("troncoDiaCalendario - " + getTroncoDiaCalendario() + " - " + getInfoTCDiaCalendario(NOME));
        System.out.println("ramoDiaCalendario - " + getRamoDiaCalendario() + " - " + getInfoRTDiaCalendario(NOME));
        System.out.println("dayWeekJuliano - " + getDayWeekJuliano());
        System.out.println("troncoDiaCurso - " + getTroncoDiaCurso() + " - " + getInfoTCDiaCurso(NOME));
        System.out.println("ramoDiaCurso - " + getRamoDiaCurso() + " - " + getInfoRTDiaCurso(NOME));
        System.out.println("ramoHora - " + getRamoHora() + " - " + getInfoRTHora(NOME) + " - " + getZangFuHora(hr));
        System.out.println("vasoMaravilhoso - " + getVasoMaravilhoso() + " - " + getCampoVM(NOME) + " - " + getCampoVM(PONTO) + " - " + getCampoVM(TRIGRAMA));
        System.out.println("diaChines - " + getDiaChines());
        System.out.println("mesChines - " + getMesChines());
        System.out.println("anoChines - " + getAnoChines());
        System.out.println("diaDaSemanaChines - " + getDiaDaSemanaChines());
        System.out.println("diaDoAnoChines - " + getDiaDoAnoChines());
        System.out.println("diaOcidental - " + getDiaOcidental());//é o mesmo que o ocidental
        System.out.println("mesOcidental - " + getMesOcidental());
        System.out.println("anoOcidental - " + getAnoOcidental());
        System.out.println("diaDaSemanaOcidental - " + getDiaDaSemanaOcidental() + " - " + getDescricaoSemana(getDiaDaSemanaOcidental()));
        System.out.println("diaDoAnoOcidental - " + getDiaDoAnoOcidental());
    }
}
