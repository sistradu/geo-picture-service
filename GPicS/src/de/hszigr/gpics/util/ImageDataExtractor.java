/**
 *
 */
package de.hszigr.gpics.util;

import java.io.File;
import java.io.FileNotFoundException;
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

/**
 * @author Stefan Radusch
 */
public class ImageDataExtractor {

    /**
     * Extrahiert die GPS-Informationen aus einem jpg-File und gibt ein Positionsobjekt zurück.
     * Sind einige Tags im Bild nicht vorhanden, wie zum Beispiel Longitude, weil vielleicht kein Signal bei der Kamera
     * verfügbar war, ist das entsprechende Attribut im Positionsobjekt null.
     *
     * @param file image
     * @return Position
     * @throws JpegProcessingException
     * @throws MetadataException
     */
    public Position getPosition(File file) throws JpegProcessingException, MetadataException, ParseException, FileNotFoundException {
        List<Tag> gpsTags = getGPSTags(file);
        return extractPosition(gpsTags);
    }

//    /**
//	 * Extract the gps information from a jpg-file and return an object with position informations.
//	 * @param file image
//	 * @return Position
//	 * @throws JpegProcessingException
//	 * @throws MetadataException
//	 */
//	public ImageInformation getImageInformation(File file) throws JpegProcessingException, MetadataException, ParseException {
//		List<Tag> gpsTags = getGPSTags(file);
//		return extractImageInformation(gpsTags);
//	}

    /**
     * Extrahiert die GPS-Informationen aus einem jpg-File und gibt ein Positionsobjekt zurück.
     *Sind einige Tags im Bild nicht vorhanden, wie zum Beispiel Longitude, weil vielleicht kein Signal bei der Kamera
     * verfügbar war, ist das entsprechende Attribut im Positionsobjekt null.
     *
     * @param file path to image
     * @return Position
     * @throws JpegProcessingException
     * @throws MetadataException
     */
    public Position getPosition(String file) throws JpegProcessingException, MetadataException, ParseException, FileNotFoundException {
        List<Tag> gpsTags = getGPSTags(new File(file));
        return extractPosition(gpsTags);
    }

    /**
     * return thumbnail-data
     * @param path to image
     * @return data byte-array with image data
     * @throws JpegProcessingException
     * @throws MetadataException
     */
    public byte[] extractThumbnail(String path) throws JpegProcessingException, MetadataException {
        File file = new File(path);
        Metadata metadata = JpegMetadataReader.readMetadata(file);
        ExifDirectory dir = (ExifDirectory) metadata.getDirectory(ExifDirectory.class);
        byte[] data = dir.getThumbnailData();
        return data;
    }

//    /**
//	 * @param gpsTags
//	 * @throws MetadataException
//	 */
//	private ImageInformation extractImageInformation(List<Tag> gpsTags) throws MetadataException, ParseException {
//		ImageInformation pos = new ImageInformation();
//        String date = null, time = null;
//		for(Tag tag : gpsTags){
//			if(tag.getTagName().equals("GPS Latitude Ref")){
//				pos.setLatitudeRef(tag.getDescription());
//			}
//			if(tag.getTagName().equals("GPS Latitude")){
//				pos.setLatitude(tag.getDescription());
//				pos.setLatitudeDecimal(getDecimalCoordinate(tag.getDescription()));
//			}
//			if(tag.getTagName().equals("GPS Longitude Ref")){
//				pos.setLongitudeRef(tag.getDescription());
//			}
//			if(tag.getTagName().equals("GPS Longitude")){
//				pos.setLongitude(tag.getDescription());
//				pos.setLongitudeDecimal(getDecimalCoordinate(tag.getDescription()));
//			}
//			if(tag.getTagName().equals("GPS Altitude")){
//				pos.setAltitude(extractDirectionOrAltitude(tag.getDescription()));
//			}
//			if(tag.getTagName().equals("Unknown tag (0x0011)")){
//				pos.setDirection(extractDirectionOrAltitude(tag.getDescription()));
//			}
//            if(tag.getTagName().equals("GPS Time-Stamp")){
//				  time = tag.getDescription().replace(" UTC","");
//			}
//            if(tag.getTagName().equals("Unknown tag (0x001d)")){
//				  date = tag.getDescription();
//			}
//		}
//        if(date != null && time != null){
//                GregorianCalendar calendar = new GregorianCalendar(Locale.getDefault());
//                calendar.setTime(new SimpleDateFormat("yyyy:MM:dd-hh:mm:ss").parse(date + "-" + time));
//                pos.setTimeStamp(calendar);
//        }
//		return pos;
//	}

    /**
     * @param gpsTags
     * @throws MetadataException
     */
    private Position extractPosition(List<Tag> gpsTags) throws MetadataException, ParseException {
        Position pos = new Position();
        String date = null;
        String time = null;
        for (Tag tag : gpsTags) {
            if (tag.getTagName().equals("GPS Latitude Ref")) {
                pos.setLatitudeRef(tag.getDescription());
            }
            if (tag.getTagName().equals("GPS Latitude")) {
                pos.setLatitude(tag.getDescription());
//				pos.setLatitudeDecimal(getDecimalCoordinate(tag.getDescription()));
            }
            if (tag.getTagName().equals("GPS Longitude Ref")) {
                pos.setLongitudeRef(tag.getDescription());
            }
            if (tag.getTagName().equals("GPS Longitude")) {
                pos.setLongitude(tag.getDescription());
//				pos.setLongitudeDecimal(getDecimalCoordinate(tag.getDescription()));
            }
            if (tag.getTagName().equals("GPS Altitude")) {
                pos.setAltitude(extractDirectionOrAltitude(tag.getDescription()));
            }
            if (tag.getTagName().equals("Unknown tag (0x0011)")) {
                pos.setDirection(extractDirectionOrAltitude(tag.getDescription()));
            }
            if (tag.getTagName().equals("GPS Time-Stamp")) {
                time = tag.getDescription().replace(" UTC", "");
            }
            if (tag.getTagName().equals("Unknown tag (0x001d)")) {
                date = tag.getDescription();
            }
        }
        if (date != null && time != null) {
            GregorianCalendar calendar = new GregorianCalendar(Locale.getDefault());
            calendar.setTime(new SimpleDateFormat("yyyy:MM:dd-hh:mm:ss").parse(date + "-" + time));
            pos.setTimeStamp(calendar);
        }
        return pos;
    }

    /**
     * @param description
     * @return
     */
    private String extractDirectionOrAltitude(String description) {
        if (!description.contains("/")) {
            return description.substring(0, description.indexOf(" "));
        }
        double direction = Double.parseDouble(description.substring(0, description.indexOf("/")));
        double divisor = Double.parseDouble(description.substring(description.indexOf("/") + 1, description.indexOf(" ")));
        direction /= divisor;
        return String.valueOf(direction);
    }

    @SuppressWarnings("rawtypes")
    private List<Tag> getGPSTags(File file) throws JpegProcessingException {
        List<Tag> tagList = new ArrayList<Tag>();
        Metadata metadata = JpegMetadataReader.readMetadata(file);
        Iterator directories = metadata.getDirectoryIterator();
        while (directories.hasNext()) {
            Directory directory = (Directory) directories.next();
            Iterator tags = directory.getTagIterator();
            while (tags.hasNext()) {
                Tag tag = (Tag) tags.next();
                if (tag.getDirectoryName().equals("GPS")) {
                    tagList.add(tag);
                }
//                if (tag.getDirectoryName().equals("Exif")) {
//                    extractThumbnail(metadata, file.getAbsolutePath());
//                }
            }
        }
        return tagList;
    }
}
