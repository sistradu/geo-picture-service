/**
 * 
 */
package de.hszigr.gpics.gps;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;

/**
 * @author Stefan Radusch
 *
 */
public class PositionExtractor {

	/**
	 * @param args
	 * @throws JpegProcessingException 
	 * @throws MetadataException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws JpegProcessingException, MetadataException, FileNotFoundException {
		PositionExtractor e = new PositionExtractor();
		File file = new File("C:/Users/StRadusch/Pictures/DSC00012.JPG");
		Position pos = e.getPosition(file);
		e.printTags(file);
	}
	
	/**
	 * Extract the gps information from a jpg-file and return an object with position informations.
	 * @param file image
	 * @return Position
	 * @throws JpegProcessingException
	 * @throws MetadataException
	 */
	public Position getPosition(File file) throws JpegProcessingException, MetadataException{
		List<Tag> gpsTags = getGPSTags(file);
		return extractPosition(gpsTags);
	}
	
	/**
	 * Extract the gps information from a jpg-file and return an object with position informations.
	 * @param file path to image
	 * @return Position
	 * @throws JpegProcessingException
	 * @throws MetadataException
	 */
	public Position getPosition(String file) throws JpegProcessingException, MetadataException{
		List<Tag> gpsTags = getGPSTags(new File(file));
		return extractPosition(gpsTags);
	}

	@SuppressWarnings("rawtypes")
	@Deprecated
	public void printTags(File file) throws JpegProcessingException{
		Metadata metadata = JpegMetadataReader.readMetadata(file);
		Iterator directories = metadata.getDirectoryIterator();
		while(directories.hasNext()){
			Directory directory = (Directory)directories.next();
			Iterator tags = directory.getTagIterator();
			while(tags.hasNext()){
				Tag tag = (Tag)tags.next();
				System.out.println(tag);
			}
		}
	}
	
	/**
	 * @param gpsTags
	 * @throws MetadataException 
	 */
	private Position extractPosition(List<Tag> gpsTags) throws MetadataException {
		Position pos = new Position();
		for(Tag tag : gpsTags){
			if(tag.getTagName().equals("GPS Latitude Ref")){
				pos.setLatitudeRef(tag.getDescription());
			}
			if(tag.getTagName().equals("GPS Latitude")){
				pos.setLatitude(tag.getDescription());
				pos.setLatitudeDecimal(getDecimalValue(tag.getDescription()));
			}
			if(tag.getTagName().equals("GPS Longitude Ref")){
				pos.setLongitudeRef(tag.getDescription());
			}
			if(tag.getTagName().equals("GPS Longitude")){
				pos.setLongitude(tag.getDescription());
				pos.setLongitudeDecimal(getDecimalValue(tag.getDescription()));
			}
			if(tag.getTagName().equals("GPS Altitude")){
				pos.setAltiude(extractDirectionOrAltitude(tag.getDescription()));
			}
			if(tag.getTagName().equals("Unknown tag (0x0011)")){
				pos.setDirection(extractDirectionOrAltitude(tag.getDescription()));
			}
		}
		return pos;
	}

	/**
	 * @param description
	 * @return
	 */
	private String extractDirectionOrAltitude(String description) {
		double direction = Double.parseDouble(description.substring(0, description.indexOf("/")));
		double divisor = Double.parseDouble(description.substring(description.indexOf("/")+1, description.indexOf(" ")));
		direction /= divisor;
		return String.valueOf(direction);
	}
	
	/**
	 * @param position
	 * @return
	 */
	private String getDecimalValue(String position) {
		double degree = Double.parseDouble(position.substring(0, position.indexOf("\"")));
		double minutes = Double.parseDouble(position.substring(position.indexOf("\"")+1, position.indexOf("'")));
		double seconds = Double.parseDouble(position.substring(position.indexOf("'")+1));
		degree += minutes/60;
		degree += seconds/3600;
		return String.valueOf(degree);
	}

	@SuppressWarnings("rawtypes")
	private List<Tag> getGPSTags(File file) throws JpegProcessingException{
		List<Tag> tagList = new ArrayList<Tag>();
		Metadata metadata = JpegMetadataReader.readMetadata(file);
		Iterator directories = metadata.getDirectoryIterator();
		while(directories.hasNext()){
			Directory directory = (Directory)directories.next();
			Iterator tags = directory.getTagIterator();
			while(tags.hasNext()){
				Tag tag = (Tag)tags.next();
				if(tag.getDirectoryName().equals("GPS")){
					tagList.add(tag);
				}
			}
		}
		return tagList;
	}

}
