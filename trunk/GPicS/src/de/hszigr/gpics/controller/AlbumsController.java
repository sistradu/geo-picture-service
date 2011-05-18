package de.hszigr.gpics.controller;

import de.hszigr.gpics.db.connect.AlbumConnector;
import de.hszigr.gpics.db.interfaces.IAlbumConnector;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import java.net.ConnectException;
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
public class AlbumsController {

    private String suchString;
    private Document alben;
    private String albennamen;
    private List<String> albenliste = new ArrayList<String>();
    private List<String> albenliste_suche = new ArrayList<String>();
    private int markiertesAlbum;
    private IAlbumConnector ialb;

    public AlbumsController(){
         initAllAlbums();

    }



    @SuppressWarnings("unchecked")
    private void initAllAlbums()
    {
        ialb = new AlbumConnector();
        try {
            alben = ialb.getAllAlben();
            NodeList nodeliste = alben.getElementsByTagName("name");
            for (int i = 0; i<nodeliste.getLength();i++) {
            this.albenliste.add(nodeliste.item(i).getTextContent());
        }
        } catch (ConnectException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public String getAllAlbums()
    {
        if (getAlbenliste().isEmpty())
        {
            initAllAlbums();
        }
        return "allAlbums.xhtml";
    }

    public void loadAll(ActionEvent ae)
    {   albenliste.clear();
        initAllAlbums();
    }

    public void loadSearched(ActionEvent ae)
    {
        search();
    }

    public String search(){

           ialb = new AlbumConnector();
               try {
                   alben = ialb.getAlbenWithNameContaining(suchString);
                   NodeList nodeliste = alben.getElementsByTagName("name");
                   albenliste.clear();
                   for(int k=0; k<nodeliste.getLength();k++){
                   this.albenliste.add(nodeliste.item(k).getTextContent());
               }

               } catch (ConnectException e1) {
                   e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                 }

            return "allAlbums.xhtml";
    }


   public String getSuchString() {
        return suchString;
    }

    public void setSuchString(String suchString) {
        this.suchString = suchString;
    }

    public Document getAlben() {
        return alben;
    }

    public void setAlben(Document alben) {
        
        this.alben = alben;
    }



    public int getMarkiertesAlbum() {
        return markiertesAlbum;
    }

    public void setMarkiertesAlbum(int markiertesAlbum) {
        this.markiertesAlbum = markiertesAlbum;
    }

    public List<String> getAlbenliste() {
        return albenliste;
    }

    public void setAlbenliste(List<String> albenliste) {
        this.albenliste = albenliste;
    }

    public List<String> getAlbenliste_suche() {
        return albenliste_suche;
    }

    public void setAlbenliste_suche(List<String> albenliste_suche) {
        this.albenliste_suche = albenliste_suche;
    }


}
