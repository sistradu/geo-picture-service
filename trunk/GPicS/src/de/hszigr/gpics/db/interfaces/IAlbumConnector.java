package de.hszigr.gpics.db.interfaces;

import org.w3c.dom.Document;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 27.04.11
 * Time: 08:13
 * To change this template use File | Settings | File Templates.
 */
public interface IAlbumConnector {

    public void createAlbum(String name, String password, String description);

    public void updateAlbum(int id, String name, String password, String description);

    public void deleteAlbum(int id);

    public void deleteAlbum(String name);

    public Document getAlbum(int id);

    public Document getAlbum(String name);

    public Document getAllAlben();

    public Document getAlbenForNutzer(Document nutzer);

    public Document getAlbenWithNameContaining(String name);

    public Document getAlbenWithDescriptionContaining(String description);

}
