package de.hszigr.gpics.validation;

import de.hszigr.gpics.util.MessagePropertiesBean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich  & Martin Schicht
 * Date: 17.04.11
 * Time: 19:26
 * To change this template use File | Settings | File Templates.
 */

public class EmailValidator implements Validator {

    MessagePropertiesBean msgPB = new MessagePropertiesBean();

    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String email = (String) o;
         if(!email.matches("([\\w\\d]+[-\\.]{1})*[\\w\\d]+@([\\w\\d-]+\\.)+[\\w]{2,4}")) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary(msgPB.getPropertiesMessage("emailungueltig"));
            message.setDetail(msgPB.getPropertiesMessage("gueltigeAdresseeingeben"));
            facesContext.addMessage("userForm:EMail", message);
            throw new ValidatorException(message);
        }
    }
}

