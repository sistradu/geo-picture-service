package de.hszigr.gpics.util;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan Radusch
 * Date: 11.05.11
 * Time: 10:49
 * To change this template use File | Settings | File Templates.
 */
public class ImageUtil {

    public static StreamedContent getStreamContent(String path) throws IOException {
        InputStream stream = new FileInputStream(path);
        StreamedContent image = new DefaultStreamedContent(stream, "image/jpeg");
//        stream.close();
        return image;
    }
}
