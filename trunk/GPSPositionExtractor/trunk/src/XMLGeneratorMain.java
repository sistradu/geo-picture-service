/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 12.04.11
 * Time: 18:03
 * To change this template use File | Settings | File Templates.
 */
public class XMLGeneratorMain {
    public static void main(String[] args){
        XMLGenerator gen = new XMLGenerator();
        for(int i = 0 ; i < 1000 ; i++){
            gen.addTag("bild" + i,1.0 / 1000.0);
            gen.addSubTag("bild" + i,"name");
            gen.addSubTag("bild" + i,"password");
            gen.addSubTag("bild" + i,"email");
        }

//        gen.addTag("album", 10.0 / 2031.0);
//        gen.addSubTag("album","name");
//        gen.addSubTag("album","description");
//        gen.addSubTag("album","password");
//        gen.addTag("bild", 1000.0 / 2031.0);
//        gen.addSubTag("bild","id");
//        gen.addSubTag("bild","name");
//        gen.addSubTag("bild","description");
//        gen.addSubTag("bild","ispublic");
//        gen.addSubTag("bild","timestamp");
//        gen.addTag("position", 1000.0 / 2031.0);
//        gen.addSubTag("position","id");
//        gen.addSubTag("position","latitude");
//        gen.addSubTag("position","latituderef");
//        gen.addSubTag("position","longitude");
//        gen.addSubTag("position","longituderef");
//        gen.addSubTag("position","altitude");
//        gen.addSubTag("position","direction");
//        gen.addTag("rundreise", 20.0 / 2031.0);
//        gen.addSubTag("rundreise","name");
//        gen.addSubTag("rundreise","description");
//        gen.addSubTag("rundreise","password");
//        gen.generateFile("C:\\Temp",20310);
        gen.generateMultipleFiles("C:\\Temp",20310);
    }
}
