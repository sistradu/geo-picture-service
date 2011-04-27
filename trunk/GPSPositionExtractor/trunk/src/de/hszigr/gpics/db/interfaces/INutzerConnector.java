package de.hszigr.gpics.db.interfaces;

import org.w3c.dom.Document;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 26.04.11
 * Time: 22:46
 * To change this template use File | Settings | File Templates.
 */
public interface INutzerConnector {

    public void createNutzer(String name, String password, String email);

    public void updateNutzer(int id, String name, String password, String email);

    public void deleteNutzer(int id);

    public void deleteNutzer(String name);

    public Document getNutzerByID(int id);

    public Document getNutzerByName(String name);

    public Document getAllNutzer();

}
