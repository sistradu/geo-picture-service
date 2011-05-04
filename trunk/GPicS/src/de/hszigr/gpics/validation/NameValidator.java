package de.hszigr.gpics.validation;

import de.hszigr.gpics.util.MessagePropertiesBean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich     & Martin Schicht
 * Date: 17.04.11
 * Time: 16:21
 * To change this template use File | Settings | File Templates.
 */

public class NameValidator implements Validator {

    MessagePropertiesBean msgPB = new MessagePropertiesBean();

    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String name = (String) o;
         if(!name.matches("[\\w\\d]+") || name.matches("[\\d]+")) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary(msgPB.getPropertiesMessage("ungueltigerBenutzerName"));
            message.setDetail(msgPB.getPropertiesMessage("ungueltigerBenutzerNamedetail"));
            facesContext.addMessage("userForm:Name", message);
            throw new ValidatorException(message);
        }
    }
}