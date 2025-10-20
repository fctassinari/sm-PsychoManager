package br.com.iidh.psmng.control.exception;

import java.util.Iterator;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import org.apache.log4j.Logger;

/**
 *
 * @author Tassinari
 */
public class CustomExceptionHandler extends ExceptionHandlerWrapper {
    
    private Logger logger;
    private ExceptionHandler wrapped;

    public CustomExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
        logger = Logger.getLogger(CustomExceptionHandler.class);
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {
        Iterator iterator = getUnhandledExceptionQueuedEvents().iterator();

        while (iterator.hasNext()) {
            ExceptionQueuedEvent event = (ExceptionQueuedEvent) iterator.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

            Throwable throwable = context.getException();

            FacesContext fc = FacesContext.getCurrentInstance();
            if (throwable instanceof ViewExpiredException) {
                ViewExpiredException vee = (ViewExpiredException) throwable;
                Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
                NavigationHandler nav = fc.getApplication().getNavigationHandler();
                try {
                    // Push some stuff to the request scope for later use in the page
                    requestMap.put("currentViewId", vee.getViewId());
                    nav.handleNavigation(fc, null, "/viewExpired");            
                    fc.renderResponse();

                } finally {
                    iterator.remove();
                }
            } else {
                try {
                    Flash flash = fc.getExternalContext().getFlash();
                //          flash.setKeepMessages(true); 
                    // Put the exception in the flash scope to be displayed in the error 
                    // page if necessary ...

                    flash.put("errorDetails", throwable.getMessage());
                    
                    logger.error(throwable.getMessage());

                    System.out.println("the error is put in the flash: " + throwable.getMessage());

                    NavigationHandler navigationHandler = fc.getApplication().getNavigationHandler();
                    navigationHandler.handleNavigation(fc, null, "/error/error?faces-redirect=true");
                    fc.renderResponse();
                } finally {
                    iterator.remove();
                }
            }
        }

        // Let the parent handle the rest
        getWrapped().handle();
    }

}
