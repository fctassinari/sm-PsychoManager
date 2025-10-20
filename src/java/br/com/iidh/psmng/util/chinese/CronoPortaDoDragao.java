package br.com.iidh.psmng.util.chinese;

/**
 *
 * @author tassinari
 */
public class CronoPortaDoDragao extends Cronoacupuntura {

    private static String[] tcrtDia = {
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
// Original
    private static final String[][] vasosMaravilhosos = {
        {"Zhong Mai", "BP4 (Gongsun)", "Li", "3"},
        {"Yin Wei Mai", "PC6 (Neiguan)", "Gen ", "7"},
        {"Du Mai", "ID3 (Houxi)", "Qian", "1"},
        {"Yang Qiao Mai", "B62 (Shenmai)", "Zhen", "4"},
        {"Dai Mai", "VB41 (Zulinqi)", "Kan", "6"},
        {"Yang Wei Mai", "TA5 (Waiguan)", "Dui", "2"},
        {"Ren Mai", "P7 (Lieque)", "Kun", "8"},
        {"Yin Qiao Mai", "R6 (Zhaohai)", "Xun", "5"}
    };
// Teste
//    private static final String[][] vasosMaravilhosos = {
//        {"Zhong Mai",     "BP4 (Gongsun)", "Quian", "6"},
//        {"Yin Wei Mai",   "PC6 (Neiguan)", "Gen ", "8"},
//        {"Du Mai",        "ID3 (Houxi)", "Dui", "7"},
//        {"Yang Qiao Mai", "B62 (Shenmai)", "Kan", "1"},
//        {"Dai Mai",       "VB41 (Zulinqi)", "Xun", "4"},
//        {"Yang Wei Mai",  "TA5 (Waiguan)", "Zhen", "3"},
//        {"Ren Mai",       "P7 (Lieque)", "Li", "9"},
//        {"Yin Qiao Mai",  "R6 (Zhaohai)", "kun", "5"}
//    };
    
    
    
    private String hr = "";
    private int troncoDia;
    private int ramoDia;
    private int vasoMaravilhoso;

    public CronoPortaDoDragao(String data, String hora) {
        super(data, hora);
        hr = hora;
        calcularPortaDoDragao();
    }

    private void calcularPortaDoDragao() {
        String dia = tcrtDia[getDataChinesa().getChineseDate() - 1];
        String[] result = dia.split(",");
        troncoDia = Integer.parseInt(result[0].substring(1));
        ramoDia = Integer.parseInt(result[1].substring(1));

        //Calculo do Vaso Maravilhoso
        vasoMaravilhoso = (Integer.parseInt(subsTCDia[troncoDia - 1]) + Integer.parseInt(subsRTDia[ramoDia - 1]) + Integer.parseInt(subsRTHora[getRamoHora() - 1])) % (getDataChinesa().getChineseDate() % 2 == 0 ? 6 : 9);
        
        
        //Se resto for 0 e o dia for par
        if (vasoMaravilhoso == 0  && (getDataChinesa().getChineseDate() % 2 == 0)){
            vasoMaravilhoso = 6;
        }
        //Se resto for 0 e dia for impar
        if (vasoMaravilhoso == 0  && (getDataChinesa().getChineseDate() % 2 != 0)){
            vasoMaravilhoso = 1;
        }
    }

    /**
     *
     * @param info Cronoacupuntura.NOME, Cronoacupuntura.ELEMENTO,
     * Cronoacupuntura.POLARIDADE, Cronoacupuntura.IDEOGRAMA
     * @return
     */
    @Override
    public String getInfoTCDia(int info) {
        return getInfoTC(this.troncoDia, info);
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
     * @param info 0 = Nome, 1 = Ponto, 2 = Trigrama
     * @return
     */
    public String getCampoVM(int info) {
        String ret = "";

        for (String[] vasosMaravilhoso : vasosMaravilhosos) {
            if (String.valueOf(vasoMaravilhoso).equals(vasosMaravilhoso[3])) {
                ret = vasosMaravilhoso[info];
            }
        }
        return ret;
    }

    public int getVasoMaravilhoso() {
        return vasoMaravilhoso;
    }

    /**
     *
     * @param info Cronoacupuntura.NOME, Cronoacupuntura.ELEMENTO,
     * Cronoacupuntura.POLARIDADE, Cronoacupuntura.IDEOGRAMA
     * @return
     */
    @Override
    public String getInfoRTDia(int info) {
        return getInfoRT(this.ramoDia, info);
    }
    
    public void print() {
        System.out.println("troncoAno - " + getTroncoAno() + " - " + getInfoTCAno(NOME));
        System.out.println("ramoAno - " + getRamoAno() + " - " + getInfoRTAno(NOME));
        System.out.println("troncoMes - " + getTroncoMes() + " - " + getInfoTCMes(NOME));
        System.out.println("ramoMes - " + getRamoMes() + " - " + getInfoRTMes(NOME));
        System.out.println("julianDate - " + getJulianDate());
        System.out.println("posicaoDiaTabSexagesimal - " + getPosicaoDiaTabSexagesimal());
        System.out.println("dayWeekJuliano - " + getDayWeekJuliano());
        System.out.println("troncoDiaCurso - " + getTroncoDia() + " - " + getInfoTCDia(NOME));
        System.out.println("ramoDiaCurso - " + getRamoDia() + " - " + getInfoRTDia(NOME));
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

    public void printResultado() {
        System.out.println("PORTA DO DRAGÃO - LONGMEN");
        System.out.println("Oito Técnicas da Tartaruga Mística");
        System.out.println("Lin Gui Ba Fa - 灵龟八法");
        System.out.println("----------------------");

        System.out.println("Data Calendario Gregoriano");
        System.out.println("\t" + getDiaOcidental() + "/" + getMesOcidental() + "/" + getAnoOcidental());

        System.out.println("Data Calendario Chines");
        System.out.println("\t" + getDiaChines() + "/" + getMesChines() + "/" + getAnoChines());

//        System.out.println("Data de Ano Novo Chines no calendario Ocidental");
//        System.out.println("\t" + c.getDataAnoNovoChinesOnGregorianCalendar().toString());
        System.out.println("Hora ");
        System.out.println("\t" + hr);

        System.out.println("Ano");
        System.out.println("\tT" + getTroncoAno() + "\t" + getInfoTCAno(Cronoacupuntura.NOME) + "\t" + getInfoTCAno(Cronoacupuntura.ELEMENTO) + " " + getInfoTCAno(Cronoacupuntura.POLARIDADE) + " " + getInfoTCAno(Cronoacupuntura.IDEOGRAMA));
        System.out.println("\tR" + getRamoAno() + "\t" + getInfoRTAno(Cronoacupuntura.NOME) + "\t" + getInfoRTAno(Cronoacupuntura.ELEMENTO) + " " + getInfoRTAno(Cronoacupuntura.POLARIDADE) + " " + getInfoRTAno(Cronoacupuntura.IDEOGRAMA) + " " + getInfoRTAno(Cronoacupuntura.HOROSCOPO));

        System.out.println("Mes " + getMesChines());
        System.out.println("\tT" + getTroncoMes() + "\t" + getInfoTCMes(Cronoacupuntura.NOME) + "\t" + getInfoTCMes(Cronoacupuntura.ELEMENTO) + " " + getInfoTCMes(Cronoacupuntura.POLARIDADE));
        System.out.println("\tR" + getRamoMes() + "\t" + getInfoRTMes(Cronoacupuntura.NOME) + "\t" + getInfoRTMes(Cronoacupuntura.ELEMENTO) + " " + getInfoRTMes(Cronoacupuntura.POLARIDADE));

        System.out.println("Dia " + getDiaChines());
        System.out.println("\tT" + getTroncoDia() + "\t" + getInfoTCDia(Cronoacupuntura.NOME) + "\t" + getInfoTCDia(Cronoacupuntura.ELEMENTO) + " " + getInfoTCDia(Cronoacupuntura.POLARIDADE));
        System.out.println("\tR" + getRamoDia() + "\t" + getInfoRTDia(Cronoacupuntura.NOME) + "\t" + getInfoRTDia(Cronoacupuntura.ELEMENTO) + " " + getInfoRTDia(Cronoacupuntura.POLARIDADE));

        System.out.println("Hora ");
        System.out.println("\tR" + getRamoHora() + "\t" + getInfoRTHora(Cronoacupuntura.NOME) + "\t" + getInfoRTHora(Cronoacupuntura.ELEMENTO) + " " + getInfoRTHora(Cronoacupuntura.POLARIDADE) + "\t" + getZangFuHora(hr));

        System.out.println("Aplicação ");
        System.out.println("\tVM " + getVasoMaravilhoso() + "\t" + getCampoVM(Cronoacupuntura.NOME) + "\t" + getCampoVM(Cronoacupuntura.PONTO) + "\t" + getCampoVM(Cronoacupuntura.TRIGRAMA));

    }
}
