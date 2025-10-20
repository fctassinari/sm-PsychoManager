package br.com.iidh.psmng.view.handler;

import br.com.iidh.psmng.util.AbstractCommonFacade;

/**
 *
 * @author Tassinari
 * @param <T>
 */
public abstract class AbstractHandlerFacade<T> extends AbstractCommonFacade {

    private final Class<T> entityClass;

    public AbstractHandlerFacade(Class<T> entityClass) {
        super(entityClass);
        this.entityClass = entityClass;
    }

    public void logPersistencia(String sqlCmd, String data) {
        modelLogger.debug(entityClass.getName() + ": " + sqlCmd + " - " + data);
    }

}
