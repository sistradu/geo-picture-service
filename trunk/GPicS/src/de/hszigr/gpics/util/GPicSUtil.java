package de.hszigr.gpics.util;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan Radusch
 * Date: 11.05.11
 * Time: 10:49
 * To change this template use File | Settings | File Templates.
 */
public class GPicSUtil {

    public static StreamedContent getStreamContent(String path) throws IOException {
        InputStream stream = new FileInputStream(path);
        StreamedContent image = new DefaultStreamedContent(stream, "image/jpeg");
        return image;
    }

    /**
     * generiert eine FacesMessage, die beim h:message-Element mit der id <elementID> angezeigt wird
     * @param elementID
     * @param errorMessage
     */
    public static void createFacesMessageForID(String elementID, String errorMessage){
        FacesMessage message = new FacesMessage(errorMessage);
        FacesContext.getCurrentInstance().addMessage(elementID,  message);
    }
}
