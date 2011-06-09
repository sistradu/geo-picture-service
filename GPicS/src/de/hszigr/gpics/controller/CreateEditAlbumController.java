package de.hszigr.gpics.controller;

import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.MetadataException;
import de.hszigr.gpics.db.connect.AlbumConnector;
import de.hszigr.gpics.db.interfaces.IAlbumConnector;
import de.hszigr.gpics.util.AlbumControllerDBUtil;
import de.hszigr.gpics.util.GPicSUtil;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.w3c.dom.Document;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.*;
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

    //TODO uploadDir
    private String uploadDir = "/home/pics/";
//    private String uploadDir = "D:/upload/";
    private List<Bild> bilder;
    private List<Bild> deleteList;

    private int selectedBId;
    private boolean isNewAlbum = true;

    public CreateEditAlbumController() {
        bilder = new ArrayList<Bild>();
        deleteList = new ArrayList<Bild>();
    }

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();

        try {
            UserController uc = (UserController) GPicSUtil.getBean("userController");
            String username = uc.getNutzerNamen();
            FileOutputStream out = new FileOutputStream(uploadDir + username + "_" + file.getFileName());
            out.write(file.getContents());
            out.flush();
            out.close();
            updateBilderListe(uploadDir + username + "_" + file.getFileName());
            if (!isNewAlbum) {
                //TODO aufruf von ladeAlbum probieren
                AlbumControllerDBUtil util = new AlbumControllerDBUtil();
                setBilder(util.ladeBilderAusDB(albumID));
//            FacesContext.getCurrentInstance().getExternalContext().redirect("createAlbum.xhtml");
            }
        } catch (Exception e) {
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

    public String initCreateEditAlbumController() {
        albumID = -1;
        albumName = "";
        albumBeschreibung = "";
        passwort = "";
        bilder = new ArrayList<Bild>();
        deleteList = new ArrayList<Bild>();
        selectedBId = -1;
        isNewAlbum = true;
        return "createAlbum";
    }

    public String ladeAlbum(int albumID) {
        isNewAlbum = false;
        deleteList = new ArrayList<Bild>();
        try {
            AlbumControllerDBUtil util = new AlbumControllerDBUtil();
            IAlbumConnector conn = new AlbumConnector();
            Document doc = conn.getAlbumByID(albumID);
            setAlbumID(Integer.parseInt(util.getTextContentFromElement(doc, "id")));
            setAlbumName(util.getTextContentFromElement(doc, "name"));
            setPasswort(util.getTextContentFromElement(doc, "password"));
            setAlbumBeschreibung(util.getTextContentFromElement(doc, "description"));
            bilder = null;
            setBilder(util.ladeBilderAusDB(albumID));
        } catch (Exception e) {
            e.printStackTrace();
            GPicSUtil.createFacesMessageForID("saveAlbum", e.getMessage(), true);
        }
        return "createAlbum";
    }

    //TODO einstieg für bearbeiten
    public String ladeAlbum() {
//        reset();
        isNewAlbum = false;
        deleteList = new ArrayList<Bild>();
        String name = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("AlbumName");
        try {
            if (name != null) {
                AlbumControllerDBUtil util = new AlbumControllerDBUtil();
                Document doc = util.ladeAlbumAusDB(name);
//                setAlbumID(Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent()));
                setAlbumID(Integer.parseInt(util.getTextContentFromElement(doc, "id")));
                setAlbumName(name);
//                setPasswort(doc.getElementsByTagName("passwort").item(0).getTextContent());
                setPasswort(util.getTextContentFromElement(doc, "password"));
//                setAlbumBeschreibung(doc.getElementsByTagName("description").item(0).getTextContent());
                setAlbumBeschreibung(util.getTextContentFromElement(doc, "description"));
                bilder = null;
                setBilder(util.ladeBilderAusDB(albumID));
            }
        } catch (Exception e) {
            e.printStackTrace();
            GPicSUtil.createFacesMessageForID("saveAlbum", e.getMessage(), true);
        }
        return "createAlbum";
    }


    public void loescheBild() {
        try {
            int bildId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("bildID"));
            for (Bild bild : deleteList) {
                if (bildId == bild.getBildID()) {
                    bild.getContent();
                    bild.getContent().getStream().close();
                }
            }
            AlbumControllerDBUtil util = new AlbumControllerDBUtil();
            util.loescheBild(this, bildId);

            for(Bild bild : bilder){
                bild.setContent(GPicSUtil.getStreamContent(bild.getPath()));
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            GPicSUtil.createFacesMessageForID("saveAlbum", e.getMessage(), true);
        }
    }

    //TODO aufruf zum speichern des bearbeiteten album
    public String updateAlbum() {
        try {
            AlbumControllerDBUtil util = new AlbumControllerDBUtil();
            util.updateAlbum(this);
            AlbumController ac = (AlbumController) GPicSUtil.getBean("albumController");
            ac.loadAlbum(this.getAlbumName());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            GPicSUtil.createFacesMessageForID("saveAlbum", e.getMessage(), true);
            return null;
        }
        isNewAlbum = true;
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
            for(Bild bild : bilder){
                String oldPath = bild.getPath();
                File f = new File(oldPath);
                String fileName = oldPath.substring(oldPath.lastIndexOf("/")+1);
                fileName = albumName + "_" + fileName;
                String tempPath = oldPath.substring(0,oldPath.lastIndexOf("/")+1);
                String newPath = tempPath + fileName;
                copyImageFiles(oldPath, newPath);
                bild.getContent();
                bild.getContent().getStream().close();
                boolean r = f.delete();
                bild.setPath(newPath);
                bild.setContent(GPicSUtil.getStreamContent(newPath));
            }

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

    public String abortEdit() {
        try {
            for (Bild bild : deleteList) {
                bild.getContent().getStream().close();
                AlbumControllerDBUtil util = new AlbumControllerDBUtil();
                util.loescheBild(this, bild.getBildID());

            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            GPicSUtil.createFacesMessageForID("saveAlbum", e.getMessage(), true);
            return "createAlbum";
        }
        return "showAlbum";
    }

    private void copyImageFiles(String oldPath, String newPath) throws IOException {
        FileInputStream fin = new FileInputStream(oldPath);
        FileOutputStream fout = new FileOutputStream(newPath);
        byte[] b = new byte[(int) new File(oldPath).length()];
        fin.read(b);
        fout.write(b);
        fin.close();
        fout.close();
    }

    private void reset() {
        albumID = -1;
        albumName = "";
        albumBeschreibung = "";
        passwort = "";
        bilder = new ArrayList<Bild>();
        deleteList = new ArrayList<Bild>();
    }

    private void updateBilderListe(String s) throws IOException, JpegProcessingException, MetadataException, ParseException {
        try {
            Bild bild = new Bild();
            bild.setName(s.substring(s.lastIndexOf("/") + 1));
            bild.setPath(s);
            StreamedContent c = GPicSUtil.getStreamContent(s);
            bild.setContent(GPicSUtil.getStreamContent(s));
            bild.setPublicBild(false);
            bild.setBildID(-1);
            bilder.add(bild);
            if (!isNewAlbum) {
                AlbumControllerDBUtil util = new AlbumControllerDBUtil();
                int bildID = util.createBild(this.albumID, bild);
                bild.setBildID(bildID);
                deleteList.add(bild);
            }
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

    public void setPasswort(String passwort) {
        this.passwort = passwort;
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

    public boolean isNewAlbum() {
        return isNewAlbum;
    }

    public void setNewAlbum(boolean newAlbum) {
        isNewAlbum = newAlbum;
    }
}
