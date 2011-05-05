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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.*;
import java.net.ConnectException;
import java.text.ParseException;
import java.util.ArrayList;
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

    private String uploadDir = "D:/upload/";
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
            updateBilder(uploadDir + file.getFileName());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        FacesMessageHandler.createFacesMessageForID("uploader", file.getFileName() + " erfolgreich hochgeladen.");
    }

    private void updateBilder(String s) {
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

    public StreamedContent getImage() {
        String name = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap().get("name");
        if(name!=null && !bilder.isEmpty()){
            StreamedContent content = null;
            for(Bild b : bilder){
                if(b.getName().equals(name)){
                    content = b.getContent();
                }
            }
            return content;
        }
        return getDefaultContent();
    }

    private StreamedContent getDefaultContent(){
        StreamedContent image = null;
        try {
            InputStream stream = new FileInputStream(uploadDir + "/gpics.jpg");
            image = new DefaultStreamedContent(stream, "image/jpeg");

        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return image;
    }

    //TODO album bearbeiten

    public String speichern(){
        try {
        //TODO Connector ändern
            IAlbumConnector connector = new MockAlbumConnector();
            generatePasswort();
            this.albumID = connector.createAlbum(albumName, passwort, albumBeschreibung);
            speicherBilder();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            FacesMessageHandler.createFacesMessageForID("saveAlbum", e.getMessage());
        }
        return "showAlbum";
    }

    private void speicherBilder() throws JpegProcessingException, MetadataException, ParseException, ConnectException {
        for(Bild bild : bilder){
            ImageDataExtractor extractor = new ImageDataExtractor();
            Position pos = extractor.getPosition(bild.getPath());
            CoordinateCalculator calculator = new CoordinateCalculator();
            String longitudeDecimal = calculator.getDecimalCoordinate(pos.getLongitude(), pos.getLongitudeRef());
            String latitudeDecimal = calculator.getDecimalCoordinate(pos.getLatitude(), pos.getLatitudeRef());
            IBildConnector connector = new MockBildConnector();
            bilder.indexOf(bild);
            connector.createBild(bild.getName(), bild.getBeschreibung(), bild.isPublicBild(), pos.getTimeStamp(), longitudeDecimal, latitudeDecimal, pos.getAltitude(), pos.getDirection());
        }
    }

    private void generatePasswort() {
        PasswortUtil util = new PasswortUtil();
        String randomPasswort = util.erzeugeZufallsPasswort(4);
        this.passwort = util.encryptWithMD5(randomPasswort);
    }

    public void checkboxSelectAction(){
        for(Bild bild : bilder){
            if(selectedBId==bild.getBildID()){
                bild.setPublicBild(true);
            }
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

    public boolean containsImages(){
        return bilder.size()>0;
    }

    public int getSelectedBId() {
        return selectedBId;
    }

    public void setSelectedBId(int selectedBId) {
        this.selectedBId = selectedBId;
    }
}
