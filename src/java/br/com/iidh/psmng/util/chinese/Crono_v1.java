package br.com.iidh.psmng.util.chinese;

import static br.com.iidh.psmng.util.chinese.JulianDate.toJulian;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author tassinari
 */
public class Crono_v1 {

    TimeZone tz = TimeZone.getTimeZone("GMT-3");
    Locale loc = new Locale("pt", "BR");
    Calendar calendar = Calendar.getInstance(tz, loc);
    GregorianCalendar gc = (GregorianCalendar) calendar;

    public static void main(String args[]) {
        String dtNascimento = String.valueOf(args);
        if (args.length <= 0) {
            dtNascimento = "06/06/2012";
        }

        new Crono_v1(new GregorianCalendar(Integer.parseInt(dtNascimento.substring(6, 10)),
                Integer.parseInt(dtNascimento.substring(3, 5)) - 1,
                Integer.parseInt(dtNascimento.substring(0, 2))));
    }

    public Crono_v1(GregorianCalendar gcNascimento) {
        //Ano
        int tcAnoNasc = 1 + ((gcNascimento.get(GregorianCalendar.YEAR) + 6) % 10);
        int rtAnoNasc = 1 + ((gcNascimento.get(GregorianCalendar.YEAR) + 8) % 12);
        //Mes
        ChineseCalendar ccNasc = new ChineseCalendar(gcNascimento.get(GregorianCalendar.YEAR),
                gcNascimento.get(GregorianCalendar.MONTH) + 1,
                gcNascimento.get(GregorianCalendar.DAY_OF_MONTH));
        String tcrtMesNasc = getTcRtMesChines(tcAnoNasc, ccNasc.getChineseMonth());
        int tcMesNasc = Integer.parseInt(tcrtMesNasc.substring(1, 2));
        int rtMesNasc = Integer.parseInt(tcrtMesNasc.substring(4));
        //Dia calendario
        double jdNasc = toJulian(new int[]{gcNascimento.get(GregorianCalendar.YEAR), gcNascimento.get(GregorianCalendar.MONTH) + 1, gcNascimento.get(GregorianCalendar.DATE)});
        int sNasc = (int) (1 + ((jdNasc - 11) % 60));
        int tcDiaNasc = (int) (1 + ((sNasc - 1) % 10));
        int rtDiaNasc = (int) (1 + ((sNasc - 1) % 12));
        int dayWeekNasc = (int) ((jdNasc + 1) % 7);
        //Dia professor
        String diaNascProf = dia[ccNasc.getChineseDate() - 1];
        int troncoDiaCurso = Integer.parseInt(diaNascProf.substring(1,2));
        int ramoDiaCurso =  Integer.parseInt(diaNascProf.substring(4));
        //Hora
        String horaNasc = "11-13";
        int rtHoraNasc = getRTHora(horaNasc);
        //Calculo do Vaso Maravilhoso
        int vasoNasc = (Integer.parseInt(subsTCDia[troncoDiaCurso - 1]) + Integer.parseInt(subsRTDia[ramoDiaCurso - 1]) + Integer.parseInt(subsRTHora[rtHoraNasc - 1])) % (ccNasc.getChineseDate() % 2 == 0 ? 6 : 9);

//-----------------------------------------------------------------------------------------
        //Ano
        int tcAnoAtual = 1 + ((gc.get(GregorianCalendar.YEAR) + 6) % 10);
        int rtAnoAtual = 1 + ((gc.get(GregorianCalendar.YEAR) + 8) % 12);
        //Mes
        ChineseCalendar ccAtual = new ChineseCalendar(gc.get(GregorianCalendar.YEAR),
                gc.get(GregorianCalendar.MONTH) + 1,
                gc.get(GregorianCalendar.DAY_OF_MONTH));
        String tcrtMesAtual = getTcRtMesChines(tcAnoAtual, ccAtual.getChineseMonth());
        int tcMesAtual = Integer.parseInt(tcrtMesAtual.substring(1, 2));
        int rtMesAtual = Integer.parseInt(tcrtMesAtual.substring(4));
        //Dia
        double jdAtual = toJulian(new int[]{gc.get(GregorianCalendar.YEAR), gc.get(GregorianCalendar.MONTH) + 1, gc.get(GregorianCalendar.DATE)});
        int sAtual = (int) (1 + ((jdAtual - 11) % 60));
        int tcDiaAtual = (int) (1 + ((sAtual - 1) % 10));
        int rtDiaAtual = (int) (1 + ((sAtual - 1) % 12));
        int dayWeekAtual = (int) ((jdAtual + 1) % 7);
        //Dia professor
        String diaAtualProf = dia[ccAtual.getChineseDate() - 1];
        int troncoDiaCursoA = Integer.parseInt(diaAtualProf.substring(1,2));
        int ramoDiaCursoA =  Integer.parseInt(diaAtualProf.substring(4));
        //Hora
        String horaAtual = "03-05";
        int rtHoraAtual = getRTHora(horaAtual);
        // Calculo do Vaso Maravilhoso
        int vasoAtual = (Integer.parseInt(subsTCDia[troncoDiaCursoA - 1]) + Integer.parseInt(subsRTDia[ramoDiaCursoA - 1]) + Integer.parseInt(subsRTHora[rtHoraAtual - 1])) % (ccAtual.getChineseDate() % 2 == 0 ? 6 : 9);;

        Calendar c = gc;
        System.out.println(c.getTime() + "\n");
        c = gcNascimento;
        System.out.println(c.getTime() + "\n");

        System.out.println(" Ano nasc  = " + gcNascimento.get(GregorianCalendar.YEAR));
        System.out.println(" Ano china = " + ccNasc.getChineseYear());
        System.out.println("    TC        = " + tcAnoNasc + " - " + tc[tcAnoNasc - 1][0] + " - " + tc[tcAnoNasc - 1][1] + " - " + tc[tcAnoNasc - 1][2] + " - " + tc[tcAnoNasc - 1][3]);
        System.out.println("    RT        = " + rtAnoNasc + " - " + rt[rtAnoNasc - 1][0] + " - " + rt[rtAnoNasc - 1][1] + " - " + rt[rtAnoNasc - 1][2]);
        System.out.println(" Mes nasc  = " + (gcNascimento.get(GregorianCalendar.MONTH) + 1));
        System.out.println(" Mes china = " + ccNasc.getChineseMonth());
        System.out.println("    TC        = " + tcMesNasc + " - " + tc[tcMesNasc - 1][0] + " - " + tc[tcMesNasc - 1][1] + " - " + tc[tcMesNasc - 1][2] + " - " + tc[tcMesNasc - 1][3]);
        System.out.println("    RT        = " + rtMesNasc + " - " + rt[rtMesNasc - 1][0] + " - " + rt[rtMesNasc - 1][1] + " - " + rt[rtMesNasc - 1][2]);
        System.out.println(" Dia nasc  = " + gcNascimento.get(GregorianCalendar.DAY_OF_MONTH));
        System.out.println(" Dia china = " + ccNasc.getChineseDate());
        //System.out.println("    JD        = " + jdNasc);
        System.out.println("    S         = " + sNasc);
        System.out.println("    TC        = " + tcDiaNasc + " - " + tc[tcDiaNasc - 1][0]);
        System.out.println("    RT        = " + rtDiaNasc + " - " + rt[rtDiaNasc - 1][0]);
        //System.out.println("    DW        = " + dayWeekNasc);
        System.out.println(" Dia Nas china= " + diaNascProf);
        System.out.println("    TC        = " + troncoDiaCurso);
        System.out.println("    RT        = " + ramoDiaCurso);
        System.out.println(" Hora nasc    = " + horaNasc);
        System.out.println("    RT        = " + rtHoraNasc + " - " + rt[rtHoraNasc - 1][0] + " - " + rt[rtHoraNasc - 1][1] + " - " + rt[rtHoraNasc - 1][2]);
        System.out.println(" Vaso nasc    = " + vasoNasc + " - " + getVM(vasoNasc));
        //System.out.println(Integer.parseInt(subsTCDia[tcDiaNasc-1]) + " + " + Integer.parseInt(subsRTDia[rtDiaNasc-1]) + " + " +  Integer.parseInt(subsRTHora[rtHoraNasc-1]) + "%" + (ccNasc.getChineseDate()%2==0?6:9));
        //System.out.println("ccNasc.getChineseDate()%2="+ccNasc.getChineseDate()%2);
        //System.out.println("(ccNasc.getChineseDate()%2==0?6:9)="+(ccNasc.getChineseDate()%2==0?6:9));

        System.out.println("\n\n");

        System.out.println(" Ano atual = " + gc.get(GregorianCalendar.YEAR));
        System.out.println(" Ano china = " + ccAtual.getChineseYear());
        System.out.println("    TC        = " + tcAnoAtual + " - " + tc[tcAnoAtual - 1][0] + " - " + tc[tcAnoAtual - 1][1] + " - " + tc[tcAnoAtual - 1][2] + " - " + tc[tcAnoAtual - 1][3]);
        System.out.println("    RT        = " + rtAnoAtual + " - " + rt[rtAnoAtual - 1][0] + " - " + rt[rtAnoAtual - 1][1] + " - " + rt[rtAnoAtual - 1][2]);
        System.out.println(" Mes atual = " + (gc.get(GregorianCalendar.MONTH) + 1));
        System.out.println(" Mes china = " + ccAtual.getChineseMonth());
        System.out.println("    TC        = " + tcMesAtual + " - " + tc[tcMesAtual - 1][0] + " - " + tc[tcMesAtual - 1][1] + " - " + tc[tcMesAtual - 1][2] + " - " + tc[tcMesAtual - 1][3]);
        System.out.println("    RT        = " + rtMesAtual + " - " + rt[rtMesAtual - 1][0] + " - " + rt[rtMesAtual - 1][1] + " - " + rt[rtMesAtual - 1][2]);
        System.out.println(" Dia atual = " + gc.get(GregorianCalendar.DAY_OF_MONTH));
        System.out.println(" Dia china = " + ccAtual.getChineseDate());
        //System.out.println("    JD        = " + jdAtual);
        System.out.println("    S         = " + sAtual);
        System.out.println("    TC        = " + tcDiaAtual + " - " + tc[tcDiaAtual - 1][0] + " - " + tc[tcDiaAtual - 1][1] + " - " + tc[tcDiaAtual - 1][2] + " - " + tc[tcDiaAtual - 1][3]);
        System.out.println("    RT        = " + rtDiaAtual + " - " + rt[rtDiaAtual - 1][0] + " - " + rt[rtDiaAtual - 1][1] + " - " + rt[rtDiaAtual - 1][2]);
        //System.out.println("    DW        = " + dayWeekAtual);
        System.out.println(" Dia Atu china= " + diaAtualProf);
        System.out.println("    TC        = " + troncoDiaCursoA);
        System.out.println("    RT        = " + ramoDiaCursoA);
        System.out.println(" Hora atual   = " + horaAtual);
        System.out.println("    RT        = " + rtHoraAtual + " - " + rt[rtHoraAtual - 1][0] + " - " + rt[rtHoraAtual - 1][1] + " - " + rt[rtHoraAtual - 1][2]);
        System.out.println(" Vaso atual   = " + vasoAtual + " - " + getVM(vasoAtual));
        //System.out.println(Integer.parseInt(subsTCDia[tcDiaAtual-1]) + " + " + Integer.parseInt(subsRTDia[rtDiaAtual-1]) + " + " +  Integer.parseInt(subsRTHora[rtHoraAtual-1]) + "%" + (ccAtual.getChineseDate()%2==0?6:9));
        //System.out.println("ccAtual.getChineseDate()%2="+ccAtual.getChineseDate()%2);
        //System.out.println("(ccAtual.getChineseDate()%2==0?6:9)="+(ccAtual.getChineseDate()%2==0?6:9));

        System.out.println("\n\n");
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

    private String getVM(int num) {
        String ret = "";

        for (int i = 0; i < vasosMaravilhosos.length; i++) {
            if (String.valueOf(num).equals(vasosMaravilhosos[i][3])) {
                ret = vasosMaravilhosos[i][0];
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

    private static String[][] rt = {
        {"Zi", "Água", "Yang"}, // 1
        {"Chou", "Terra", "Yin"}, // 2
        {"Yin", "Madeira", "Yang"}, // 3
        {"Mao", "Madeira", "Yin"}, // 4
        {"Chén", "Terra", "Yang"}, // 5
        {"Si", "Fogo", "Yin"}, // 6
        {"Wu", "Fogo", "Yang"}, // 7
        {"Wèi", "Terra", "Yin"}, // 8
        {"Shen", "Metal", "Yang"}, // 9
        {"You", "Metal", "Yin"}, // 10
        {"Xu", "Terra", "Yang"}, // 11
        {"Hài", "Água", "Yin"} // 12
    };

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
        {"BP4 (Gongsun)",   "Zhong Mai",        "Li",   "3"},
        {"PC6 (Neiguan)",   "Yin Wei Mai",      "Gen ", "7"},
        {"ID3 (Houxi)",     "Du Mai",           "Qian", "1"},
        {"B62 (Shenmai)",   "Yang Qiao Mai",    "Zhen", "4"},
        {"VB41 (Zulinqi)",  "Daí Mai",          "Kan",  "6"},
        {"TA5 (Waiguan)",   "Yang Wei Mai",     "Dui",  "2"},
        {"P7 (Lieque)",     "Ren Mai",          "Kun",  "8"},
        {"R6 (Zhaohai)",    "Yin Qiao Mai",     "Xun",  "5"}
    };
}
