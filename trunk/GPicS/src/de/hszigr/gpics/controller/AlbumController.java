package de.hszigr.gpics.controller;

import de.hszigr.gpics.db.connect.AlbumConnector;
import de.hszigr.gpics.db.connect.BildConnector;
import de.hszigr.gpics.db.interfaces.IAlbumConnector;
import de.hszigr.gpics.db.interfaces.IBildConnector;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.w3c.dom.Document;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Rico Scholz
 * Date: 27.04.11
 * Time: 10:03
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@SessionScoped
public class AlbumController {

    private Document album;
    private int albumID;
    private String albumName;
    private String albumBeschreibung;
    private String passwort;
    private int erstellerID;
    private ArrayList<Bild> bilder = new ArrayList<Bild>();

    public AlbumController(){

        try{
            albumName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("AlbumName");

            loadAlbum(albumName);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public String loadAlbumFromXHTML() throws Exception{
        albumName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("AlbumName");
        loadAlbum(albumName);
        return "showAlbum";
    }
    
    public void loadAlbum(String name) throws Exception{
        bilder.clear();
        albumName = name;

        IAlbumConnector iac = new AlbumConnector();
        IBildConnector ibc = new BildConnector();

        System.out.println(name);
        album = iac.getAlbumByName(name);
        int aID = Integer.parseInt(album.getElementsByTagName("id").item(0).getTextContent());
        erstellerID = Integer.parseInt(album.getElementsByTagName("nutzer").item(0).getTextContent());

        albumBeschreibung = album.getElementsByTagName("description").item(0).getTextContent();
        Document bilderXML = ibc.getBilderForAlbum(aID);


        for (int i = 0; i < bilderXML.getElementsByTagName("fileposition").getLength(); i++){

            Bild bild  = new Bild();
            bild.setBildID(Integer.parseInt(getTextContentFromElement(bilderXML, "id")));
            bild.setName(getTextContentFromElement(bilderXML, "name"));
            bild.setBeschreibung(getTextContentFromElement(bilderXML, "description"));
            String isPublic = getTextContentFromElement(bilderXML, "ispublic");
            if (isPublic.equals("true")) {
                bild.setPublicBild(true);
            } else {
                bild.setPublicBild(false);
            }
            String timeStamp = getTextContentFromElement(bilderXML, "date");
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = sdf.parse(timeStamp);
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(date);
                bild.setDate(cal);
            } catch (ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            bild.setLongitude(getTextContentFromElement(bilderXML, "longitude"));
            bild.setLatitude(getTextContentFromElement(bilderXML, "latitude"));
            bild.setAltitude(getTextContentFromElement(bilderXML, "altitude"));
            bild.setDirection(getTextContentFromElement(bilderXML, "direction"));
            String filePosition = getTextContentFromElement(bilderXML, "fileposition");
            bild.setPath(filePosition);

            InputStream stream = null;
            try {
                stream = new FileInputStream(filePosition);
            } catch (IOException e) {
                stream = new FileInputStream("D:/upload/gpics.jpg");
                bild.setPath("D:/upload/gpics.jpg");
                e.printStackTrace();
            }

            StreamedContent image = new DefaultStreamedContent(stream, "image/jpeg");
            bild.setContent(image);

            bilder.add(bild);
        }
    }

    public String getTextContentFromElement(Document doc, String tagName){
        String back = "";
        try{
            back = doc.getElementsByTagName(tagName).item(0).getTextContent();
        }catch(NullPointerException npe){
            npe.printStackTrace();
        }
        return back;
    }

    public Document getAlbum() {
        return album;
    }

    public List<Bild> getBilder() {
        return bilder;
    }

    public String getAlbumName() {

        return albumName;
    }

    public int getErstellerID() {
        return erstellerID;
    }

    public void setErstellerID(int erstellerID) {
        this.erstellerID = erstellerID;
    }

    public int getAlbumID() {
        return albumID;
    }

    public void setAlbumID(int albumID) {
        this.albumID = albumID;
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

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}
