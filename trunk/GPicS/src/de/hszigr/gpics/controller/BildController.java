package de.hszigr.gpics.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Snookerms
 * Date: 27.04.11
 * Time: 09:32
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@RequestScoped
public class BildController {

    private int bildID;
    private String bildName;
    private String beschreibung;
    private boolean oeffentlich = false;
    private Date timestamp = new java.util.Date();
    private String latitude;
    private String longitude;
    private String altitude;
    private String direction;


    public int getBildID() {
        return bildID;
    }

    public void setBildID(int bildID) {
        this.bildID = bildID;
    }

    public String getBildName() {
        return bildName;
    }

    public void setBildName(String bildName) {
        this.bildName = bildName;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public boolean isOeffentlich() {
        return oeffentlich;
    }

    public void setOeffentlich(boolean oeffentlich) {
        this.oeffentlich = oeffentlich;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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
