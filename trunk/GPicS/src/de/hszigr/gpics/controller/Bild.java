package de.hszigr.gpics.controller;

import com.sun.xml.internal.ws.developer.Stateful;
import org.primefaces.model.StreamedContent;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan Radusch
 * Date: 04.05.11
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */
public class Bild implements Serializable{

    private int bildID;
    private String name;
    private String beschreibung;
    private boolean isPublic;
    private GregorianCalendar date;
    private String longitude;
    private String latitude;
    private String altitude;
    private String direction;
    private String path;
    private StreamedContent content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public StreamedContent getContent() {
        return content;
    }

    public void setContent(StreamedContent content) {
        this.content = content;
    }

    public int getBildID() {
        return bildID;
    }

    public void setBildID(int bildID) {
        this.bildID = bildID;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
