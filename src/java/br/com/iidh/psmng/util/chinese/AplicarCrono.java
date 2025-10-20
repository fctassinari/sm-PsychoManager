package br.com.iidh.psmng.util.chinese;

/**
 *
 * @author tassinari
 */
public class AplicarCrono {
//        String dataNasc = "24/08/1944";
//        String dataNasc = "28/03/1976";
//        String dataNasc = "18/02/1977";
        String dataNasc = "16/01/1972";
//        String dataNasc = "06/06/1999";
//        String dataNasc = "21/05/1995";
//        String dataNasc = "24/10/2020";
        String horaNasc = Cronoacupuntura.HORA_23_01;

//        String dataAplic = "23/09/2020";
//        String horaAplic = Cronoacupuntura.HORA_15_17;

        String dataAplic = "24/10/2020";
        String horaAplic = Cronoacupuntura.HORA_23_01;

    
//    TimeZone tz = TimeZone.getTimeZone("GMT-3");
//    Locale loc = new Locale("pt", "BR");
//    Calendar calendar = Calendar.getInstance(tz, loc);
//    GregorianCalendar gcToday = (GregorianCalendar) calendar;

    public static void main(String args[]) {
        AplicarCrono ac = new AplicarCrono();
        ac.portaDoDragao();
        ac.agulhaDeOuro();
        
        String x = "1";
        System.out.println(String.format("%02d", Integer.parseInt(x)));
        
        
    }

    public void portaDoDragao() {
//        CronoPortaDoDragao cronoNasc = new CronoPortaDoDragao(dataNasc, horaNasc);
//        CronoPortaDoDragao cronoAplic = new CronoPortaDoDragao(dataAplic, horaAplic);

//        cronoNasc.printResultado();
//        System.out.println("\n---------------------------------------------------------------\n");
//        cronoAplic.printResultado();
    }

    public void agulhaDeOuro() {
        CronoAgulhaDeOuro cronoNasc = new CronoAgulhaDeOuro(dataNasc, horaNasc);
        CronoAgulhaDeOuro cronoAplic = new CronoAgulhaDeOuro(dataAplic, horaAplic);

        cronoNasc.printResultado();
        System.out.println("\n---------------------------------------------------------------\n");
        cronoAplic.printResultado();        
    }
}
