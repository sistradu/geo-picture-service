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
 * Date: 18.04.11
 * Time: 00:03
 * To change this template use File | Settings | File Templates.
 */
public class KoordValidator implements Validator {

   MessagePropertiesBean msgPB = new MessagePropertiesBean();

    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String koord = (String) o;
         if(!koord.matches("-?[0-9]{1,3}°[0-9]{1,2}'[0-9]{1,2}''") && !koord.matches("-?[0-9]{1,3}\\.[0-9]{6}")) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary(msgPB.getPropertiesMessage("ungueltigeKoordinaten"));
            message.setDetail(msgPB.getPropertiesMessage("nurgueltigeKoordinaten"));
            facesContext.addMessage("userForm:Koords", message);
            throw new ValidatorException(message);
        }
    }
}