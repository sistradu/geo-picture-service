package de.hszigr.gpics.db;

import de.hszigr.gpics.db.interfaces.IBildConnector;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.net.ConnectException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 04.05.11
 * Time: 08:52
 * To change this template use File | Settings | File Templates.
 */
public class MockBildConnector implements IBildConnector{

    private final HashMap<Integer, Document> bildIDMap;
    private final HashMap<String, Document> bildNameMap;
    private final HashMap<Integer, Document> bildAlbumIDMap;
    int id = -1;

    public MockBildConnector(){
        this.bildIDMap = new HashMap<Integer,Document>();
        this.bildNameMap = new HashMap<String,Document>();
        this.bildAlbumIDMap = new HashMap<Integer,Document>();
        //51.148833333333336
        //51"8'55.8
       this.addBild("Peterskirche", "Die Peterskirche", true,
               "2011-02-21 19:00:57", "/pics/Peterskirche_1.jpg",
               "51\"8'55.8", "51\"8'55.8", "51.148833", "51.148833",
               123, 120, 0);
        this.addBild("Vogtshof", "Der Vogtshof", true,
               "2011-02-21 19:01:22", "/pics/Vogtshof_2.jpg",
               "51\"8'55.8", "51\"8'55.8", "51.148833", "51.148833",
               123, 120, 0);
        this.addBild("Vogtshof Innenhof", "Studenten beim Grillen", false,
               "2011-02-21 19:05:01", "/pics/Vogtshof_Innenhof_3.jpg",
               "51\"8'55.8", "51\"8'55.8", "51.148833", "51.148833",
               123, 120, 0);
        this.addBild("Wohnung Vogtshof", "Klein aber fein...", true,
               "2011-02-21 21:37:01", "/pics/Wohnung_Vogtshof_4.jpg",
               "51\"8'55.8", "51\"8'55.8", "51.148833", "51.148833",
               123, 120, 0);
        this.addBild("Blumenuhr", "Die Blumenuhr", true,
               "2011-03-21 19:00:57", "/pics/Blumenuhr_5.jpg",
               "51\"8'55.8", "51\"8'55.8", "51.148833", "51.148833",
               123, 120, 1);
        this.addBild("Bahnhof", "Der Bahnhof", true,
               "2011-03-21 19:01:22", "/pics/Bahnhof_6.jpg",
               "51\"8'55.8", "51\"8'55.8", "51.148833", "51.148833",
               123, 120, 1);
        this.addBild("Nonnenfelsen", "In Jonsdorf", true,
               "2011-03-19 13:05:01", "/pics/Nonnenfelsen_7.jpg",
               "51\"8'55.8", "51\"8'55.8", "51.148833", "51.148833",
               123, 120, 2);
        this.addBild("Die Hochschule", "Eines von 9 Gebäuden...", true,
               "2011-03-21 21:37:01", "/pics/Die_Hochschule_8.jpg",
               "51\"8'55.8", "51\"8'55.8", "51.148833", "51.148833",
               123, 120, 1);
        this.addBild("Gondelfahrt", "Blick vom Nonnenfelsen", true,
               "2011-03-19 13:34:57", "/pics/Gondelfahrt_9.jpg",
               "51\"8'55.8", "51\"8'55.8", "51.148833", "51.148833",
               123, 120, 2);
        this.addBild("Die Mensa", "Leider nicht mehr offen...", true,
               "2011-03-21 21:41:22", "/pics/Die_Mensa_10.jpg",
               "51\"8'55.8", "51\"8'55.8", "51.148833", "51.148833",
               123, 120, 1);
        this.addBild("Hochwald", "Blick vom Nonnenfelsen", true,
               "2011-03-19 13:35:01", "/pics/Hochwald_11.jpg",
               "51\"8'55.8", "51\"8'55.8", "51.148833", "51.148833",
               123, 120, 2);
        this.addBild("Lausche", "Blick vom Nonnenfelsen", true,
               "2011-03-19 13:36:02", "/pics/Lausche_12.jpg",
               "51\"8'55.8", "51\"8'55.8", "51.148833", "51.148833",
               123, 120, 2);
    }

