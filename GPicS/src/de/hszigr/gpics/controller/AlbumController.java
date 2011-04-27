package de.hszigr.gpics.controller;

import org.w3c.dom.Document;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Rico Scholz
 * Date: 27.04.11
 * Time: 10:03
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@SessionScoped
public class AlbumController {

    private String sucheNach;
    private String suchString;
    private Document alben;

    public String getSucheNach() {
        return sucheNach;
    }

    public void setSucheNach(String sucheNach) {
        this.sucheNach = sucheNach;
    }

    public String getSuchString() {
        return suchString;
    }

    public void setSuchString(String suchString) {
        this.suchString = suchString;
    }

    public Document getAlben() {
        return alben;
    }

    public void setAlben(Document alben) {
        this.alben = alben;
    }
}
