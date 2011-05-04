package de.hszigr.gpics.db.interfaces;

import org.w3c.dom.Document;

import java.net.ConnectException;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 26.04.11
 * Time: 22:46
 * To change this template use File | Settings | File Templates.
 */
public interface INutzerConnector {

    /**
     * Trägt einen Nutzer in die Datenbank ein.
     * @param name
     * @param password
     * @param email
     * @throws IllegalArgumentException falls der Nutzername bereits existiert oder benötigte Parameter null sind.
     * @throws ConnectException falls keine Verbindung mit der Datenbank aufgebaut werden konnte.
     */
    public int createNutzer(String name, String password, String email) throws IllegalArgumentException, ConnectException;

    /**
     * Ändert die Daten zu einem Nutzer in der Datenbank.
     * @param id
     * @param name
     * @param password
     * @param email
     * @throws IllegalArgumentException falls der Nutzer mit der angegebenen id, bzw. dem Namen nicht existiert oder id und name null sind.
     * @throws ConnectException falls keine Verbindung mit der Datenbank aufgebaut werden konnte.
     */
    public void updateNutzer(int id, String name, String password, String email) throws IllegalArgumentException, ConnectException;

    /**
     * Löscht einen Nutzer anhand seiner id in der Datenbank.
     * @param id
     * @throws IllegalArgumentException falls der Nutzer mit der angegebenen id nicht existiert.
     * @throws ConnectException falls keine Verbindung mit der Datenbank aufgebaut werden konnte.
     */
    public void deleteNutzer(int id) throws IllegalArgumentException, ConnectException;

    /**
     * Löscht den Nutzer mit dem angegebenen Namen.
     * @param name
     * @throws IllegalArgumentException falls der Nutzer mit dem angegebenen namen nicht existiert.
     * @throws ConnectException falls keine Verbindung mit der Datenbank aufgebaut werden konnte.
     */
    public void deleteNutzer(String name) throws IllegalArgumentException, ConnectException;

    /**
     * Liefert den Nutzer mit der angegebene id.
     * @param id
     * @return
     * @throws IllegalArgumentException falls der Nutzer mit der angegebenen id nicht existiert.
     * @throws ConnectException falls keine Verbindung mit der Datenbank aufgebaut werden konnte.
     */
    public Document getNutzerByID(int id) throws IllegalArgumentException, ConnectException;

    /**
     * Liefert den Nutzer mit dem angegebenen Namen.
     * @param name
     * @return
     * @throws IllegalArgumentException falls der Nutzer mit dem angegebenen namen nicht existiert.
     * @throws ConnectException falls keine Verbindung mit der Datenbank aufgebaut werden konnte.
     */
    public Document getNutzerByName(String name) throws IllegalArgumentException, ConnectException;

    /**
     * Liefert alle Nutzer.
     * @return
     * @throws ConnectException falls keine Verbindung mit der Datenbank aufgebaut werden konnte.
     */
    public Document getAllNutzer() throws ConnectException;

}
