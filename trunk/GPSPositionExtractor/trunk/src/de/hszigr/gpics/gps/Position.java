/**
 * 
 */
package de.hszigr.gpics.gps;

/**
 * @author Stefan Radusch
 *
 */
public class Position {

	private String latitudeRef;
	private String latitude;
	private String latitudeDecimal;
	private String longitudeRef;
	private String longitude;
	private String longitudeDecimal;
	private String altiude;
	private String direction;
	
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
	public String getLatitudeDecimal() {
		return latitudeDecimal;
	}
	public void setLatitudeDecimal(String latitudeDecimal) {
		this.latitudeDecimal = latitudeDecimal;
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
	public String getLongitudeDecimal() {
		return longitudeDecimal;
	}
	public void setLongitudeDecimal(String longitudeDecimal) {
		this.longitudeDecimal = longitudeDecimal;
	}
	public void setAltiude(String altiude) {
		this.altiude = altiude;
	}
	public String getAltiude() {
		return altiude;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getDirection() {
		return direction;
	}
	
}
