package de.hszigr.gpics.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileOutputStream;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan Radusch
 * Date: 27.04.11
 * Time: 13:51
 * To change this template use File | Settings | File Templates.
 */
public class ImageDataExtractorTest {
    @Test
    public void testGetPosition() throws Exception {
        ImageDataExtractor extractor = new ImageDataExtractor();

//        File f = new File("D:/Bilder XML/101MSDCF");
//        File[] files = f.listFiles();
//        for(File file : files){
//            Position p = extractor.getPosition(file);
//            if(p==null){
//                Assert.fail();
//            }else{
//                if(p.getAltitude()==null){
//                    Assert.fail("Altitude is null, File: " + file.getAbsolutePath());
//                }
//                if(p.getDirection()==null){
//                    Assert.fail("Direction is null, File: " + file.getAbsolutePath());
//                }
//                if(p.getLongitude()==null){
//                    Assert.fail("Longitude is null, File: " + file.getAbsolutePath());
//                }
//                if(p.getLongitudeRef()==null){
//                    Assert.fail("LongitudeRef is null, File: " + file.getAbsolutePath());
//                }
//                if(p.getLatitude()==null){
//                    Assert.fail("Latitude is null, File: " + file.getAbsolutePath());
//                }
//                if(p.getLatitudeRef()==null){
//                    Assert.fail("LatitudeRef is null, File: " + file.getAbsolutePath());
//                }
//                if(p.getTimeStamp()==null){
//                    Assert.fail("TimeStamp is null, File: " + file.getAbsolutePath());
//                }
//            }
//        }

        Position pos = extractor.getPosition("C:/Users/StRadusch/Pictures/XML/DSC00012.JPG");

        Assert.assertEquals("N",pos.getLatitudeRef());
        Assert.assertEquals("51\"8'55.8",pos.getLatitude());
        Assert.assertEquals("E", pos.getLongitudeRef());
        Assert.assertEquals("14\"59'55.985", pos.getLongitude());
        Assert.assertEquals("277.5", pos.getDirection());
        Assert.assertEquals("105.5",pos.getAltitude());
        GregorianCalendar cal = pos.getTimeStamp();
        
        Assert.assertEquals(8, cal.get(GregorianCalendar.HOUR_OF_DAY));
        Assert.assertEquals(1, cal.get(GregorianCalendar.MINUTE));
        Assert.assertEquals(41, cal.get(GregorianCalendar.SECOND));
        Assert.assertEquals(0, cal.get(GregorianCalendar.MILLISECOND));
        Assert.assertEquals(31, cal.get(GregorianCalendar.DAY_OF_MONTH));
        Assert.assertEquals(3, cal.get(GregorianCalendar.MONTH)+1);
        Assert.assertEquals(2011, cal.get(GregorianCalendar.YEAR));
    }

    @Test
    public void testExtractThumbnail() throws Exception {
        ImageDataExtractor extractor = new ImageDataExtractor();
        byte[] data = extractor.extractThumbnail("C:/Users/StRadusch/Pictures/XML/DSC00012.JPG");
        Assert.assertNotSame(0, data.length);
//        FileOutputStream out = new FileOutputStream("C:/Users/StRadusch/Desktop/DSC00012.JPG");
//        out.write(data);
//        out.close();
    }
}
