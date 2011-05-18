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
 * User: Martin Schicht
 * Date: 04.05.11
 * Time: 08:48
 * To change this template use File | Settings | File Templates.
 */
public class KallenderValidator implements Validator {

    MessagePropertiesBean msgPB = new MessagePropertiesBean();

 public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

        MessagePropertiesBean msgPB = new MessagePropertiesBean();
        String msg_1 = msgPB.getPropertiesMessage("ungueltigesDatum");

        GPicSUtil.createFacesMessageForID("kalval",msg_1, true);
        FacesMessage msg = new FacesMessage(msg_1);

        String pattern = "(0[1-9]|[12][0-9]|3[01])[.](0[1-9]|1[012])[.](19|20)\\d\\d";

            if(((String) o).matches(pattern)){

            throw new ValidatorException(msg);
        }
    }
}

