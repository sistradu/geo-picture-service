package de.hszigr.gpics.primefaces_beans;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan Radusch
 * Date: 19.04.11
 * Time: 09:53
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
public class GMapsBean implements Serializable {

    private MapModel simpleModel;
    private Marker marker;
    private String filePath = "C:/Users/StRadusch/Pictures/XML/DSC00012.JPG";
    String thumbPath;
    private StreamedContent image;

    public GMapsBean() {
        simpleModel = new DefaultMapModel();
        setThumbPath();
        makeMarker();
    }

    private void setThumbPath() {
        thumbPath = filePath.substring(0, filePath.indexOf("."));
        thumbPath = thumbPath + "_thumb.jpg";
    }

    private void makeMarker() {
//        PositionExtractor extractor = new PositionExtractor();
//        Position pos = null;
//        try {
//            pos = extractor.getPosition(filePath);
//            InputStream stream = new FileInputStream(thumbPath);
//            image = new DefaultStreamedContent(stream, "image/jpeg");
//        } catch (JpegProcessingException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (MetadataException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        LatLng coord1 = new LatLng(Double.parseDouble(pos.getLatitudeDecimal()), Double.parseDouble(pos.getLongitudeDecimal()));
//        Marker m  = new Marker(coord1, "Hochschule Zittau/GÃ¶rlitz");
////        m.setIcon("DSC00012.JPG");
////        simpleModel.addOverlay(new Marker(coord1, "Hochschule Zittau/GÃ¶rlitz", thumbPath, "http://www.blind-summit.co.uk/wp-content/plugins/google-map/images/marker_red.png"));
//        simpleModel.addOverlay(m);
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
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
}