    private void addBild(String name, String description, boolean isPublic, String timeStamp, String position, String latitude, String longitude, String latitudeDecimal, String longitudeDecimal, int altitude, int direction, int albumID){
        this.id++;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().newDocument();
            Element idElem = doc.createElement("id");
            Element nameElem = doc.createElement("name");
            Element descriptionElem = doc.createElement("description");
            Element publicElem = doc.createElement("ispublic");
            Element timestampElem = doc.createElement("timestamp");
            Element filepositionElem = doc.createElement("fileposition");
            Element albumElem = doc.createElement("album");
            Element positionElem = doc.createElement("position");
            Element latitudeElem = doc.createElement("latitude");
            Element longitudeElem = doc.createElement("longitude");
            Element latitudeDecElem = doc.createElement("latitudedecimal");
            Element longitudeDecElem = doc.createElement("longitudedecimal");
            Element altitudeElem = doc.createElement("altitude");
            Element directionElem = doc.createElement("direction");
            idElem.setTextContent(""+this.id);
            nameElem.setTextContent(name);
            descriptionElem.setTextContent(description);
            publicElem.setTextContent(""+isPublic);
            timestampElem.setTextContent(timeStamp);
            filepositionElem.setTextContent(position);
            albumElem.setTextContent(""+albumID);
            latitudeElem.setTextContent(latitude);
            latitudeDecElem.setTextContent(latitudeDecimal);
            longitudeElem.setTextContent(longitude);
            longitudeDecElem.setTextContent(longitudeDecimal);
            altitudeElem.setTextContent(""+altitude);
            directionElem.setTextContent(""+direction);
            positionElem.appendChild(latitudeElem);
            positionElem.appendChild(latitudeDecElem);
            positionElem.appendChild(longitudeElem);
            positionElem.appendChild(longitudeDecElem);
            positionElem.appendChild(altitudeElem);
            positionElem.appendChild(directionElem);
            Node bildNode = doc.createElement("bild");
            bildNode.appendChild(idElem);
            bildNode.appendChild(nameElem);
            bildNode.appendChild(descriptionElem);
            bildNode.appendChild(publicElem);
            bildNode.appendChild(timestampElem);
            bildNode.appendChild(filepositionElem);
            bildNode.appendChild(albumElem);
            bildNode.appendChild(positionElem);
            doc.appendChild(bildNode);
            this.bildIDMap.put(this.id, doc);
            this.bildNameMap.put(name, doc);
            this.bildAlbumIDMap.put(albumID + this.id, doc);
        } catch (ParserConfigurationException e) {
            System.out.println("Fehler beim Erstellen des Albums " + name + ".");
            e.printStackTrace();
        }
    }

    public int createBild(String name, String description, boolean isPublic, GregorianCalendar date, String filePosition, String longitude, String latitude, String altitude, String direction, int albumID) throws ConnectException {
        return ++this.id;
    }

    public void updateBild(int id, String name, String description, boolean isPublic, GregorianCalendar date, String filePosition, String longitude, String latitude, String altitude, String direction) throws ConnectException, IllegalArgumentException {
        //can do nothing
    }

    public void deleteBild(int id) throws ConnectException, IllegalArgumentException {
        //can do nothing
    }

    public void deleteBilderFromAlbum(int albumID) throws ConnectException, IllegalArgumentException {
       //can do nothing
    }

    public Document getBildByID(int id) throws ConnectException, IllegalArgumentException {
        if(this.bildIDMap.containsKey(id))
            return this.bildIDMap.get(id);
        else
            throw new IllegalArgumentException("");
    }

    public Document getBilderByName(String name) throws ConnectException {
        try {
            //TODO mit Hilfe des Schemas erzeugen...
            //Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI).newSchema(new File("nutzer.xsd"));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //factory.setSchema(schema);
            Document doc = factory.newDocumentBuilder().newDocument();
            Node bilderNode = doc.createElement("bilder");
            for(Map.Entry<String,Document> entry : this.bildNameMap.entrySet()){
                if(entry.getKey().contains(name))
                    bilderNode.appendChild(doc.adoptNode(entry.getValue().getFirstChild()));
            }
            doc.appendChild(bilderNode);
            return doc;
        } catch (ParserConfigurationException e) {
            System.out.println("Fehler beim Erstellen des Dokuments für alle Bilder.");
            e.printStackTrace();
        }// catch (SAXException e) {
         //   System.out.println("Fehler beim Laden des Schemas für alle Nutzer.");
         //   e.printStackTrace();
        //}
        return null;
    }

    public Document getBilderForAlbum(int albumID) throws ConnectException, IllegalArgumentException {
        try {
            //TODO mit Hilfe des Schemas erzeugen...
            //Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI).newSchema(new File("nutzer.xsd"));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //factory.setSchema(schema);
            Document doc = factory.newDocumentBuilder().newDocument();
            Node bilderNode = doc.createElement("bilder");
            for(Map.Entry<Integer,Document> entry : this.bildIDMap.entrySet()){
                if(entry.getValue().getElementsByTagName("album").item(0).getTextContent().equals(""+albumID))
                    bilderNode.appendChild(doc.adoptNode(entry.getValue().getFirstChild()));
            }
            doc.appendChild(bilderNode);
            return doc;
        } catch (ParserConfigurationException e) {
            System.out.println("Fehler beim Erstellen des Dokuments für alle Bilder.");
            e.printStackTrace();
        }// catch (SAXException e) {
         //   System.out.println("Fehler beim Laden des Schemas für alle Nutzer.");
         //   e.printStackTrace();
        //}
        return null;
    }

    /**
     * Diese Methode wurde noch nicht implementiert und ist evt. nur in zukünftigen Versionen verfügbar
     * @param rundreiseID
     * @return
     * @throws ConnectException
     * @throws IllegalArgumentException
     */
    public Document getBilderForRundreise(int rundreiseID) throws ConnectException, IllegalArgumentException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
