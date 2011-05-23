package de.hszigr.gpics.controller;

import de.hszigr.gpics.db.connect.AlbumConnector;
import de.hszigr.gpics.db.connect.BildConnector;
import de.hszigr.gpics.db.connect.NutzerConnector;
import de.hszigr.gpics.db.interfaces.IAlbumConnector;
import de.hszigr.gpics.db.interfaces.IBildConnector;
import de.hszigr.gpics.db.interfaces.INutzerConnector;
import de.hszigr.gpics.util.GPicSUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.File;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan Radusch
 * Date: 20.05.11
 * Time: 08:17
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@SessionScoped
public class AdminPageController {

    private List<String> user;
    private List<String> alben;
    private List<String> bilder;

    public String loadPage(){
        reset();
        try {
            INutzerConnector nutzerConnector = new NutzerConnector();
            Document doc = nutzerConnector.getAllNutzer();
            NodeList nodeListID = doc.getElementsByTagName("id");
            NodeList nodeListName = doc.getElementsByTagName("name");
            for(int i = 0; i<nodeListName.getLength();i++){
                Node userNode = nodeListName.item(i);
                int userID = Integer.parseInt(nodeListID.item(i).getTextContent());
                String userName  = userNode.getTextContent();
                user.add(userName);
                IAlbumConnector albumConnector = new AlbumConnector();
                Document albenDoc = albumConnector.getAlbenForNutzer(userID);
                NodeList nodeListAlbenID = albenDoc.getElementsByTagName("id");
                NodeList nodeListAlbenName = albenDoc.getElementsByTagName("name");
                List<String> tempAlbenList = new ArrayList<String>();
//                for(int j = 0;j<nodeListAlbenName.getLength();j++){
//                    Node albenNode = nodeListAlbenName.item(i);
////                    String id = nodeListAlbenID.item(j).getTextContent();
//                    int albumID = Integer.parseInt(nodeListAlbenID.item(j).getTextContent());
//                    String albumName  = albenNode.getTextContent();
//                    tempAlbenList.add(albumName);
//                    IBildConnector bildConnector = new BildConnector();
//                    Document bilderDoc = bildConnector.getBilderForAlbum(albumID);
//                    NodeList nodeListBilderName = bilderDoc.getElementsByTagName("name");
//                    List<String> tempBilderList = new ArrayList<String>();
//                    for(int k = 0;k<nodeListBilderName.getLength();k++){
//                        Node bilderNode = nodeListBilderName.item(k);
//                        String bildName = bilderNode.getTextContent();
//                        tempBilderList.add(bildName);
//                    }
//                    bilder.add(tempBilderList);
//                }
//                alben.add(tempAlbenList);
            }
            return "adminPage";
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        UserController uc = (UserController) GPicSUtil.getBean("userController");
        uc.setAdmin(false);
        return "index";
    }

    public String loadALben(){
        try {
            String name = FacesContext.getCurrentInstance()
                        .getExternalContext().getRequestParameterMap().get("username");
            INutzerConnector nutzerConnector = new NutzerConnector();
            Document doc = nutzerConnector.getNutzerByName(name);
            int id = Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent());
            IAlbumConnector albumConnector = new AlbumConnector();
            Document albenDoc = albumConnector.getAlbenForNutzer(id);
            NodeList nodeListAlbenName = albenDoc.getElementsByTagName("name");
            for(int i = 0;i<nodeListAlbenName.getLength();i++){
                String album = nodeListAlbenName.item(i).getTextContent();
                alben.add(album);
            }

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return "adminPage";
    }


     public String loadBilder(){
        try {
            String name = FacesContext.getCurrentInstance()
                        .getExternalContext().getRequestParameterMap().get("albumname");
            IAlbumConnector albumConnector = new AlbumConnector();
            Document doc = albumConnector.getAlbumByName(name);
            int id = Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent());
            IBildConnector bildConnector = new BildConnector();
            Document bildDoc = bildConnector.getBilderForAlbum(id);
            NodeList nodeListBildName = bildDoc.getElementsByTagName("name");
            for(int i = 0;i<nodeListBildName.getLength();i++){
                String bild = nodeListBildName.item(i).getTextContent();
                bilder.add(bild);
            }
//            FacesContext.getCurrentInstance().getExternalContext().redirect("adminPage.xhtml");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
         return "adminPage";
    }

    public String deleteUser(){
        String userName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("username");
        for(int i = 0; i<user.size();i++){
            String u = user.get(i);
            if(u.equals(userName)){
                user.remove(i);
            }
        }
        try{
            if(userName!=null){
                INutzerConnector connector = new NutzerConnector();
                Document doc = connector.getNutzerByName(userName);
                int userID = Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent());
                deleteUser(userID);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "adminPage";
    }

    public String deleteAlbum(){
        String albumName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("albumname");
        for(int i = 0; i<alben.size();i++){
            String album = bilder.get(i);
            if(album.equals(albumName)){
                alben.remove(i);
            }
        }
        try{
            if(albumName!=null){
                deleteAlbum(albumName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "adminPage";
    }

    public String deleteBild(){
        String bildName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("bildname");
        for(int i = 0; i<bilder.size();i++){
            String bild = bilder.get(i);
            if(bild.equals(bildName)){
                bilder.remove(i);
            }
        }
        try{
            if(bildName!=null){
                IBildConnector connector = new BildConnector();
                Document doc = connector.getBilderByName(bildName);
                int bildID = Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent());
                deletePic(bildID);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "adminPage";
    }

    private void deleteUser(int userID) throws ConnectException {
        IAlbumConnector albumConnector = new AlbumConnector();
        Document albumDoc = albumConnector.getAlbenForNutzer(userID);
        NodeList nodeList = albumDoc.getElementsByTagName("name");
        for(int i = 0;i<nodeList.getLength();i++){
            Node node = nodeList.item(i);
            String albumName = node.getTextContent();
            deleteAlbum(albumName);
        }

        INutzerConnector nutzerConnector = new NutzerConnector();
        nutzerConnector.deleteNutzer(userID);
    }

    private void deleteAlbum(String albumName) throws ConnectException {
        IAlbumConnector albumConnector = new AlbumConnector();
        IBildConnector bildConnector = new BildConnector();
        Document albumDoc = albumConnector.getAlbumByName(albumName);
        int albumID = Integer.parseInt(albumDoc.getElementsByTagName("id").item(0).getTextContent());
        Document bildDoc = bildConnector.getBilderForAlbum(albumID);
        NodeList bildList = bildDoc.getElementsByTagName("id");
        for(int i = 0;i<bildList.getLength();i++){
            Node node = bildList.item(i);
            int bildID = Integer.parseInt(node.getTextContent());
            deletePic(bildID);
        }
        albumConnector.deleteAlbum(albumName);
    }

    private void deletePic(int bildID) throws ConnectException {
        IBildConnector connector = new BildConnector();
        Document doc = connector.getBildByID(bildID);
        String path = doc.getElementsByTagName("fileposition").item(0).getTextContent();
        File file = new File(path);
        file.delete();
        connector.deleteBild(bildID);
    }

    private void reset(){
        user = new ArrayList<String>();
        alben = new ArrayList<String>();
        bilder = new ArrayList<String>();
    }

    public List<String> getUser() {
        return user;
    }

    public void setUser(List<String> user) {
        this.user = user;
    }

    public List<String> getAlben() {
        return alben;
    }

    public void setAlben(List<String> alben) {
        this.alben = alben;
    }

    public List<String> getBilder() {
        return bilder;
    }

    public void setBilder(List<String> bilder) {
        this.bilder = bilder;
    }
}
