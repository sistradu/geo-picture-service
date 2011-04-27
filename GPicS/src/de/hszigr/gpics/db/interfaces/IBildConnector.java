package de.hszigr.gpics.db.interfaces;

import org.w3c.dom.Document;

import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 27.04.11
 * Time: 08:13
 * To change this template use File | Settings | File Templates.
 */
public interface IBildConnector {

    public void createBild(String name, String description, boolean isPublic, GregorianCalendar date, Document location);

    public void createLocation(String longitude, String latitude, String altitude, String direction);

    public void updateBild(int id, String name, String description, boolean isPublic, GregorianCalendar date, Document location);

    public void updateLocation(int id, String longitude, String latitude, String altitude, String direction);

    public void deleteBild(int id);

    public void deleteBilderFromAlbum(Document album);

    public Document getBildByID(int id);

    public Document getBildByName(String name);

    public Document getBilderForAlbum(Document album);

    //vorgemerkt
    public Document getBilderForRundreise(Document rundreise);

}
