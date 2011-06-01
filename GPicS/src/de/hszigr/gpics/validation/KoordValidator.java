package de.hszigr.gpics.validation;

import de.hszigr.gpics.util.GPicSUtil;
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
         if(!koord.matches("-?[0-9]{1,3}.[0-9]{1,20}") && !koord.matches("-?[0-9]{1,3}\\.[0-9]{20}")) {
            MessagePropertiesBean msgPB = new MessagePropertiesBean();
            String msg_1 = msgPB.getPropertiesMessage("ungueltigeKoordinaten");
            GPicSUtil.createFacesMessageForID("kooval", msg_1, true);
            FacesMessage msg = new FacesMessage(msg_1);
            throw new ValidatorException(msg);
        }
    }


}