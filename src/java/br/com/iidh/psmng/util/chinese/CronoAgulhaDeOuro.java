package br.com.iidh.psmng.util.chinese;

/**
 *
 * @author tassinari
 */
public class CronoAgulhaDeOuro extends Cronoacupuntura {

    private String hr = "";
    private Integer numQuadradoMagico;

    public CronoAgulhaDeOuro(String data, String hora) {
        super(data, hora);
        hr = hora;
        calcularAgulhaDeOuro();
    }

    private void calcularAgulhaDeOuro() {

        int indiceHora = 0;

        for (String[] hora1 : hora) {
            if (hr.equals(hora1[0])) {
                indiceHora = Integer.parseInt(hora1[1]);
            }
        }
        numQuadradoMagico = sexagesimalXHora[getPosicaoDiaTabSexagesimal() - 1][indiceHora - 1];
    }

    public void print() {
        System.out.println("troncoAno - " + getTroncoAno() + " - " + getInfoTCAno(NOME));
        System.out.println("ramoAno - " + getRamoAno() + " - " + getInfoRTAno(NOME));
        System.out.println("troncoMes - " + getTroncoMes() + " - " + getInfoTCMes(NOME));
        System.out.println("ramoMes - " + getRamoMes() + " - " + getInfoRTMes(NOME));
        System.out.println("julianDate - " + getJulianDate());
        System.out.println("posicaoDiaTabSexagesimal - " + getPosicaoDiaTabSexagesimal());
        System.out.println("troncoDia - " + getTroncoDia() + " - " + getInfoTCDia(NOME));
        System.out.println("ramoDia - " + getRamoDia() + " - " + getInfoRTDia(NOME));
        System.out.println("dayWeekJuliano - " + getDayWeekJuliano());
        System.out.println("Num Quadrado Mágico - " + numQuadradoMagico);

        System.out.println("ramoHora - " + getRamoHora() + " - " + getInfoRTHora(NOME) + " - " + getZangFuHora(hr));
        System.out.println("vasoMaravilhoso - " + getNumQuadradoMagico() + " - " + getCampoVM(NOME) + " - " + getCampoVM(PONTO) + " - " + getCampoVM(TRIGRAMA));
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
        System.out.println("AGULHA DE OURO - ");
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
        System.out.println("\tS" + getPosicaoDiaTabSexagesimal());
        System.out.println("\tT" + getTroncoDia() + "\t" + getInfoTCDia(Cronoacupuntura.NOME) + "\t" + getInfoTCDia(Cronoacupuntura.ELEMENTO) + " " + getInfoTCDia(Cronoacupuntura.POLARIDADE));
        System.out.println("\tR" + getRamoDia() + "\t" + getInfoRTDia(Cronoacupuntura.NOME) + "\t" + getInfoRTDia(Cronoacupuntura.ELEMENTO) + " " + getInfoRTDia(Cronoacupuntura.POLARIDADE));

        System.out.println("Hora ");
        System.out.println("\tR" + getRamoHora() + "\t" + getInfoRTHora(Cronoacupuntura.NOME) + "\t" + getInfoRTHora(Cronoacupuntura.ELEMENTO) + " " + getInfoRTHora(Cronoacupuntura.POLARIDADE) + "\t" + getZangFuHora(hr));

        System.out.println("Aplicação ");
        System.out.println("\tVM " + getNumQuadradoMagico() + "\t" + getCampoVM(Cronoacupuntura.NOME) + "\t" + getCampoVM(Cronoacupuntura.PONTO) + "" + getCampoVM(Cronoacupuntura.TRIGRAMA));
    }

    public Integer getNumQuadradoMagico() {
        return numQuadradoMagico;
    }

