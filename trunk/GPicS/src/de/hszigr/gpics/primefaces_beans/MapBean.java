package de.hszigr.gpics.primefaces_beans;

import de.hszigr.gpics.controller.AlbumController;
import de.hszigr.gpics.controller.Bild;
import de.hszigr.gpics.util.GPicSUtil;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
        for (int i = 0; i < ac.getBilder().size(); i++){
            Bild bild = ac.getBilder().get(i);

            LatLng coord = new LatLng(Double.parseDouble(bild.getLatitude()), Double.parseDouble(bild.getLongitude()));
            Marker m  = new Marker(coord, bild.getName(), bild.getPath());

            simpleModel.addOverlay(m);
        }
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        for (int i = 0; i < bilder.size(); i++){
            if (event.getOverlay().getData().equals(bilder.get(i).getPath())){
                image = bilder.get(i).getContent();
                beschreibung = bilder.get(i).getBeschreibung();
            }
        }

        this.marker = (Marker) event.getOverlay();
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
