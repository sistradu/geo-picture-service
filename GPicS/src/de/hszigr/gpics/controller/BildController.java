package de.hszigr.gpics.controller;

import de.hszigr.gpics.db.connect.AlbumConnector;
import de.hszigr.gpics.db.connect.BildConnector;
import de.hszigr.gpics.db.interfaces.IAlbumConnector;
import de.hszigr.gpics.db.interfaces.IBildConnector;
import de.hszigr.gpics.primefaces_beans.CalendarBean;
import de.hszigr.gpics.util.GPicSUtil;
import org.primefaces.model.StreamedContent;
import org.w3c.dom.Document;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.net.ConnectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Snookerms
 * Date: 27.04.11
 * Time: 09:32
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@SessionScoped
public class BildController {

    private int bildID;
    private String bildName;
    private String beschreibung;
    private boolean oeffentlich = false;
    private Date timestamp = new java.util.Date();
    private String latitude;
    private String longitude;
    private String altitude;
    private String direction;
    private IBildConnector ibild;
    private CalendarBean calbean = new CalendarBean();
    private Document bild;
    private String fileposition;
    private StreamedContent data;
//    private String albumName;

    public String loadBilddaten(){
      ibild = new BildConnector();
//        String id  = FacesContext.getCurrentInstance()
//                    .getExternalContext().getRequestParameterMap().get("id");
//        int id_b = Integer.parseInt(id);
     //   calbean = new CalendarBean();
        try {
              //
//            bild = ibild.getBildByID(id_b);
            int bildId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("bildid"));
            bild = ibild.getBildByID(bildId);
//            bildID=id_b;
//            int albumID = Integer.parseInt(bild.getElementsByTagName("album").item(0).getTextContent());
//            IAlbumConnector album = new AlbumConnector();
//            Document doc = album.getAlbumByID(albumID);
//            albumName = doc.getElementsByTagName("name").item(0).getTextContent();

            bildName=bild.getElementsByTagName("name").item(0).getTextContent();
            beschreibung=bild.getElementsByTagName("description").item(0).getTextContent();
            oeffentlich=Boolean.parseBoolean(bild.getElementsByTagName("ispublic").item(0).getTextContent());
            timestamp=new SimpleDateFormat("yyyy-MM-dd").parse(bild.getElementsByTagName("date").item(0).getTextContent());
            latitude=bild.getElementsByTagName("latitude").item(0).getTextContent();
            longitude=bild.getElementsByTagName("longitude").item(0).getTextContent();
            altitude=bild.getElementsByTagName("altitude").item(0).getTextContent();
            direction=bild.getElementsByTagName("direction").item(0).getTextContent();
            fileposition=bild.getElementsByTagName("fileposition").item(0).getTextContent();
            data= GPicSUtil.getStreamContent(fileposition);
            return "editPicture";
        } catch (ConnectException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return "showAlbum";
    }

    public int getBildID() {
        return bildID;
    }

    public void setBildID(int bildID) {
        this.bildID = bildID;
    }

    public String getBildName() {
        return bildName;
    }

    public void setBildName(String bildName) {
        this.bildName = bildName;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public boolean isOeffentlich() {
        return oeffentlich;
    }

    public void setOeffentlich(boolean oeffentlich) {
        this.oeffentlich = oeffentlich;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public CalendarBean getCalbean() {
        return calbean;
    }

    public void bild_aendern(){
      ibild = new BildConnector();
  //  calbean = new CalendarBean();
      GregorianCalendar cal = new GregorianCalendar(Locale.getDefault());
     cal.setTime(timestamp);
        try {
            ibild.updateBild(bildID,bildName,beschreibung,oeffentlich,cal,null,longitude,latitude,altitude,direction);
//            CreateEditAlbumController c = (CreateEditAlbumController) GPicSUtil.getBean("createEditAlbumController");
//            return c.ladeAlbum();
//            return "editPicture";
        } catch (ConnectException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//        return "editPicture";
    }


    public String getFileposition() {
        return fileposition;
    }

    public void setFileposition(String fileposition) {
        this.fileposition = fileposition;
    }

    public StreamedContent getData() {
//        loadBilddaten();
        return data;
    }

    public void setData(StreamedContent data) {
        this.data = data;
    }

//    public String getAlbumName() {
//        return albumName;
//    }
//
//    public void setAlbumName(String albumName) {
//        this.albumName = albumName;
//    }
}

