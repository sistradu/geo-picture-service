package de.hszigr.gpics.util;

import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.MetadataException;
import de.hszigr.gpics.controller.Bild;
import de.hszigr.gpics.controller.CreateEditAlbumController;
import de.hszigr.gpics.controller.UserController;
import de.hszigr.gpics.db.connect.AlbumConnector;
import de.hszigr.gpics.db.connect.BildConnector;
import de.hszigr.gpics.db.interfaces.IAlbumConnector;
import de.hszigr.gpics.db.interfaces.IBildConnector;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.*;
import java.net.ConnectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan Radusch
 * Date: 11.05.11
 * Time: 10:13
 * To change this template use File | Settings | File Templates.
 */
public class AlbumControllerDBUtil {

    public void loescheBild(CreateEditAlbumController controller, int bildId) throws IOException {
        List<Bild> bilder = controller.getBilder();
        for (int i = 0; i < bilder.size(); i++) {
            Bild bild = bilder.get(i);
            if (bildId == bild.getBildID()) {
                bild.getContent().getStream().close();
                File f = new File(bild.getPath());
                f.delete();
                bilder.remove(bild);
                //TODO connector ändern
                IBildConnector connector = new BildConnector();
                connector.deleteBild(bild.getBildID());
            }
        }
    }

    public void updateAlbum(CreateEditAlbumController controller) throws ConnectException, JpegProcessingException, FileNotFoundException, MetadataException, ParseException {
        //TODO connector ändern
        IAlbumConnector albumConnector = new AlbumConnector();
        albumConnector.updateAlbum(controller.getAlbumID(), controller.getAlbumName(), controller.getPasswort(), controller.getAlbumBeschreibung());
        for (Bild bild : controller.getBilder()) {
            IBildConnector bildConnector = new BildConnector();
            if(bild.getBildID()==-1){
//                bildConnector.createBild(bild.getName(), bild.getBeschreibung(), bild.isPublicBild(), bild.getDate(), bild.getPath(), bild.getLongitude(), bild.getLatitude(), bild.getAltitude(), bild.getDirection(), controller.getAlbumID());
                createBild(controller.getAlbumID(), bild);
            }else{
                bildConnector.updateBild(bild.getBildID(), bild.getName(), bild.getBeschreibung(), bild.isPublicBild(), bild.getDate(), bild.getPath(), bild.getLongitude(), bild.getLatitude(), bild.getAltitude(), bild.getDirection());
            }
        }
    }

    public void createAlbum(CreateEditAlbumController controller) throws ConnectException, JpegProcessingException, FileNotFoundException, MetadataException, ParseException {
        //TODO Connector ändern
        IAlbumConnector connector = new AlbumConnector();
        generatePasswort(controller);
        UserController uc = (UserController) GPicSUtil.getBean("userController");
        int nutzerID = uc.getNutzerID();
        controller.setAlbumID(connector.createAlbum(controller.getAlbumName(), controller.getPasswort(), controller.getAlbumBeschreibung(), nutzerID));
        Document album = connector.getAlbumByName(controller.getAlbumName());
//            AlbumControllerDBUtil util = new AlbumControllerDBUtil();
        int albumID = Integer.parseInt(getTextContentFromElement(album, "id"));
        createBilder(albumID, controller);
    }

    public Document ladeAlbumAusDB(String name) throws ConnectException {
        //TODO Connector ändern
        IAlbumConnector albumConnector = new AlbumConnector();
        return albumConnector.getAlbumByName(name);
    }

    public List<Bild> ladeBilderAusDB(int albumID) throws ConnectException {
        //TODO Connector ändern
        IBildConnector bildConnector = new BildConnector();
        List<Bild> bilder = new ArrayList<Bild>();
        Document doc = bildConnector.getBilderForAlbum(albumID);
        NodeList nodeList = doc.getElementsByTagName("bild");
        for (int i = 0; i<nodeList.getLength();i++) {
            Node node = nodeList.item(i);
            String name = node.getChildNodes().item(3).getTextContent();
            Bild bild = ladeEinzelnesBildAusDB(bildConnector, name);
            bilder.add(bild);
        }
        return bilder;
    }

