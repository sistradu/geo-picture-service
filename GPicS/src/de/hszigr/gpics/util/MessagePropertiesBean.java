package de.hszigr.gpics.util;

import javax.faces.context.FacesContext;

/**
 * Created by IntelliJ IDEA.
 * User: Rico Scholz
 * Date: 27.04.11
 * Time: 18:17
 * To change this template use File | Settings | File Templates.
 */
public class MessagePropertiesBean {

	public String getPropertiesMessage(String key) {

		FacesContext context = FacesContext.getCurrentInstance();

		String text = GPicSUtil.getMessageResourceString(context.getApplication()
                .getMessageBundle(), key, null, context.getViewRoot()
                .getLocale());

		return text;
	}

}
