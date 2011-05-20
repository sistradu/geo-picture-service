package de.hszigr.gpics.controller;

import de.hszigr.gpics.db.connect.AlbumConnector;
import de.hszigr.gpics.db.connect.BildConnector;
import de.hszigr.gpics.db.interfaces.IAlbumConnector;
import de.hszigr.gpics.db.interfaces.IBildConnector;
import de.hszigr.gpics.util.GPicSUtil;
import de.hszigr.gpics.util.MessagePropertiesBean;
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
    private StreamedContent picture;

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
            bild.setBildID(Integer.parseInt(getTextContentFromElement(bilderXML, "id", i)));
            bild.setName(getTextContentFromElement(bilderXML, "name", i));
            bild.setBeschreibung(getTextContentFromElement(bilderXML, "description", i));
            String isPublic = getTextContentFromElement(bilderXML, "ispublic", i);
            if (isPublic.equals("true")) {
                bild.setPublicBild(true);
            } else {
                bild.setPublicBild(false);
            }
            String timeStamp = getTextContentFromElement(bilderXML, "date", i);
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = sdf.parse(timeStamp);
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(date);
                bild.setDate(cal);
            } catch (ParseException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            bild.setLongitude(getTextContentFromElement(bilderXML, "longitude", i));
            bild.setLatitude(getTextContentFromElement(bilderXML, "latitude", i));
            bild.setAltitude(getTextContentFromElement(bilderXML, "altitude", i));
            bild.setDirection(getTextContentFromElement(bilderXML, "direction", i));
            String filePosition = getTextContentFromElement(bilderXML, "fileposition", i);
            bild.setPath(filePosition);

            InputStream stream = null;
            try {
                stream = new FileInputStream(filePosition);
            } catch (IOException e) {
                MessagePropertiesBean msgPB = new MessagePropertiesBean();
                stream = new FileInputStream(msgPB.getPropertiesMessage("defaultPicturePath"));
                bild.setPath(msgPB.getPropertiesMessage("defaultPicturePath"));
                e.printStackTrace();
            }

            StreamedContent image = new DefaultStreamedContent(stream, "image/jpeg");
            bild.setContent(image);

            UserController uc = (UserController) GPicSUtil.getBean("userController");
            if ((uc.isEingeloggt() && uc.getNutzerID() == this.erstellerID) || bild.isPublicBild()){
                bilder.add(bild);
            }

        }
    }

    public String getTextContentFromElement(Document doc, String tagName, int index){
        String back = "";
        try{
            back = doc.getElementsByTagName(tagName).item(index).getTextContent();
        }catch(NullPointerException npe){
            npe.printStackTrace();
        }
        return back;
    }

    public String getImage(String name) {
        StreamedContent defaultImage = null;
        try {
            MessagePropertiesBean msgPB = new MessagePropertiesBean();
            String pfad = "D:/upload/gpics.jpg";// msgPB.getPropertiesMessage("defaultPicturePath");
            defaultImage = GPicSUtil.getStreamContent(pfad);
            FacesContext fc = FacesContext.getCurrentInstance();
        //    String name = fc.getExternalContext().getRequestParameterMap().get("name");
            if (name != null && !bilder.isEmpty()) {
                StreamedContent content = null;
                for (Bild b : bilder) {
                    if (b.getName().equals(name)) {
                        content = b.getContent();
                    }
                }
                picture = content;
                return "";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        picture = defaultImage;
        return "";
    }

    public StreamedContent getPicture(){
        return picture;
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
