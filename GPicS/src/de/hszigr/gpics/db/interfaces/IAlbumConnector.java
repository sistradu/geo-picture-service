package de.hszigr.gpics.db.interfaces;

import org.w3c.dom.Document;

import java.net.ConnectException;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 27.04.11
 * Time: 08:13
 * To change this template use File | Settings | File Templates.
 */
public interface IAlbumConnector {
    /**
     * Trägt ein neues Album in die Datenbank ein.
     *
     * @param name
     * @param password
     * @param description
     * @throws ConnectException falls keine Verbindung mit der Datenbank hergestellt werden konnte.
     * @throws IllegalArgumentException falls ein Album mit dem selben Namen bereits exisiert.
     */
    public int createAlbum(String name, String password, String description, int NutzerID) throws ConnectException, IllegalArgumentException;

    /**
     * Ändert die Daten zu einem Album in der Datenbank.
     * @param id
     * @param name
     * @param password
     * @param description
     * @throws ConnectException falls keine Verbindung mit der Datenbank hergestellt werden konnte.
     * @throws IllegalArgumentException falls kein Album mit der angegebenen id oder dem angegebenen Namen existiert.
     */
    public void updateAlbum(int id, String name, String password, String description) throws ConnectException, IllegalArgumentException;

    /**
     * Löscht das Album in der Datenbank mit der angegebenen id.
     * @param id
     * @throws ConnectException falls keine Verbindung mit der Datenbank hergestellt werden konnte.
     * @throws IllegalArgumentException falls kein Album mit der angegebenen id existiert.
     */
    public void deleteAlbum(int id) throws ConnectException, IllegalArgumentException;

    /**
     * Löscht das Album in der Datenbank mit dem angegebenen Namen.
     * @param name
     * @throws ConnectException falls keine Verbindung mit der Datenbank hergestellt werden konnte.
     * @throws IllegalArgumentException falls kein Album mit dem angegebenen namen existiert.
     */
    public void deleteAlbum(String name) throws ConnectException, IllegalArgumentException;

    /**
     * Liefert das Album mit der angegebenen id.
     * @param id
     * @return
     * @throws ConnectException falls keine Verbindung mit der Datenbank hergestellt werden konnte.
     * @throws IllegalArgumentException falls kein Album mit der angegebenen id existiert.
     */
    public Document getAlbumByID(int id) throws ConnectException, IllegalArgumentException;

    /**
     * Liefert das Album mit dem angegebenen Namen.
     * @param name
     * @return
     * @throws ConnectException falls keine Verbindung mit der Datenbank hergestellt werden konnte.
     * @throws IllegalArgumentException falls kein Album mit dem angegebenen namen existiert.
     */
    public Document getAlbumByName(String name) throws ConnectException, IllegalArgumentException;

    /**
     * Liefert alle Alben.
     * @return
     * @throws ConnectException falls keine Verbindung mit der Datenbank hergestellt werden konnte.
     */
    public Document getAllAlben() throws ConnectException;

    /**
     * Liefert alle Alben des angegebenen Nutzers.
     * @param nutzerID
     * @return
     * @throws ConnectException falls keine Verbindung mit der Datenbank hergestellt werden konnte.
     * @throws IllegalArgumentException falls der angegebene Nutzer nicht existiert.
     */
    public Document getAlbenForNutzer(int nutzerID) throws ConnectException, IllegalArgumentException;

    /**
     * Liefert alle Alben, die im Namen den angegebenen String enthalten.
     * @param name
     * @return
     * @throws ConnectException falls keine Verbindung mit der Datenbank hergestellt werden konnte.
     */
    public Document getAlbenWithNameContaining(String name) throws ConnectException;

    /**
     * Liefert alle Alben, die in der Beschreibung den angegebenen String enthalten.
     * @param description
     * @return
     * @throws ConnectException falls keine Verbindung mit der Datenbank hergestellt werden konnte.
     */
    public Document getAlbenWithDescriptionContaining(String description) throws ConnectException;

}
