package de.hszigr.gpics.gps;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 05.04.11
 * Time: 12:39
 * To change this template use File | Settings | File Templates.
 */
public class ImageInformation extends Position {

    private GregorianCalendar timeStamp;

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
