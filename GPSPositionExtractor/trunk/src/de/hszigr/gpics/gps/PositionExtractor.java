/**
 * 
 */
package de.hszigr.gpics.gps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;
import de.micromata.opengis.kml.v_2_2_0.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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
		File file = new File(args[0]);

        try {
            ImageInformation pos = e.getImageInformation(file);
            Kml kml = e.createKml("Görlitz, Deutschland", "Mensa HS ZI/GR", pos);
             e.printKml(kml);
        } catch (ParseException e1) {
            e1.printStackTrace();
        } catch (JAXBException e1) {
            e1.printStackTrace();
        }
//		e.printTags(file);
	}

    public void printKml(Kml kml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance( Kml.class );
        Marshaller m = context.createMarshaller();
        m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
        m.marshal(kml, System.out);
    }

    public Kml createKml(String name, String description, ImageInformation position) {
//        final Kml kml = new Kml();
//        kml.createAndSetPlacemark().withName(name)
//                .withOpen(Boolean.TRUE)
//                .createAndSetPoint()
//                .addToCoordinates(Double.parseDouble(position.getLongitudeDecimal()), Double.parseDouble(position.getLatitudeDecimal()));
        final Kml kml = new Kml();
        Placemark place = kml.createAndSetPlacemark().withName(name).withTimePrimitive(new TimeStamp().withWhen(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(position.getTimeStamp().getTime())));
        place.withOpen(true);
        place.setDescription(description);
        place.createAndSetPoint().addToCoordinates(Double.parseDouble(position.getLongitudeDecimal()), Double.parseDouble(position.getLatitudeDecimal()), Double.parseDouble(position.getAltitude()));
        return kml;
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
	 * @param file image
	 * @return Position
	 * @throws JpegProcessingException
	 * @throws MetadataException
	 */
	public ImageInformation getImageInformation(File file) throws JpegProcessingException, MetadataException, ParseException {
		List<Tag> gpsTags = getGPSTags(file);
		return extractImageInformation(gpsTags);
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
	private ImageInformation extractImageInformation(List<Tag> gpsTags) throws MetadataException, ParseException {
		ImageInformation pos = new ImageInformation();
        String date = null, time = null;
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
				pos.setAltitude(extractDirectionOrAltitude(tag.getDescription()));
			}
			if(tag.getTagName().equals("Unknown tag (0x0011)")){
				pos.setDirection(extractDirectionOrAltitude(tag.getDescription()));
			}
            if(tag.getTagName().equals("GPS Time-Stamp")){
				  time = tag.getDescription().replace(" UTC","");
			}
            if(tag.getTagName().equals("Unknown tag (0x001d)")){
				  date = tag.getDescription();
			}
		}
        if(date != null && time != null){
                GregorianCalendar calendar = new GregorianCalendar(Locale.getDefault());
                calendar.setTime(new SimpleDateFormat("yyyy:MM:dd-hh:mm:ss").parse(date + "-" + time));
                pos.setTimeStamp(calendar);
        }
		return pos;
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
				pos.setAltitude(extractDirectionOrAltitude(tag.getDescription()));
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
        if(!description.contains("/")){
            return description.substring(0,description.indexOf(" "));
        }
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
                if(tag.getDirectoryName().equals("Exif")){
                    extractThumbnail(metadata, file.getAbsolutePath());
                }
			}
		}
		return tagList;
	}

    private void extractThumbnail(Metadata metadata, String path) {
        path = path.substring(0,path.lastIndexOf("."));
        path = path + "_thumb.jpg";
        try {
            ExifDirectory dir = (ExifDirectory)metadata.getDirectory(ExifDirectory.class);
            dir.writeThumbnail(path);
        } catch (MetadataException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
