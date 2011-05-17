package de.hszigr.gpics.controller;

import de.hszigr.gpics.util.AlbumControllerDBUtil;
import de.hszigr.gpics.util.GPicSUtil;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
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

    //TODO uploadDir
//    private String uploadDir = "/home/pics/";
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
            updateBilderListe(uploadDir + file.getFileName());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        GPicSUtil.createFacesMessageForID("uploader", file.getFileName() + " erfolgreich hochgeladen.", false);
    }

    public StreamedContent getImage() {
        StreamedContent defaultImage = null;
        try {
            defaultImage = GPicSUtil.getStreamContent(uploadDir + "/gpics.jpg");
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
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return defaultImage;
    }

    //TODO einstieg für bearbeiten
    public String ladeAlbum() {
        String name = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("AlbumName");
        try {
            if (name != null) {
                AlbumControllerDBUtil util = new AlbumControllerDBUtil();
                util.ladeAttributeAusDB(name, this);

            }
        } catch (Exception e) {
            e.printStackTrace();
            GPicSUtil.createFacesMessageForID("saveAlbum", e.getMessage(),true);
        }
        return "createAlbum";
    }

    public void loescheBild() {
        try {
            int bildId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("bildID"));
            AlbumControllerDBUtil util = new AlbumControllerDBUtil();
            util.loescheBild(this, bildId);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            GPicSUtil.createFacesMessageForID("saveAlbum", e.getMessage(), true);
        }
    }

    //TODO aufruf zum speichern des bearbeiteten album
    public String updateAlbum(){
        try {
            AlbumControllerDBUtil util = new AlbumControllerDBUtil();
            util.updateAlbum(this);
        } catch (ConnectException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            GPicSUtil.createFacesMessageForID("saveAlbum", e.getMessage(), true);
            return null;
        }
        return "showAlbum";
    }

    public String createAlbum() {
        try {
//            if (this.albumName == null) {
//                MessagePropertiesBean msg = new MessagePropertiesBean();
//                GPicSUtil.createFacesMessageForID("createAlbumName", msg.getPropertiesMessage("forgotAlbumName"));
//                return null;
//
//            }
            AlbumControllerDBUtil util = new AlbumControllerDBUtil();
            util.createAlbum(this);
            AlbumController ac = (AlbumController) GPicSUtil.getBean("albumController");
            ac.loadAlbum(this.getAlbumName());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            GPicSUtil.createFacesMessageForID("saveAlbum", e.getMessage(), true);
            return "createAlbum";
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

    public boolean newAlbum(){
        return this.albumID == 0;
    }

    private void updateBilderListe(String s) throws IOException {
        try {
            Bild bild = new Bild();
            bild.setName(s.substring(s.lastIndexOf("/") + 1));
            bild.setPath(s);
            bild.setContent(GPicSUtil.getStreamContent(s));
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

    public void setPasswort(String passwort){
        this.passwort=passwort;
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
