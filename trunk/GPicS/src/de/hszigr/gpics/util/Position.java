/**
 * 
 */
package de.hszigr.gpics.util;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * @author Stefan Radusch
 *
 */
public class Position {

	private String latitudeRef = "";
	private String latitude = "";
//	private String latitudeDecimal;
	private String longitudeRef = "";
	private String longitude = "";
//	private String longitudeDecimal;
	private String altitude = "";
	private String direction = "";
    private GregorianCalendar timeStamp;
	
	public String getLatitudeRef() {
		return latitudeRef;
	}
	public void setLatitudeRef(String latitudeRef) {
		this.latitudeRef = latitudeRef;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitudeRef() {
		return longitudeRef;
	}
	public void setLongitudeRef(String longitudeRef) {
		this.longitudeRef = longitudeRef;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}
	public String getAltitude() {
		return altitude;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getDirection() {
		return direction;
	}

    public GregorianCalendar getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(GregorianCalendar timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString(){
        String string = super.toString();
        if(this.getTimeStamp() != null){
            string += "Time: " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(this.getTimeStamp().getTime());
        }
        return string;
    }
}
