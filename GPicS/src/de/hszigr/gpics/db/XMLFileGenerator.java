package de.hszigr.gpics.db;

import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 10.05.11
 * Time: 17:38
 * To change this template use File | Settings | File Templates.
 */
public class XMLFileGenerator {

    public static void main(String[] args){
        XMLFileGenerator.createAll("C:\\files");
    }

    public static void createAll(String dir){
        File file = new File(dir);
        if(!file.exists())
            file.mkdir();
        XMLFileGenerator.createAlben(dir);
        XMLFileGenerator.createBilder(dir);
        XMLFileGenerator.createNutzer(dir);
    }

    private static void createNutzer(String dir){
        String nutzerDir = dir + "\\nutzer";
        File file = new File(nutzerDir);
        if(!file.exists())
            file.mkdir();
        MockNutzerConnector con = new MockNutzerConnector();
        try {
            Document doc = con.getAllNutzer();
            File nutzerFile = new File(nutzerDir + "\\nutzer.xml");
            if(!nutzerFile.exists())
                nutzerFile.createNewFile();
            TransformerFactory tranFactory = TransformerFactory.newInstance();
                Transformer aTransformer = tranFactory.newTransformer();
                aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

                Source src = new DOMSource(doc);
                Result dest = new StreamResult(nutzerFile);
                aTransformer.transform(src, dest);
        } catch (ConnectException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TransformerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        for(int i = 0 ; true ; i++){
            try{
                Document doc = con.getNutzerByID(i);
                File xmlFile = new File(nutzerDir + "\\" + doc.getElementsByTagName("id").item(0).getTextContent() + ".xml");
                if(!xmlFile.exists())
                    xmlFile.createNewFile();
                TransformerFactory tranFactory = TransformerFactory.newInstance();
                Transformer aTransformer = tranFactory.newTransformer();
                aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

                Source src = new DOMSource(doc);
                Result dest = new StreamResult(xmlFile);
                aTransformer.transform(src, dest);

            }catch(Exception ex){
                break;
            }
        }
    }

    private static void createBilder(String dir){
        String bilderDir = dir + "\\bilder";
        File file = new File(bilderDir);
        MockBildConnector con = new MockBildConnector();
        if(!file.exists())
            file.mkdir();
        try {
            Document doc = con.getBilderByName("");
            File bilderFile = new File(bilderDir + "\\bilder.xml");
            if(!bilderFile.exists())
                bilderFile.createNewFile();
            TransformerFactory tranFactory = TransformerFactory.newInstance();
                Transformer aTransformer = tranFactory.newTransformer();
                aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

                Source src = new DOMSource(doc);
                Result dest = new StreamResult(bilderFile);
                aTransformer.transform(src, dest);
        } catch (ConnectException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TransformerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        for(int i = 0 ; true ; i++){
            try{
                Document doc = con.getBildByID(i);
                File xmlFile = new File(bilderDir + "\\" + doc.getElementsByTagName("id").item(0).getTextContent() + ".xml");
                if(!xmlFile.exists())
                    xmlFile.createNewFile();
                TransformerFactory tranFactory = TransformerFactory.newInstance();
                Transformer aTransformer = tranFactory.newTransformer();
                aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

                Source src = new DOMSource(doc);
                Result dest = new StreamResult(xmlFile);
                aTransformer.transform(src, dest);

            }catch(Exception ex){
                break;
            }
        }
    }

    private static void createAlben(String dir){
        String albenDir = dir + "\\alben";
        File file = new File(albenDir);
        MockAlbumConnector con = new MockAlbumConnector();
        if(!file.exists())
            file.mkdir();
        try {
            Document doc = con.getAllAlben();
            File albenFile = new File(albenDir + "\\alben.xml");
            if(!albenFile.exists())
                albenFile.createNewFile();
            TransformerFactory tranFactory = TransformerFactory.newInstance();
                Transformer aTransformer = tranFactory.newTransformer();
                aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

                Source src = new DOMSource(doc);
                Result dest = new StreamResult(albenFile);
                aTransformer.transform(src, dest);
        } catch (ConnectException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TransformerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        for(int i = 0 ; true ; i++){
            try{
                Document doc = con.getAlbumByID(i);
                File xmlFile = new File(albenDir + "\\" + doc.getElementsByTagName("id").item(0).getTextContent() + ".xml");
                if(!xmlFile.exists())
                    xmlFile.createNewFile();
                TransformerFactory tranFactory = TransformerFactory.newInstance();
                Transformer aTransformer = tranFactory.newTransformer();
                aTransformer.setOutputProperty(OutputKeys.INDENT, "yes"); 

                Source src = new DOMSource(doc);
                Result dest = new StreamResult(xmlFile);
                aTransformer.transform(src, dest);
            }catch(Exception ex){
                break;
            }
        }
    }

}
