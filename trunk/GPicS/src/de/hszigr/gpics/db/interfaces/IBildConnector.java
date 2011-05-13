package de.hszigr.gpics.db.interfaces;

import org.w3c.dom.Document;

import java.net.ConnectException;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 27.04.11
 * Time: 08:13
 * To change this template use File | Settings | File Templates.
 */
public interface IBildConnector {

    /**
     * Trägt ein neues Bild in die Datenbank ein.
     * @param name
     * @param description
     * @param isPublic
     * @param date
     * @param filePosition
     * @param longitude
     * @param latitude
     * @param altitude
     * @param direction
     * @param albumID
     * @return Die id des eingetragenen Bildes.
     * @throws ConnectException falls keine Verbindung mit der Datenbank aufgebaut werden konnte.
     */
    public int createBild(String name, String description, boolean isPublic, GregorianCalendar date, String filePosition, String longitude, String latitude, String altitude, String direction, int albumID) throws ConnectException;

    /**
     * Ändert das Bild mit der angegebenen id.
     * @param id
     * @param name
     * @param description
     * @param isPublic
     * @param date
     * @param longitude
     * @param latitude
     * @param altitude
     * @param direction
     * @throws ConnectException falls keine Verbindung mit der Datenbank aufgebaut werden konnte.
     * @throws IllegalArgumentException falls das Bild mit der angegebenen id nicht existiert.
     */
    public void updateBild(int id, String name, String description, boolean isPublic, GregorianCalendar date, String filePosition, String longitude, String latitude, String altitude, String direction) throws ConnectException, IllegalArgumentException;

    /**
     * Löscht das Bild mit der angegebenen id.
     * @param id
     * @throws ConnectException falls keine Verbindung mit der Datenbank aufgebaut werden konnte.
     * @throws IllegalArgumentException  falls das Bild mit der angegebenen id nicht existiert.
     */
    public void deleteBild(int id) throws ConnectException, IllegalArgumentException;

    /**
     * Löscht alle Bilder des angegebenen Albums.
     * @param album
     * @throws ConnectException falls keine Verbindung mit der Datenbank aufgebaut werden konnte.
     * @throws IllegalArgumentException  falls das Album nicht existiert.
     */
    public void deleteBilderFromAlbum(int albumID) throws ConnectException, IllegalArgumentException;

    /**
     * Liefert das Bild mit der angegebenen id.
     * @param id
     * @return
     * @throws ConnectException falls keine Verbindung mit der Datenbank aufgebaut werden konnte.
     * @throws IllegalArgumentException  falls das Bild mit der angegebenen id nicht existiert.
     */
    public Document getBildByID(int id) throws ConnectException, IllegalArgumentException;

    /**
     * Liefert alle Bilder mit dem angegebenen Namen (auch wenn dieser nur enthalten ist).
     * @param name
     * @return
     * @throws ConnectException falls keine Verbindung mit der Datenbank aufgebaut werden konnte.
     */
    public Document getBilderByName(String name) throws ConnectException;

    /**
     * Liefert alle Bilder für das angegebene Album.
     * @param album
     * @return
     * @throws ConnectException falls keine Verbindung mit der Datenbank aufgebaut werden konnte.
     * @throws IllegalArgumentException  falls das Album nicht existiert.
     */
    public Document getBilderForAlbum(int albumID) throws ConnectException, IllegalArgumentException;

    /**
     * Liefert alle Bilder für die angegebene Rundreise.
     * @param rundreise
     * @return
     * @throws ConnectException falls keine Verbindung mit der Datenbank aufgebaut werden konnte.
     * @throws IllegalArgumentException  falls die Rundreise nicht existiert.
     */
    public Document getBilderForRundreise(int rundreiseID) throws ConnectException, IllegalArgumentException;

}
