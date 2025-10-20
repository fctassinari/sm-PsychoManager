package br.com.iidh.psmng.util.chinese;

/**
 *
 * @author tassinari
 */
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.util.Locale;
import java.util.stream.Stream;

/**
 * https://thiagovespa.com.br/blog/2018/04/04/calculo-do-dia-lunar-e-fases-da-lua-com-java-10/
 * https://thiagovespa.com.br/blog/2018/04/05/como-saber-a-fase-da-lua-de-cabeca/
 */
public class MoonUtils {

    public static final long SYNODIC_PERIOD = 2551442877L; //29.530588854 days

    public static final LocalDateTime KNOWN_NEW_MOON = LocalDate.of(1970, Month.JANUARY, 7).atStartOfDay();
    

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

    public static int getLunarDay(LocalDateTime day) {
        int lunarDay = (int) Duration.ofMillis(Duration.between(KNOWN_NEW_MOON, day).toMillis() % SYNODIC_PERIOD).toDays();
        return lunarDay;
    }

    public static MoonPhase getMoonPhase(int lunarDay) {
        int phaseNum = (int)Math.floor(lunarDay/3.7);
        return MoonPhase.values()[phaseNum];
    }

    public static MoonPhase getMoonPhaseConway(int lunarDay) {
        if(lunarDay<=28) {
            if(lunarDay>=24) {
                return MoonPhase.WANING_CRESCENT;
            }
            if(lunarDay>=22) {
                return MoonPhase.THIRD_QUARTER;
            }
            if(lunarDay>=17) {
                return MoonPhase.WANING_GIBBOUS;
            }
            if(lunarDay>=14) {
                return MoonPhase.FULL_MOON;
            }
            if(lunarDay>=9) {
                return MoonPhase.WAXING_GIBBOUS;
            }
            if(lunarDay>=7) {
                return MoonPhase.FIRST_QUARTER;
            }
            if(lunarDay>=2) {
                return MoonPhase.WAXING_CRESCENT;
            }
        }
        return MoonPhase.NEW_MOON; //0,1,29
    }

    /**
     * Based on https://www.cs.williams.edu/~bailey/cs135/lectures/Lecture02.pdf and http://www.faqs.org/faqs/astronomy/faq/part3/section-15.html
     * Only works on 20th and 21th century
     */
    public static int getLunarDayConway(LocalDateTime ldt) {
        int year = ldt.getYear();
        int month = ldt.getMonthValue();
        int day = ldt.getDayOfMonth();

        if(year < 1900 || year >= 2100) throw new RuntimeException("Date must be greater than 1900 and less than 2100");
        Double centS = -4.0;
        if(year > 2000) {
            centS = -8.3;
        }
        Double lastTwoDigits = year%100.0;
        double vl = lastTwoDigits % 19; 
        if(vl > 9) {
            vl-=19.0;
        }
        vl*=11.0;
        vl%=30;
        vl+=centS;

        vl+=month+day;
        if(month<3) {
            vl+=2;
        }

        vl = Math.round(vl)%30;
        return (int)((vl < 0) ? vl+30 : vl);
    }

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.plusMonths(1);

        Stream.iterate(now, date -> date.plusDays(1))
            .limit(ChronoUnit.DAYS.between(now, end))
            .forEach(MoonUtils::printLunarAnalysis);
      
    }

    static void printLunarAnalysis(LocalDateTime ldt) {
        DateTimeFormatter df = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.SHORT)
            .withLocale(new Locale("pt", "br"));

        int lunarDay = getLunarDay(ldt);
        int lunarDayConway = getLunarDayConway(ldt);
        MoonPhase moonPhase = getMoonPhase(lunarDay);
        MoonPhase moonPhaseConway = getMoonPhase(lunarDayConway);
        if(moonPhase.equals(MoonPhase.NEW_MOON)){
            System.out.println("Data: " + ldt.format(df));
            System.out.println("Dia lunar: " + lunarDay);
            System.out.println("Dia lunar Conway: " + lunarDayConway);
            System.out.println("Fase da lua (provável): " + moonPhase);
            System.out.println("Fase da lua (provável) Conway: " + moonPhaseConway);
            System.out.println();
        }
    }
}