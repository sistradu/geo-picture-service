package de.hszigr.gpics.db.connect;

import de.hszigr.gpics.db.interfaces.IBildConnector;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 13.05.11
 * Time: 10:15
 * To change this template use File | Settings | File Templates.
 */
public class BildConnector implements IBildConnector{

    public int createBild(String name, String description, boolean isPublic, GregorianCalendar date, String filePosition, String longitude, String latitude, String altitude, String direction, int albumID) throws ConnectException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("name", name);
        params.put("description", description);
        params.put("ispublic", ""+isPublic);
        params.put("date", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date.getTime()));
        params.put("fileposition", filePosition);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("altitude", altitude);
        params.put("direction", direction);
        params.put("album", "" + albumID);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/createBild.xql", params, 0);
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

    public void updateBild(int id, String name, String description, boolean isPublic, GregorianCalendar date, String filePosition, String longitude, String latitude, String altitude, String direction) throws ConnectException, IllegalArgumentException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("name", name);
        params.put("description", description);
        params.put("ispublic", ""+isPublic);
        params.put("date", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date));
        params.put("fileposition", filePosition);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("altitude", altitude);
        params.put("direction", direction);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/updateBild.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
            }catch(SAXException ex){
            //OK
        }
    }

    public void deleteBild(int id) throws ConnectException, IllegalArgumentException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("id", ""+id);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/deleteBild.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
            }catch(SAXException ex){
            //OK
        }
    }

    public void deleteBilderFromAlbum(int albumID) throws ConnectException, IllegalArgumentException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("album", ""+albumID);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/deleteBilderFromAlbum.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
            }catch(SAXException ex){
            //OK
        }
    }

    public Document getBildByID(int id) throws ConnectException, IllegalArgumentException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("id", ""+id);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/bildByID.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
        return doc;
        }catch(SAXException ex){
            throw new ConnectException("Keine oder fehlerhafte Daten empfangen!");
        }
    }

    public Document getBilderByName(String name) throws ConnectException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("name", name);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/bilderByName.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
        return doc;
        }catch(SAXException ex){
            throw new ConnectException("Keine oder fehlerhafte Daten empfangen!");
        }
    }

    public Document getBilderForAlbum(int albumID) throws ConnectException, IllegalArgumentException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("album", ""+albumID);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/bilderForAlbum.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
        return doc;
        }catch(SAXException ex){
            throw new ConnectException("Keine oder fehlerhafte Daten empfangen!");
        }
    }

    public Document getBilderForRundreise(int rundreiseID) throws ConnectException, IllegalArgumentException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
