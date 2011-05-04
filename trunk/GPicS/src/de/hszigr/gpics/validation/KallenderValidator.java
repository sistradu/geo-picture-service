package de.hszigr.gpics.validation;

import de.hszigr.gpics.util.MessagePropertiesBean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Created by IntelliJ IDEA.
 * User: Martin Schicht
 * Date: 04.05.11
 * Time: 08:48
 * To change this template use File | Settings | File Templates.
 */
public class KallenderValidator implements Validator {

    MessagePropertiesBean msgPB = new MessagePropertiesBean();

 public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String datum = (String) o;
         if(!datum.matches("(0[1-9]|[12][0-9]|3[01])[.](0[1-9]|1[012])[.](19|20)\\d\\d")) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary(msgPB.getPropertiesMessage("ungueltigesDatum"));
            message.setDetail(msgPB.getPropertiesMessage("ungueltigesDatumdetail"));
            facesContext.addMessage("userForm:Name", message);
            throw new ValidatorException(message);
        }
    }
}

