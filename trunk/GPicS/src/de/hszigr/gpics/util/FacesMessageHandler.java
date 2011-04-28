package de.hszigr.gpics.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan Radusch
 * Date: 27.04.11
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
public class FacesMessageHandler {

    public static void createFacesMessageForID(String elementID, String errorMessage){
        FacesMessage message = new FacesMessage(errorMessage);
        FacesContext.getCurrentInstance().addMessage(elementID,  message);
    }
}
