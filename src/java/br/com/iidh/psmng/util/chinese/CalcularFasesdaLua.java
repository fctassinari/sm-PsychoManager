/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.iidh.psmng.util.chinese;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

/**
 *
 * @author tassinari
 */
public class CalcularFasesdaLua {
    
    public static void main(String args[]){
        new CalcularFasesdaLua();
    }

    public CalcularFasesdaLua() {
        //https://thiagovespa.com.br/blog/2018/04/04/calculo-do-dia-lunar-e-fases-da-lua-com-java-10/
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusMonths(1);
        LocalDateTime end = now.plusMonths(1);

        Stream.iterate(start, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(start, end))
                .forEach(MoonUtils::printLunarAnalysis);
    }
/*
    static enum MoonPhase {
        NEW_MOON, // Nova
        WAXING_CRESCENT, // Crescente
        FIRST_QUARTER, // Quarto crescente
        WAXING_GIBBOUS, // Crescente gibosa
        FULL_MOON, // Cheia
        WANING_GIBBOUS, // Minguante gibosa
        THIRD_QUARTER, // Quarto minguante
        WANING_CRESCENT; // Minguante
    }

    public static final long SYNODIC_PERIOD = 2551442877L; //29.530588854 days

    public static final LocalDateTime KNOWN_NEW_MOON = LocalDate.of(1970, Month.JANUARY, 7).atStartOfDay();

    public static int getLunarDay(LocalDateTime day) {
        int lunarDay = (int) Duration.ofMillis(Duration.between(KNOWN_NEW_MOON, day).toMillis() % SYNODIC_PERIOD).toDays();
        return lunarDay;
    }

    public static MoonPhase getMoonPhase(int lunarDay) {
        int phaseNum = (int) Math.floor(lunarDay / 3.7);
        return MoonPhase.values()[phaseNum];
    }

    private static void printLunarAnalysis(LocalDateTime ldt) {
        DateTimeFormatter df = DateTimeFormatter
                .ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(new Locale("pt", "br"));

        int lunarDay = getLunarDay(ldt);
        MoonPhase moonPhase = getMoonPhase(lunarDay);
        System.out.println("Data: " + ldt.format(df));
        System.out.println("Dia lunar: " + lunarDay);
        System.out.println("Fase da lua (provável): " + moonPhase);
        System.out.println();
    }*/
}
