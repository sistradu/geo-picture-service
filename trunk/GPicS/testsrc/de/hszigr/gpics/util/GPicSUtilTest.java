package de.hszigr.gpics.util;

import org.junit.Assert;
import org.junit.Test;
import org.primefaces.model.StreamedContent;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan Radusch
 * Date: 12.05.11
 * Time: 20:13
 * To change this template use File | Settings | File Templates.
 */
public class GPicSUtilTest {
    @Test
    public void testGetStreamContent() throws Exception {

        String imagePath = "D:/upload/DSC00012.JPG";
        File file = new File(imagePath);
        if(!file.exists()){
            imagePath = "/home/test/DSC00012.JPG";
        }
        StreamedContent content = GPicSUtil.getStreamContent(imagePath);
        Assert.assertNotSame(null, content);

        imagePath = "D:/upload/gpics.jpg";
        file = new File(imagePath);
        if(!file.exists()){
            imagePath = "/home/pics/gpics.jpg";
        }
        content = GPicSUtil.getStreamContent(imagePath);
        Assert.assertNotSame(null, content);
    }
}
