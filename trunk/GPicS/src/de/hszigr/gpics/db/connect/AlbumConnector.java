package de.hszigr.gpics.db.connect;

import de.hszigr.gpics.db.interfaces.IAlbumConnector;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 13.05.11
 * Time: 10:14
 * To change this template use File | Settings | File Templates.
 */
public class AlbumConnector implements IAlbumConnector{

    public int createAlbum(String name, String password, String description, int nutzerID) throws ConnectException, IllegalArgumentException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("name", name);
        params.put("pasword", password);
        params.put("description", description);
        params.put("nutzer", ""+nutzerID);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/createAlbum.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
        if(doc.getElementsByTagName("id").getLength()>0){
            return Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent());
        }else{
            throw new ConnectException("Es konnte keine neue ID generiert werden!");
        }
            }catch(SAXException ex){
            throw new ConnectException("Keine oder fehlerhafte Daten empfangen!");
        }
    }

    public void updateAlbum(int id, String name, String password, String description) throws ConnectException, IllegalArgumentException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("id", ""+ id);
        params.put("name", name);
        params.put("pasword", password);
        params.put("description", description);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/updateAlbum.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
            }catch(SAXException ex){
            //OK
        }
    }

    public void deleteAlbum(int id) throws ConnectException, IllegalArgumentException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("id", ""+id);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/deleteAlbum.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
            }catch(SAXException ex){
            //OK
        }
    }

    public void deleteAlbum(String name) throws ConnectException, IllegalArgumentException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("name", name);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/deleteAlbumWithName.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
        }catch(SAXException ex){
            //OK
        }
    }

    public Document getAlbumByID(int id) throws ConnectException, IllegalArgumentException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("id", ""+id);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/albumByID.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
        return doc;
        }catch(SAXException ex){
            throw new ConnectException("Keine oder fehlerhafte Daten empfangen!");
        }
    }

    public Document getAlbumByName(String name) throws ConnectException, IllegalArgumentException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("name", name);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/albumByName.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
        return doc;
        }catch(SAXException ex){
            throw new ConnectException("Keine oder fehlerhafte Daten empfangen!");
        }
    }

    public Document getAllAlben() throws ConnectException {
        Map<String,String> params = new HashMap<String,String>();
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/allAlben.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
        return doc;
        }catch(SAXException ex){
            throw new ConnectException("Keine oder fehlerhafte Daten empfangen!");
        }
    }

    public Document getAlbenForNutzer(int nutzerID) throws ConnectException, IllegalArgumentException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("nutzer", ""+nutzerID);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/albenForNutzer.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
        return doc;
        }catch(SAXException ex){
            throw new ConnectException("Keine oder fehlerhafte Daten empfangen!");
        }
    }

    public Document getAlbenWithNameContaining(String name) throws ConnectException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("name", name);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/albumWithNameContaining.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
        return doc;
            }catch(SAXException ex){
            throw new ConnectException("Keine oder fehlerhafte Daten empfangen!");
        }
    }

    public Document getAlbenWithDescriptionContaining(String description) throws ConnectException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("description", description);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/albumWithDescriptionContaining.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
        return doc;
        }catch(SAXException ex){
            throw new ConnectException("Keine oder fehlerhafte Daten empfangen!");
        }
    }

}
