package de.hszigr.gpics.db;

import de.hszigr.gpics.db.interfaces.INutzerConnector;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 27.04.11
 * Time: 08:19
 * To change this template use File | Settings | File Templates.
 */
public class MockNutzerConnector implements INutzerConnector{

    private final HashMap<Integer, Document> nutzerIDMap;
    private final HashMap<String, Document> nutzerNameMap;
    int id = -1;

    public MockNutzerConnector(){
        this.nutzerIDMap = new HashMap<Integer,Document>();
        this.nutzerNameMap = new HashMap<String,Document>();
        this.addNutzer("Karl", "test", "karl@web.de", new String[]{"Görlitz","Zittau"});
        this.addNutzer("Günther", "passwort", "guenther@web.de", new String[]{"Oberlausitz"});
    }

    private void addNutzer(String name, String password, String email, String[] albums){
        this.id++;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().newDocument();
            Element nameElem = doc.createElement("name");
            Element passwordElem = doc.createElement("password");
            Element emailElem = doc.createElement("email");
            nameElem.setTextContent(name);
            passwordElem.setTextContent(password);//TODO verschlüsseln...
            emailElem.setTextContent(email);
            Node nutzerNode = doc.createElement("benutzer");
            nutzerNode.appendChild(nameElem);
            nutzerNode.appendChild(passwordElem);
            nutzerNode.appendChild(emailElem);
            for(String album : albums){
                Element albumElem = doc.createElement("album");
                albumElem.setAttribute("name", album);
                nutzerNode.appendChild(albumElem);
            }
            doc.appendChild(nutzerNode);
            this.nutzerIDMap.put(this.id, doc);
            this.nutzerNameMap.put(name, doc);
        } catch (ParserConfigurationException e) {
            //TODO Logger Ausgabe --> Fehler beim Erstellen von [name]
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void createNutzer(String name, String password, String email) {
        //can do nothing...
    }

    public void updateNutzer(int id, String name, String password, String email) {
        //can do nothing...
    }

    public void deleteNutzer(int id) {
        //can do nothing...
    }

    public void deleteNutzer(String name) {
        //can do nothing...
    }

    public Document getNutzerByID(int id) {
        return this.nutzerIDMap.get(id);
    }

    public Document getNutzerByName(String name) {
        return this.nutzerNameMap.get(name);
    }

    public Document getAllNutzer() {
        try {
            //TODO mit Hilfe des Schemas erzeugen...
            //Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI).newSchema(new File("nutzer.xsd"));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //factory.setSchema(schema);
            Document doc = factory.newDocumentBuilder().newDocument();
            Node nutzersNode = doc.createElement("benutzers");
            for(Map.Entry<Integer,Document> entry : this.nutzerIDMap.entrySet()){
                nutzersNode.appendChild(entry.getValue());
            }
            doc.appendChild(nutzersNode);
            return doc;
        } catch (ParserConfigurationException e) {
            //TODO Logger Ausgabe --> Fehler beim Erstellen des Dokuments
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }// catch (SAXException e) {
         //   //TODO Logger Ausgabe --> Fehler beim Parsen des Schemas
         //   e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        //}
        return null;
    }
}
