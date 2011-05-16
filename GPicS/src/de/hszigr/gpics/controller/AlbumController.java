package de.hszigr.gpics.controller;

import de.hszigr.gpics.db.connect.AlbumConnector;
import de.hszigr.gpics.db.connect.BildConnector;
import de.hszigr.gpics.db.interfaces.IAlbumConnector;
import de.hszigr.gpics.db.interfaces.IBildConnector;
import org.w3c.dom.Document;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
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
    private String albumName;
    private String albumDescription;
    private ArrayList<Bild> bilder = new ArrayList<Bild>();

    public AlbumController() throws Exception{
        albumName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("AlbumName");

        loadAlbum(albumName);
    }

    private void loadAlbum(String name) throws Exception{
        IAlbumConnector iac = new AlbumConnector();
        album = iac.getAlbumByName(name);

        IBildConnector ibc = new BildConnector();
        Document bilderXML = ibc.getBilderByName(name);


        for (int i = 0; i < bilderXML.getElementsByTagName("fileposition").getLength(); i++){
            Bild bild  = new Bild();
            bild.setName(bilderXML.getElementsByTagName("name").item(i).getTextContent());

            String path = (bilderXML.getElementsByTagName("fileposition").item(i).getTextContent().substring(1));
            bild.setPath(path);
            path = path.substring(0, path.lastIndexOf(".")) + "_thumb"+path.substring(path.lastIndexOf("."));

            bild.setPathThumbnail(path);

            bild.setLatitude(bilderXML.getElementsByTagName("latitudedecimal").item(i).getTextContent());
            bild.setLongitude(bilderXML.getElementsByTagName("longitudedecimal").item(i).getTextContent());
            
            bilder.add(bild);
        }

        
    }

    public Document getAlbum() {
        return album;
    }

    public List<Bild> getBilder() {
        return bilder;
    }

    public String getAlbumName() {

        return album.getElementsByTagName("name").item(0).getTextContent();
    }

    public String getAlbumDescription() {
        return album.getElementsByTagName("description").item(0).getTextContent();
    }
}
