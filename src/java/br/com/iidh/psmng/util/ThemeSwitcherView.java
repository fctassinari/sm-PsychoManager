package br.com.iidh.psmng.util;

/**
 *
 * @author Tassinari
 */
import br.com.iidh.psmng.view.handler.util.JsfUtil;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.component.themeswitcher.ThemeSwitcher;

@ManagedBean
@ApplicationScoped
public class ThemeSwitcherView extends AbstractCommonFacade{

    private List<Theme> themes;
    private String defaultTheme = "omega";
    private String userTheme = defaultTheme;

    @ManagedProperty("#{themeService}")
    private ThemeService service;

    public ThemeSwitcherView() {
        super(ThemeSwitcherView.class);
    }

    @PostConstruct
    public void init() {
        themes = service.getThemes();
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setService(ThemeService service) {
        this.service = service;
    }

    public void saveTheme(AjaxBehaviorEvent ajax) {
        setUserTheme((String) ((ThemeSwitcher) ajax.getSource()).getValue());
        JsfUtil.addSuccessMessage(getLabelMessages("TemaAlterado"));
    }

    public String getUserTheme() {
        return userTheme;
    }

    public void setUserTheme(String userTheme) {
        this.userTheme = userTheme;
    }

    public String getDefaultTheme() {
        return defaultTheme;
    }

    public void setDefaultTheme(String defaultTheme) {
        this.defaultTheme = defaultTheme;
    }

}
