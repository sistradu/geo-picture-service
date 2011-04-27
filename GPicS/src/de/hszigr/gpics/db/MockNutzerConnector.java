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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        String password = "test";
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
        this.addNutzer("Karl", password, "karl@web.de", new String[]{"Görlitz","Zittau"});
        password = "password";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        this.addNutzer("Günther", password, "guenther@web.de", new String[]{"Oberlausitz"});
    }

    private void addNutzer(String name, String password, String email, String[] albums){
        this.id++;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().newDocument();
            Element idElem = doc.createElement("id");
            Element nameElem = doc.createElement("name");
            Element passwordElem = doc.createElement("password");
            Element emailElem = doc.createElement("email");
            nameElem.setTextContent(name);
            passwordElem.setTextContent(password);
            emailElem.setTextContent(email);
            idElem.setTextContent(""+id);
            Node nutzerNode = doc.createElement("benutzer");
            nutzerNode.appendChild(idElem);
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
            System.out.println("Fehler beim Erstellen des Nutzers " + name + ".");
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
                nutzersNode.appendChild(doc.adoptNode(entry.getValue().getFirstChild()));
            }
            doc.appendChild(nutzersNode);
            return doc;
        } catch (ParserConfigurationException e) {
            System.out.println("Fehler beim Erstellen des Dokuments für alle Nutzer.");
            e.printStackTrace();
        }// catch (SAXException e) {
         //   System.out.println("Fehler beim Laden des Schemas für alle Nutzer.");
         //   e.printStackTrace();
        //}
        return null;
    }
}
