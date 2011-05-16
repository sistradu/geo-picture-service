package de.hszigr.gpics.db.connect;

import de.hszigr.gpics.db.interfaces.INutzerConnector;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 13.05.11
 * Time: 10:16
 * To change this template use File | Settings | File Templates.
 */
public class NutzerConnector implements INutzerConnector{

     public int createNutzer(String name, String password, String email) throws IllegalArgumentException, ConnectException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("name", name);
        params.put("password", password);
        params.put("email", email);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/createNutzer.xql", params, 0);
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

    public void updateNutzer(int id, String name, String password, String email) throws IllegalArgumentException, ConnectException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("id", ""+ id);
        params.put("name", name);
        params.put("pasword", password);
        params.put("email", email);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/updateNutzer.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
            }catch(SAXException ex){
            //OK
        }
    }

    public void deleteNutzer(int id) throws IllegalArgumentException, ConnectException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("id", ""+id);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/deleteNutzer.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
            }catch(SAXException ex){
            //OK
        }
    }

    public void deleteNutzer(String name) throws IllegalArgumentException, ConnectException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("name", ""+name);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/deleteNutzerWithName.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
            }catch(SAXException ex){
            //OK
        }
    }

    public Document getNutzerByID(int id) throws IllegalArgumentException, ConnectException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("id", ""+id);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/nutzerByID.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
        return doc;
        }catch(SAXException ex){
            throw new ConnectException("Keine oder fehlerhafte Daten empfangen!");
        }
    }

    public Document getNutzerByName(String name) throws IllegalArgumentException, ConnectException {
        Map<String,String> params = new HashMap<String,String>();
        params.put("name", name);
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/nutzerByName.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
        return doc;
        }catch(SAXException ex){
            throw new ConnectException("Keine oder fehlerhafte Daten empfangen!");
        }
    }

    public Document getAllNutzer() throws ConnectException {
        Map<String,String> params = new HashMap<String,String>();
        try{
        Document doc = DBConnector.getInstance().executeGetRequest("queries/allNutzer.xql", params, 0);
        if(doc.getElementsByTagName("error").getLength()>0){
            throw new IllegalArgumentException(doc.getElementsByTagName("message").item(0).getTextContent());
        }
        return doc;
        }catch(SAXException ex){
            throw new ConnectException("Keine oder fehlerhafte Daten empfangen!");
        }
    }

}
