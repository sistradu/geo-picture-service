package de.hszigr.gpics.util;

import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.MetadataException;
import de.hszigr.gpics.controller.Bild;
import de.hszigr.gpics.controller.CreateEditAlbumController;
import de.hszigr.gpics.db.MockAlbumConnector;
import de.hszigr.gpics.db.MockBildConnector;
import de.hszigr.gpics.db.interfaces.IAlbumConnector;
import de.hszigr.gpics.db.interfaces.IBildConnector;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

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
        for(int i = 0; i<bilder.size();i++){
                Bild bild = bilder.get(i);
                if(bildId == bild.getBildID()){
                    bild.getContent().getStream().close();
                    File f = new File(bild.getPath());
                    f.delete();
                    bilder.remove(bild);
                    //TODO connector ändern
                    IBildConnector connector = new MockBildConnector();
                    connector.deleteBild(bild.getBildID());
                }
            }
    }

    public void updateAlbum(CreateEditAlbumController controller) throws ConnectException {
        //TODO connector ändern
            IAlbumConnector albumConnector = new MockAlbumConnector();
            albumConnector.updateAlbum(controller.getAlbumID(), controller.getAlbumName(), controller.getPasswort(), controller.getAlbumBeschreibung());
            for(Bild bild : controller.getBilder()){
                IBildConnector bildConnector = new MockBildConnector();
                bildConnector.updateBild(bild.getBildID(), bild.getName(), bild.getBeschreibung(), bild.isPublicBild(), bild.getDate(), bild.getLongitude(), bild.getLatitude(), bild.getAltitude(), bild.getDirection());
            }
    }

    public void createAlbum(CreateEditAlbumController controller) throws ConnectException, JpegProcessingException, FileNotFoundException, MetadataException, ParseException {
        //TODO Connector ändern
            IAlbumConnector connector = new MockAlbumConnector();
            generatePasswort(controller);
            controller.setAlbumID(connector.createAlbum(controller.getAlbumName(), controller.getPasswort(), controller.getAlbumBeschreibung()));
            Document album = connector.getAlbum(controller.getAlbumName());
//            AlbumControllerDBUtil util = new AlbumControllerDBUtil();
            createBilder(album, controller);
    }

 @SuppressWarnings("unchecked")
    public void ladeAttributeAusDB(String name, CreateEditAlbumController album) throws ConnectException {
        //TODO Connector ändern
        IAlbumConnector albumConnector = new MockAlbumConnector();
        Document doc = albumConnector.getAlbum(name);
        album.setAlbumID(Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent()));
        album.setAlbumName(name);
        album.setPasswort(doc.getElementsByTagName("passwort").item(0).getTextContent());
        album.setAlbumBeschreibung(doc.getElementsByTagName("description").item(0).getTextContent());

        //TODO Connector ändern
        IBildConnector bildConnector = new MockBildConnector();
        List<Bild> bilder = new ArrayList<Bild>();
        List<Node> nodeList = (List<Node>) doc.getElementsByTagName("bild");
        for (Node node : nodeList) {
            Bild bild = ladeBildAusDB(bildConnector, node);
            bilder.add(bild);
        }
     album.setBilder(bilder);
    }

    private void createBilder(Document album, CreateEditAlbumController controller) throws JpegProcessingException, MetadataException, ParseException, ConnectException, FileNotFoundException {
        for (Bild bild : controller.getBilder()) {
            ImageDataExtractor extractor = new ImageDataExtractor();
            Position pos = extractor.getPosition(bild.getPath());
            CoordinateCalculator calculator = new CoordinateCalculator();
            String longitudeDecimal = calculator.getDecimalCoordinate(pos.getLongitude(), pos.getLongitudeRef());
            bild.setLongitude(longitudeDecimal);
            String latitudeDecimal = calculator.getDecimalCoordinate(pos.getLatitude(), pos.getLatitudeRef());
            bild.setLatitude(latitudeDecimal);
            bild.setDate(pos.getTimeStamp());
            IBildConnector connector = new MockBildConnector();
//            bilder.indexOf(bild);
//            connector.createBild(bild.getName(), bild.getBeschreibung(), bild.isPublicBild(), pos.getTimeStamp(), longitudeDecimal, latitudeDecimal, pos.getAltitude(), pos.getDirection());
            connector.createBild(bild.getName(), bild.getBeschreibung(), bild.isPublicBild(), bild.getDate(), bild.getPath(), bild.getLongitude(), bild.getLatitude(), bild.getAltitude(), bild.getDirection(), album);
        }
    }

    private Bild ladeBildAusDB(IBildConnector bildConnector, Node node) throws ConnectException {
        String bildName = node.getAttributes().getNamedItem("id").getTextContent();
        Document bildDoc = bildConnector.getBilderByName(bildName);
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
        String timeStamp = getTextContentFromElement(bildDoc, "timestamp");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

    private String getTextContentFromElement(Document bildDoc, String tagName) {
        return bildDoc.getElementsByTagName(tagName).item(0).getTextContent();
    }

    private void generatePasswort(CreateEditAlbumController controller) {
        PasswortUtil util = new PasswortUtil();
        String randomPasswort = util.erzeugeZufallsPasswort(4);
        controller.setPasswort(util.encryptWithMD5(randomPasswort));
    }
}