    /**
     * Cada linha é uma linha da tabela sexagesimal Cada coluna é um par de
     * horas, 23-01, 01-03....
     */
    private static final Integer[][] sexagesimalXHora = {
        {8, 6, 4, 2, 9, 3, 7, 5, 3, 1, 4, 2},
        {5, 3, 1, 4, 2, 6, 4, 2, 5, 3, 1, 5},
        {2, 5, 3, 1, 8, 6, 6, 4, 2, 9, 7, 1},
        {3, 1, 5, 3, 6, 4, 2, 6, 4, 1, 5, 3},
        {5, 3, 6, 4, 2, 9, 4, 7, 5, 3, 1, 8},
        {5, 3, 1, 5, 3, 6, 4, 2, 6, 4, 1, 5},
        {5, 3, 1, 4, 2, 9, 4, 2, 5, 3, 1, 8},
        {1, 4, 2, 6, 4, 2, 5, 3, 1, 5, 3, 6},
        {7, 5, 3, 1, 4, 2, 6, 4, 2, 5, 3, 1},
        {1, 5, 2, 6, 4, 2, 6, 3, 1, 5, 3, 1},
        {2, 9, 7, 5, 3, 6, 1, 8, 6, 4, 7, 5},
        {2, 6, 4, 1, 5, 3, 1, 5, 2, 6, 4, 2},
        {1, 4, 2, 9, 7, 5, 5, 3, 1, 8, 6, 9},
        {5, 3, 1, 5, 2, 6, 4, 2, 6, 3, 1, 5},
        {3, 1, 4, 2, 9, 7, 2, 5, 3, 1, 8, 6},
        {6, 4, 2, 6, 4, 1, 5, 3, 1, 5, 2, 6},
        {8, 6, 4, 7, 5, 3, 7, 5, 8, 6, 4, 2},
        {4, 1, 5, 6, 1, 5, 2, 6, 4, 2, 6, 3},
        {5, 3, 1, 8, 3, 9, 4, 2, 9, 3, 1, 8},
        {2, 6, 3, 1, 5, 3, 1, 4, 2, 6, 4, 2},
        {1, 8, 6, 4, 2, 5, 9, 7, 5, 3, 6, 4},
        {4, 2, 6, 3, 1, 5, 3, 1, 4, 2, 6, 4},
        {4, 7, 5, 3, 1, 8, 8, 6, 4, 2, 9, 3},
        {2, 6, 4, 2, 5, 3, 1, 5, 3, 6, 4, 2},
        {2, 9, 3, 1, 8, 6, 1, 4, 2, 9, 7, 5},
        {2, 6, 4, 2, 6, 3, 1, 5, 3, 1, 4, 2},
        {6, 4, 2, 5, 3, 1, 5, 3, 6, 4, 2, 9},
        {5, 2, 6, 4, 2, 6, 3, 1, 5, 3, 1, 4},
        {8, 6, 4, 2, 5, 3, 7, 5, 3, 6, 4, 2},
        {5, 3, 6, 4, 2, 6, 4, 1, 6, 3, 1, 5},
        {8, 6, 4, 2, 9, 3, 7, 5, 3, 1, 4, 2},
        {5, 3, 1, 4, 2, 6, 4, 2, 5, 3, 1, 5},
        {3, 6, 4, 2, 9, 7, 7, 5, 3, 1, 8, 2},
        {4, 2, 6, 4, 1, 5, 3, 1, 5, 2, 6, 4},
        {5, 3, 6, 4, 2, 9, 4, 7, 5, 3, 1, 8},
        {5, 3, 1, 5, 3, 6, 4, 2, 6, 4, 1, 5},
        {5, 3, 1, 4, 2, 9, 4, 2, 5, 3, 1, 8},
        {1, 4, 2, 6, 4, 2, 5, 3, 1, 5, 3, 6},
        {6, 4, 2, 9, 3, 1, 5, 3, 1, 4, 2, 9},
        {6, 4, 1, 5, 3, 1, 5, 2, 6, 4, 2, 8},
        {2, 9, 7, 5, 3, 6, 1, 8, 6, 4, 7, 5},
        {2, 6, 4, 1, 5, 3, 1, 5, 2, 6, 4, 2},
        {1, 4, 2, 9, 7, 5, 5, 3, 1, 8, 6, 9},
        {5, 3, 1, 5, 2, 6, 4, 2, 6, 3, 1, 5},
        {4, 2, 5, 3, 1, 8, 3, 6, 4, 2, 9, 7},
        {1, 5, 3, 1, 5, 2, 6, 4, 2, 6, 3, 1},
        {8, 6, 4, 7, 5, 3, 7, 5, 8, 6, 4, 2},
        {4, 1, 5, 3, 1, 5, 2, 6, 4, 2, 6, 3},
        {5, 3, 1, 8, 2, 9, 4, 2, 9, 3, 1, 8},
        {2, 6, 3, 1, 5, 3, 1, 4, 2, 6, 4, 2},
        {9, 7, 5, 3, 1, 4, 8, 6, 4, 2, 5, 8},
        {3, 1, 5, 2, 6, 4, 2, 6, 3, 1, 5, 3},
        {4, 7, 5, 3, 1, 8, 8, 6, 4, 2, 9, 3},
        {2, 6, 4, 2, 5, 3, 1, 5, 3, 6, 4, 2},
        {2, 9, 3, 1, 8, 6, 1, 4, 2, 9, 7, 5},
        {2, 6, 4, 2, 6, 3, 1, 5, 3, 1, 4, 2},
        {7, 5, 3, 6, 4, 2, 6, 4, 7, 5, 3, 1},
        {6, 3, 1, 5, 3, 1, 4, 2, 6, 4, 2, 5},
        {8, 6, 4, 2, 5, 3, 7, 5, 3, 6, 4, 2},
        {5, 3, 6, 4, 2, 6, 4, 1, 5, 3, 1, 5}
    };

    private static final String[][] vasosMaravilhosos = {
        {"Yang Qiao Mai",   "B62 (Shenmai) - ID3 (Houxi)", "Água"},
        {"Yin Qiao Mai",    "R6 (Zhaohai) - P7 (Lieque)", "Terra"},
        {"Yang Wei Mai",    "TA5 (Waiguan) - VB41 (Zulinqi)", "Trovão"},
        {"Dai Mai",         "VB41 (Zulinqi) - TA5 (Waiguan)", "Vento"},
        {"Yin Qiao Mai",    "R6 (Zhaohai) - P7 (Lieque)", "Terra"},
        {"Zhong Mai",       "BP4 (Gongsun) - PC6 (Neiguan)", "Céu"},
        {"Du Mai",          "ID3 (Houxi) - B62 (Shenmai)", "Lago"},
        {"Yin Wei Mai",     "PC6 (Neiguan) - BP4 (Gongsun)", "Montanha"},
        {"Ren Mai",         "P7 (Lieque) - R6 (Zhaohai)", "Fogo"}
    };

    /**
     * @param info 0 = Nome, 1 = Ponto Principal, 2 = Ponto Acoplado, 3 = Trigrama
     * @return
     */
    public String getCampoVM(int info) {
        return vasosMaravilhosos[getNumQuadradoMagico()-1][info];
    }

}
