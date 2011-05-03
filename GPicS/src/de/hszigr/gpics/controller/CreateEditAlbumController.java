package de.hszigr.gpics.controller;

import de.hszigr.gpics.util.PasswortUtil;

import javax.faces.bean.ManagedBean;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan Radusch
 * Date: 28.04.11
 * Time: 20:24
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
public class CreateEditAlbumController {

    private int albumID;
    private String albumName;
    private String albumBeschreibung;
    private String passwort;

    public void generatePasswort(){
        PasswortUtil util = new PasswortUtil();
        String randomPasswort = util.erzeugeZufallsPasswort(4);
        this.passwort = util.encryptWithMD5(randomPasswort);
    }

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumBeschreibung() {
        return albumBeschreibung;
    }

    public void setAlbumBeschreibung(String albumBeschreibung) {
        this.albumBeschreibung = albumBeschreibung;
    }

    public String getPasswort() {
        return passwort;
    }
}
