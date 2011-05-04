package de.hszigr.gpics.db;

import de.hszigr.gpics.db.interfaces.IAlbumConnector;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.net.ConnectException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 27.04.11
 * Time: 11:15
 * To change this template use File | Settings | File Templates.
 */
public class MockAlbumConnector implements IAlbumConnector {

    private final HashMap<Integer, Document> albenIDMap;
    private final HashMap<String, Document> albenNameMap;
    private final HashMap<String, Document> albenNutzerNameMap;
    private final HashMap<String, Document> albenDescriptionMap;
    int id = -1;

    public MockAlbumConnector(){
        this.albenIDMap = new HashMap<Integer,Document>();
        this.albenNameMap = new HashMap<String,Document>();
        this.albenNutzerNameMap = new HashMap<String,Document>();
        this.albenDescriptionMap = new HashMap<String,Document>();
        String password = "goerlitz";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        this.addAlbum("Görlitz", password, "Bilder über die Stadt Görlitz.", new String[]{"1","2","3","4"}, "Karl");
        password = "zittau";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        this.addAlbum("Zittau", password, "Bilder über die Stadt Zittau.", new String[]{"5","6","8","10"}, "Karl");
        password = "oberlausitz";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        this.addAlbum("Oberlausitz", password, "Bilder über die schöne Oberlausitz.", new String[]{"7","9","11","12"}, "Günther");
    }

    private void addAlbum(String name, String password, String description, String[] bilder, String nutzername){
        this.id++;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().newDocument();
            Element idElem = doc.createElement("id");
            Element nameElem = doc.createElement("name");
            Element passwordElem = doc.createElement("password");
            Element descriptionElem = doc.createElement("description");
            idElem.setTextContent(""+this.id);
            nameElem.setTextContent(name);
            passwordElem.setTextContent(password);
            descriptionElem.setTextContent(description);
            Node albumNode = doc.createElement("album");
            albumNode.appendChild(idElem);
            albumNode.appendChild(nameElem);
            albumNode.appendChild(passwordElem);
            albumNode.appendChild(descriptionElem);
            for(String bild : bilder){
                Element albumElem = doc.createElement("bild");
                albumElem.setAttribute("id", bild);
                albumNode.appendChild(albumElem);
            }
            doc.appendChild(albumNode);
            this.albenIDMap.put(this.id, doc);
            this.albenNameMap.put(name, doc);
            this.albenNutzerNameMap.put(nutzername + this.id, doc);
            this.albenDescriptionMap.put(description, doc);
        } catch (ParserConfigurationException e) {
            System.out.println("Fehler beim Erstellen des Albums " + name + ".");
            e.printStackTrace();
        }
    }

    public void createAlbum(String name, String password, String description) throws ConnectException, IllegalArgumentException {
        //can do nothing
    }

    public void updateAlbum(int id, String name, String password, String description) throws ConnectException, IllegalArgumentException {
        //can do nothing
    }

    public void deleteAlbum(int id) throws ConnectException, IllegalArgumentException {
        //can do nothing
    }

    public void deleteAlbum(String name) throws ConnectException, IllegalArgumentException {
        //can do nothing
    }

    public Document getAlbum(int id) throws ConnectException, IllegalArgumentException {
        if(this.albenIDMap.containsKey(id))
            return this.albenIDMap.get(id);
        else
            throw new IllegalArgumentException("");
    }

    public Document getAlbum(String name) throws ConnectException, IllegalArgumentException {
        return this.albenNameMap.get(name);
    }

    public Document getAllAlben() throws ConnectException {
        try {
            //TODO mit Hilfe des Schemas erzeugen...
            //Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI).newSchema(new File("alben.xsd"));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //factory.setSchema(schema);
            Document doc = factory.newDocumentBuilder().newDocument();
            Node albenNode = doc.createElement("alben");
            for(Map.Entry<Integer,Document> entry : this.albenIDMap.entrySet()){
                albenNode.appendChild(doc.adoptNode(entry.getValue().getFirstChild()));
            }
            doc.appendChild(albenNode);
            return doc;
        } catch (ParserConfigurationException e) {
            System.out.println("Fehler beim Erstellen des Dokuments für alle Alben.");
            e.printStackTrace();
        }// catch (SAXException e) {
         //   System.out.println("Fehler beim Laden des Schemas für alle Nutzer.");
         //   e.printStackTrace();
        //}
        return null;
    }

    public Document getAlbenForNutzer(Document nutzer) throws ConnectException, IllegalArgumentException {
        try {
            //TODO mit Hilfe des Schemas erzeugen...
            //Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI).newSchema(new File("nutzer.xsd"));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //factory.setSchema(schema);
            Document doc = factory.newDocumentBuilder().newDocument();
            Node albenNode = doc.createElement("alben");
            for(Map.Entry<String,Document> entry : this.albenNutzerNameMap.entrySet()){
                if(entry.getKey().startsWith(nutzer.getElementsByTagName("name").item(0).getTextContent()))
                    albenNode.appendChild(doc.adoptNode(entry.getValue().getFirstChild()));
            }
            doc.appendChild(albenNode);
            return doc;
        } catch (ParserConfigurationException e) {
            System.out.println("Fehler beim Erstellen des Dokuments für alle Alben.");
            e.printStackTrace();
        }// catch (SAXException e) {
         //   System.out.println("Fehler beim Laden des Schemas für alle Nutzer.");
         //   e.printStackTrace();
        //}
        return null;
    }

    public Document getAlbenWithNameContaining(String name) throws ConnectException {
        try {
            //TODO mit Hilfe des Schemas erzeugen...
            //Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI).newSchema(new File("nutzer.xsd"));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //factory.setSchema(schema);
            Document doc = factory.newDocumentBuilder().newDocument();
            Node albenNode = doc.createElement("alben");
            for(Map.Entry<String,Document> entry : this.albenNameMap.entrySet()){
                if(entry.getKey().contains(name))
                    albenNode.appendChild(doc.adoptNode(entry.getValue().getFirstChild()));
            }
            doc.appendChild(albenNode);
            return doc;
        } catch (ParserConfigurationException e) {
            System.out.println("Fehler beim Erstellen des Dokuments für alle Alben.");
            e.printStackTrace();
        }// catch (SAXException e) {
         //   System.out.println("Fehler beim Laden des Schemas für alle Nutzer.");
         //   e.printStackTrace();
        //}
        return null;
    }

    public Document getAlbenWithDescriptionContaining(String description) throws ConnectException {
        try {
            //TODO mit Hilfe des Schemas erzeugen...
            //Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI).newSchema(new File("nutzer.xsd"));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //factory.setSchema(schema);
            Document doc = factory.newDocumentBuilder().newDocument();
            Node albenNode = doc.createElement("alben");
            for(Map.Entry<String,Document> entry : this.albenDescriptionMap.entrySet()){
                if(entry.getKey().contains(description))
                    albenNode.appendChild(doc.adoptNode(entry.getValue().getFirstChild()));
            }
            doc.appendChild(albenNode);
            return doc;
        } catch (ParserConfigurationException e) {
            System.out.println("Fehler beim Erstellen des Dokuments für alle Alben.");
            e.printStackTrace();
        }// catch (SAXException e) {
         //   System.out.println("Fehler beim Laden des Schemas für alle Nutzer.");
         //   e.printStackTrace();
        //}
        return null;
    }
}