    private void createBilder(int albumID, CreateEditAlbumController controller) throws JpegProcessingException, MetadataException, ParseException, ConnectException, FileNotFoundException {
        for (Bild bild : controller.getBilder()) {
            ImageDataExtractor extractor = new ImageDataExtractor();
            Position pos = extractor.getPosition(bild.getPath());
            CoordinateCalculator calculator = new CoordinateCalculator();
            String longitudeDecimal = calculator.getDecimalCoordinate(pos.getLongitude(), pos.getLongitudeRef());
            bild.setLongitude(longitudeDecimal);
            String latitudeDecimal = calculator.getDecimalCoordinate(pos.getLatitude(), pos.getLatitudeRef());
            bild.setLatitude(latitudeDecimal);
            if (pos.getTimeStamp() != null) {
                bild.setDate(pos.getTimeStamp());
            }else{
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(new Date(System.currentTimeMillis()));
                bild.setDate(cal);
            }
            IBildConnector connector = new BildConnector();
//            bilder.indexOf(bild);
//            connector.createBild(bild.getName(), bild.getBeschreibung(), bild.isPublicBild(), pos.getTimeStamp(), longitudeDecimal, latitudeDecimal, pos.getAltitude(), pos.getDirection());
            connector.createBild(bild.getName(), bild.getBeschreibung(), bild.isPublicBild(), bild.getDate(), bild.getPath(), bild.getLongitude(), bild.getLatitude(), bild.getAltitude(), bild.getDirection(), albumID);
        }
    }

     private void createBild(int albumID, Bild bild) throws JpegProcessingException, MetadataException, ParseException, ConnectException, FileNotFoundException {
            ImageDataExtractor extractor = new ImageDataExtractor();
            Position pos = extractor.getPosition(bild.getPath());
            CoordinateCalculator calculator = new CoordinateCalculator();
            String longitudeDecimal = calculator.getDecimalCoordinate(pos.getLongitude(), pos.getLongitudeRef());
            bild.setLongitude(longitudeDecimal);
            String latitudeDecimal = calculator.getDecimalCoordinate(pos.getLatitude(), pos.getLatitudeRef());
            bild.setLatitude(latitudeDecimal);
            if (pos.getTimeStamp() != null) {
                bild.setDate(pos.getTimeStamp());
            }else{
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(new Date(System.currentTimeMillis()));
                bild.setDate(cal);
            }
            IBildConnector connector = new BildConnector();
//            bilder.indexOf(bild);
//            connector.createBild(bild.getName(), bild.getBeschreibung(), bild.isPublicBild(), pos.getTimeStamp(), longitudeDecimal, latitudeDecimal, pos.getAltitude(), pos.getDirection());
            connector.createBild(bild.getName(), bild.getBeschreibung(), bild.isPublicBild(), bild.getDate(), bild.getPath(), bild.getLongitude(), bild.getLatitude(), bild.getAltitude(), bild.getDirection(), albumID);
    }

    private Bild ladeEinzelnesBildAusDB(IBildConnector bildConnector, String name) throws ConnectException {
//        String bildName = node.getAttributes().getNamedItem("id").getTextContent();
//        Document bildDoc = bildConnector.getBilderByName(bildName);
        Document bildDoc = bildConnector.getBilderByName(name);
        Bild bild = new Bild();
        bild.setBildID(Integer.parseInt(getTextContentFromElement(bildDoc, "id")));
        bild.setName(getTextContentFromElement(bildDoc, "name"));
        bild.setBeschreibung(getTextContentFromElement(bildDoc, "description"));
        String isPublic = getTextContentFromElement(bildDoc, "ispublic");
        if (isPublic.equals("true")) {
            bild.setPublicBild(true);
        } else {
            bild.setPublicBild(false);
        }
        String timeStamp = getTextContentFromElement(bildDoc, "date");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = sdf.parse(timeStamp);
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            bild.setDate(cal);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        bild.setLongitude(getTextContentFromElement(bildDoc, "longitude"));
        bild.setLatitude(getTextContentFromElement(bildDoc, "latitude"));
        bild.setAltitude(getTextContentFromElement(bildDoc, "altitude"));
        bild.setDirection(getTextContentFromElement(bildDoc, "direction"));
        String filePosition = getTextContentFromElement(bildDoc, "fileposition");
        bild.setPath(filePosition);
        try {
            InputStream stream = new FileInputStream(filePosition);
            StreamedContent image = new DefaultStreamedContent(stream, "image/jpeg");
            bild.setContent(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return bild;
    }

    public String getTextContentFromElement(Document doc, String tagName) {
        return doc.getElementsByTagName(tagName).item(0).getTextContent();
    }

    private void generatePasswort(CreateEditAlbumController controller) {
        PasswortUtil util = new PasswortUtil();
        String randomPasswort = util.erzeugeZufallsPasswort(4);
        //Passwort unverschlüsselt, damit es angezeigt werden kann
        controller.setPasswort(randomPasswort);
    }
}
