package br.com.iidh.psmng.view.handler.util;

import br.com.iidh.psmng.util.Padroes;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Tassinari
 */
public class CalendarPeriodBean implements Serializable{

    private Locale locale;
    private boolean popup;
    private Date selectedDate = Calendar.getInstance().getTime();
    private boolean showApply = false;
    private boolean useCustomDayLabels;
    private boolean disabled = false;

    private Date selectedDateDe = Calendar.getInstance().getTime();
    private Date selectedDateAte = Calendar.getInstance().getTime();

    public CalendarPeriodBean() {
        locale = Locale.getDefault();
        popup = true;
    }

    public Locale getLocale() {
//        System.out.println("------------------------" + locale.toString());
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public boolean isPopup() {
        return popup;
    }

    public void setPopup(boolean popup) {
        this.popup = popup;
    }

    public String getPatternDTHR() {
        return Padroes.formatoDataHora.toPattern();
    }

    public void selectLocale(ValueChangeEvent event) {

        String tLocale = (String) event.getNewValue();
        if (tLocale != null) {
            String lang = tLocale.substring(0, 2);
            String country = tLocale.substring(3);
            locale = new Locale(lang, country, "");
        }
    }

    public boolean isUseCustomDayLabels() {
        return useCustomDayLabels;
    }

    public void setUseCustomDayLabels(boolean useCustomDayLabels) {
        this.useCustomDayLabels = useCustomDayLabels;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public Date getSelectedDateDe() {
        return selectedDateDe;
    }

    public void setSelectedDateDe(Date selectedDateDe) {
        this.selectedDateDe = selectedDateDe;
    }

    public Date getSelectedDateAte() {
        return selectedDateAte;
    }

    public void setSelectedDateAte(Date selectedDateAte) {
        this.selectedDateAte = selectedDateAte;
    }

    public boolean isShowApply() {
        return showApply;
    }

    public void setShowApply(boolean showApply) {
        this.showApply = showApply;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isShowApplyAvailable() {
        return !disabled && popup;
    }

    public boolean isPatternAvailable() {
        return !disabled && popup;
    }

    public String getTimezone() {
        return Padroes.TIMEZONE;
    }

    public String getPatternDT() {
        return Padroes.formatoData.toPattern();
    }

}
