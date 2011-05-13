package de.hszigr.gpics.db.connect;

import de.hszigr.gpics.db.interfaces.IAlbumConnector;
import de.hszigr.gpics.db.interfaces.IBildConnector;
import de.hszigr.gpics.db.interfaces.INutzerConnector;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.mail.internet.NewsAddress;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 11.05.11
 * Time: 14:46
 * To change this template use File | Settings | File Templates.
 */
public class DBConnector {

    private static final String location = "http://193.174.103.76:8088/exist/rest/db/";

    private DBConnector(){

    }

    private static DBConnector INSTANCE = new DBConnector();

    public static DBConnector getInstance(){
        return DBConnector.INSTANCE;
    }

    public synchronized Document executeGetRequest(String file, Map<String,String> params, int amount) throws IllegalArgumentException, ConnectException, SAXException{
        Authenticator.setDefault(new ExistAuthentificator());
        Document doc;
        try{
            String paramString = "";
            boolean firstSet = false;
            for(Map.Entry<String,String> entry : params.entrySet()){
                if(entry.getValue() != null && !entry.getValue().isEmpty()){
                    if(firstSet){
                        paramString += "&" + entry.getKey() + "=" + entry.getValue();
                    }else{
                        paramString += "?" + entry.getKey() + "=" + entry.getValue();
                        firstSet = true;
                    }
                }
            }
            URL url = new URL(DBConnector.location + file + paramString);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(url.openStream());
        }catch(MalformedURLException mex){
            System.out.println("URL was not correct!");
            throw new IllegalArgumentException("Keine gültige URL angegeben!", mex);
        }catch(IOException iex){
            System.out.println("I/O Error occured!");
            throw new ConnectException("Fehler bei der Datenübermittlung!");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw new ConnectException("Fehler beim Empfangen der Daten!");
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw e;
        }
        return doc;
    }
}
