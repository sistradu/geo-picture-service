package de.hszigr.gpics.controller;

import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.MetadataException;
import de.hszigr.gpics.db.MockAlbumConnector;
import de.hszigr.gpics.db.MockBildConnector;
import de.hszigr.gpics.db.interfaces.IAlbumConnector;
import de.hszigr.gpics.db.interfaces.IBildConnector;
import de.hszigr.gpics.util.*;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
 * Date: 28.04.11
 * Time: 20:24
 * To change this template use File | Settings | File Templates.
 */
//TODO scope ändern
@ManagedBean
@SessionScoped
public class CreateEditAlbumController {

    private int albumID;
    private String albumName;
    private String albumBeschreibung;
    private String passwort;

    //TODO uploadDir
    private String uploadDir = "/home/pics/";
//    private String uploadDir = "D:/upload/";
    private List<Bild> bilder;

    private int selectedBId;

    public CreateEditAlbumController() {
        bilder = new ArrayList<Bild>();
    }

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();

        try {
            FileOutputStream out = new FileOutputStream(uploadDir + file.getFileName());
            out.write(file.getContents());
            out.flush();
            out.close();
            updateBilderListe(uploadDir + file.getFileName());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        FacesMessageHandler.createFacesMessageForID("uploader", file.getFileName() + " erfolgreich hochgeladen.");
    }

    public StreamedContent getImage() {
        String name = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("name");
        if (name != null && !bilder.isEmpty()) {
            StreamedContent content = null;
            for (Bild b : bilder) {
                if (b.getName().equals(name)) {
                    content = b.getContent();
                }
            }
            return content;
        }
        return getDefaultContent();
    }

    //TODO einstieg für bearbeiten
    public String ladeAlbum() {
        String name = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("albumName");
        try {
            if (name != null) {
                ladeAttributeAusDB(name);

            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessageHandler.createFacesMessageForID("saveAlbum", e.getMessage());
        }
        return "createAlbum";
    }

    public void loescheBild() {
        try {
            int bildId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("bildID"));
            for(int i = 0; i<bilder.size();i++){
                Bild bild = bilder.get(i);
                if(bildId == bild.getBildID()){
                    File f = new File(bild.getPath());
                    bilder.remove(bild);
                    //TODO connector ändern
                    IBildConnector connector = new MockBildConnector();
                    connector.deleteBild(bild.getBildID());
                }
            }
        } catch (ConnectException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            FacesMessageHandler.createFacesMessageForID("saveAlbum", e.getMessage());
        }
    }

    //TODO aufruf zum speichern des bearbeiteten album
    public String updateAlbum(){
        try {
            //TODO connector ändern
            IAlbumConnector albumConnector = new MockAlbumConnector();
            albumConnector.updateAlbum(this.albumID, this.albumName, this.passwort, this.albumBeschreibung);
            for(Bild bild : bilder){
                IBildConnector bildConnector = new MockBildConnector();
                bildConnector.updateBild(bild.getBildID(), bild.getName(), bild.getBeschreibung(), bild.isPublicBild(), bild.getDate(), bild.getLongitude(), bild.getLatitude(), bild.getAltitude(), bild.getDirection());
            }
        } catch (ConnectException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            FacesMessageHandler.createFacesMessageForID("saveAlbum", e.getMessage());
            return null;
        }
        return "showAlbum";
    }

    public String createAlbum() {
        try {
            if (this.albumName == null) {
                MessagePropertiesBean msg = new MessagePropertiesBean();
                FacesMessageHandler.createFacesMessageForID("createAlbumName", msg.getPropertiesMessage("forgotAlbumName"));
                return null;

            }
            //TODO Connector ändern
            IAlbumConnector connector = new MockAlbumConnector();
            generatePasswort();
            this.albumID = connector.createAlbum(albumName, passwort, albumBeschreibung);
            Document album = connector.getAlbum(albumName);
            createBilder(album);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            FacesMessageHandler.createFacesMessageForID("saveAlbum", e.getMessage());
        }
        return "showAlbum";
    }

    public void checkboxSelectAction() {
        for (Bild bild : bilder) {
            if (selectedBId == bild.getBildID()) {
                bild.setPublicBild(true);
            }
        }
    }

    public boolean isNewAlbum(){
        return this.albumID == 0;
    }

    @SuppressWarnings("unchecked")
    private void ladeAttributeAusDB(String name) throws ConnectException {
        //TODO Connector ändern
        IAlbumConnector albumConnector = new MockAlbumConnector();
        Document doc = albumConnector.getAlbum(name);
        this.albumID = Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent());
        this.albumName = name;
        this.passwort = doc.getElementsByTagName("passwort").item(0).getTextContent();
        this.albumBeschreibung = doc.getElementsByTagName("description").item(0).getTextContent();

        //TODO Connector ändern
        IBildConnector bildConnector = new MockBildConnector();
        this.bilder = new ArrayList<Bild>();
        List<Node> nodeList = (List<Node>) doc.getElementsByTagName("bild");
        for (Node node : nodeList) {
            Bild bild = ladeBildAusDB(bildConnector, node);
            bilder.add(bild);
        }
    }

    private void createBilder(Document album) throws JpegProcessingException, MetadataException, ParseException, ConnectException, FileNotFoundException {
        for (Bild bild : bilder) {
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

    private StreamedContent getDefaultContent() {
        StreamedContent image = null;
        try {
            InputStream stream = new FileInputStream(uploadDir + "/gpics.jpg");
            image = new DefaultStreamedContent(stream, "image/jpeg");

        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return image;
    }

    private void generatePasswort() {
        PasswortUtil util = new PasswortUtil();
        String randomPasswort = util.erzeugeZufallsPasswort(4);
        this.passwort = util.encryptWithMD5(randomPasswort);
    }

    private void updateBilderListe(String s) {
        try {
            Bild bild = new Bild();
            bild.setName(s.substring(s.lastIndexOf("/") + 1));
            bild.setPath(s);
            InputStream stream = new FileInputStream(s);
            StreamedContent image = new DefaultStreamedContent(stream, "image/jpeg", s.substring(s.lastIndexOf("/") + 1));
            bild.setContent(image);
            bild.setPublicBild(false);
            bild.setBildID(bilder.size());
            bilder.add(bild);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumBeschreibung() {
        return albumBeschreibung;
    }

    public void setAlbumBeschreibung(String albumBeschreibung) {
        this.albumBeschreibung = albumBeschreibung;
    }

    public String getPasswort() {
        return passwort;
    }

    public List<Bild> getBilder() {
        return bilder;
    }

    public void setBilder(List<Bild> bilder) {
        this.bilder = bilder;
    }

    public boolean containsImages() {
        return bilder.size() > 0;
    }

    public int getSelectedBId() {
        return selectedBId;
    }

    public void setSelectedBId(int selectedBId) {
        this.selectedBId = selectedBId;
    }
}
