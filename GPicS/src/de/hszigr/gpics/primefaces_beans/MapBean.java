package de.hszigr.gpics.primefaces_beans;

import de.hszigr.gpics.controller.AlbumController;
import de.hszigr.gpics.controller.Bild;
import de.hszigr.gpics.util.GPicSUtil;
import de.hszigr.gpics.util.MessagePropertiesBean;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.map.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan Radusch
 * Date: 19.04.11
 * Time: 09:53
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
@SessionScoped
public class MapBean implements Serializable {

    private MapModel simpleModel = new DefaultMapModel();
    private Marker marker;
    private List<Bild> bilder = new ArrayList<Bild>();

    private StreamedContent image;
    private String beschreibung;

    public MapBean() {

        makeMarker();
    }

    public void doIt(){
        makeMarker();
    }

    public void makeMarker() {
        simpleModel = new DefaultMapModel();
        AlbumController ac = (AlbumController) GPicSUtil.getBean("albumController");
        bilder = ac.getBilder();
        Polyline polyline = new Polyline();
        for (int i = 0; i < ac.getBilder().size(); i++){
            Bild bild = bilder.get(i);

            LatLng coord = new LatLng(Double.parseDouble(bild.getLatitude()), Double.parseDouble(bild.getLongitude()));

            polyline.getPaths().add(coord);

            Marker m  = new Marker(coord, bild.getName(), bild.getPath());

            simpleModel.addOverlay(m);
        }

        polyline.setStrokeWeight(5);
        polyline.setStrokeColor("#FF9900");
        polyline.setStrokeOpacity(0.7);

        simpleModel.addOverlay(polyline);
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        for (int i = 0; i < bilder.size(); i++){
            if (event.getOverlay().getData().equals(bilder.get(i).getPath())){
                image = readContent(bilder.get(i).getPath());
                MessagePropertiesBean msgPB = new MessagePropertiesBean();
                if (bilder.get(i).getPath().equals(msgPB.getPropertiesMessage("defaultPicturePath"))){
                    beschreibung = msgPB.getPropertiesMessage("pictureNotFound");
                }else{
                    beschreibung = bilder.get(i).getBeschreibung();
                }
            }
        }

        this.marker = (Marker) event.getOverlay();
    }

    private StreamedContent readContent(String path) {
        StreamedContent back = new DefaultStreamedContent();
        try {
            InputStream stream = new FileInputStream(path);
            back = new DefaultStreamedContent(stream, "image/jpeg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return back;  //To change body of created methods use File | Settings | File Templates.
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public Marker getMarker() {
        return marker;
    }

    public StreamedContent getImage(){
        return image;
    }

    public String getBeschreibung() {
        return beschreibung;
    }
}