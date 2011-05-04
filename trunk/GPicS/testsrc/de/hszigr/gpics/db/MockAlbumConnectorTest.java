package de.hszigr.gpics.db;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 03.05.11
 * Time: 17:23
 * To change this template use File | Settings | File Templates.
 */
public class MockAlbumConnectorTest {

    private MockAlbumConnector connector;

    @Before
    public void setUp() throws Exception {
        connector = new MockAlbumConnector();
    }

    @After
    public void tearDown() throws Exception {
        connector = null;
    }

    @Test
    public void testGetAlbumByID() throws Exception {
        try{
            connector.getAlbum(3);
            Assert.fail("Exception should have been thrown!");
        }catch(Exception ex){
            //OK!
        }
        org.w3c.dom.Document doc = connector.getAlbum(0);
        NodeList idNodes = doc.getElementsByTagName("id");
        NodeList nameNodes = doc.getElementsByTagName("name");
        NodeList passwordNodes = doc.getElementsByTagName("password");
        NodeList descriptionNodes = doc.getElementsByTagName("description");
        NodeList bilderNodes = doc.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("0", idNodes.item(0).getTextContent());
        Assert.assertEquals("Görlitz", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die Stadt Görlitz.", descriptionNodes.item(0).getTextContent());
        String password = "goerlitz";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("1", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("2", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("3", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("4", bilderNodes.item(3).getAttributes().item(0).getTextContent());

        doc = connector.getAlbum(1);
        idNodes = doc.getElementsByTagName("id");
        nameNodes = doc.getElementsByTagName("name");
        passwordNodes = doc.getElementsByTagName("password");
        descriptionNodes = doc.getElementsByTagName("description");
        bilderNodes = doc.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("1", idNodes.item(0).getTextContent());
        Assert.assertEquals("Zittau", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die Stadt Zittau.", descriptionNodes.item(0).getTextContent());
        password = "zittau";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("5", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("6", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("8", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("10", bilderNodes.item(3).getAttributes().item(0).getTextContent());

        doc = connector.getAlbum(2);
        idNodes = doc.getElementsByTagName("id");
        nameNodes = doc.getElementsByTagName("name");
        passwordNodes = doc.getElementsByTagName("password");
        descriptionNodes = doc.getElementsByTagName("description");
        bilderNodes = doc.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("2", idNodes.item(0).getTextContent());
        Assert.assertEquals("Oberlausitz", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die schöne Oberlausitz.", descriptionNodes.item(0).getTextContent());
        password = "oberlausitz";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("7", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("9", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("11", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("12", bilderNodes.item(3).getAttributes().item(0).getTextContent());
    }

    @Test
    public void testGetAlbumByName() throws Exception {
        org.w3c.dom.Document doc = connector.getAlbum("Görlitz");
        NodeList idNodes = doc.getElementsByTagName("id");
        NodeList nameNodes = doc.getElementsByTagName("name");
        NodeList passwordNodes = doc.getElementsByTagName("password");
        NodeList descriptionNodes = doc.getElementsByTagName("description");
        NodeList bilderNodes = doc.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("0", idNodes.item(0).getTextContent());
        Assert.assertEquals("Görlitz", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die Stadt Görlitz.", descriptionNodes.item(0).getTextContent());
        String password = "goerlitz";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("1", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("2", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("3", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("4", bilderNodes.item(3).getAttributes().item(0).getTextContent());

        doc = connector.getAlbum("Zittau");
        idNodes = doc.getElementsByTagName("id");
        nameNodes = doc.getElementsByTagName("name");
        passwordNodes = doc.getElementsByTagName("password");
        descriptionNodes = doc.getElementsByTagName("description");
        bilderNodes = doc.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("1", idNodes.item(0).getTextContent());
        Assert.assertEquals("Zittau", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die Stadt Zittau.", descriptionNodes.item(0).getTextContent());
        password = "zittau";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("5", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("6", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("8", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("10", bilderNodes.item(3).getAttributes().item(0).getTextContent());

        doc = connector.getAlbum("Oberlausitz");
        idNodes = doc.getElementsByTagName("id");
        nameNodes = doc.getElementsByTagName("name");
        passwordNodes = doc.getElementsByTagName("password");
        descriptionNodes = doc.getElementsByTagName("description");
        bilderNodes = doc.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("2", idNodes.item(0).getTextContent());
        Assert.assertEquals("Oberlausitz", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die schöne Oberlausitz.", descriptionNodes.item(0).getTextContent());
        password = "oberlausitz";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("7", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("9", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("11", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("12", bilderNodes.item(3).getAttributes().item(0).getTextContent());
    }

    @Test
    public void testGetAllAlben() throws Exception {
        org.w3c.dom.Document doc = connector.getAllAlben();
        NodeList albenNodes = doc.getElementsByTagName("album");
        Assert.assertEquals(3,albenNodes.getLength());
        Element albumNode = (Element) albenNodes.item(0);
        NodeList idNodes = albumNode.getElementsByTagName("id");
        NodeList nameNodes = albumNode.getElementsByTagName("name");
        NodeList passwordNodes = albumNode.getElementsByTagName("password");
        NodeList descriptionNodes = albumNode.getElementsByTagName("description");
        NodeList bilderNodes = albumNode.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("0", idNodes.item(0).getTextContent());
        Assert.assertEquals("Görlitz", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die Stadt Görlitz.", descriptionNodes.item(0).getTextContent());
        String password = "goerlitz";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("1", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("2", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("3", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("4", bilderNodes.item(3).getAttributes().item(0).getTextContent());

        albumNode = (Element) albenNodes.item(1);
        idNodes = albumNode.getElementsByTagName("id");
        nameNodes = albumNode.getElementsByTagName("name");
        passwordNodes = albumNode.getElementsByTagName("password");
        descriptionNodes = albumNode.getElementsByTagName("description");
        bilderNodes = albumNode.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("1", idNodes.item(0).getTextContent());
        Assert.assertEquals("Zittau", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die Stadt Zittau.", descriptionNodes.item(0).getTextContent());
        password = "zittau";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("5", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("6", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("8", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("10", bilderNodes.item(3).getAttributes().item(0).getTextContent());

        albumNode = (Element) albenNodes.item(2);
        idNodes = albumNode.getElementsByTagName("id");
        nameNodes = albumNode.getElementsByTagName("name");
        passwordNodes = albumNode.getElementsByTagName("password");
        descriptionNodes = albumNode.getElementsByTagName("description");
        bilderNodes = albumNode.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("2", idNodes.item(0).getTextContent());
        Assert.assertEquals("Oberlausitz", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die schöne Oberlausitz.", descriptionNodes.item(0).getTextContent());
        password = "oberlausitz";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("7", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("9", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("11", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("12", bilderNodes.item(3).getAttributes().item(0).getTextContent());
    }

    @Test
    public void testGetAlbenForNutzer() throws Exception {
        org.w3c.dom.Document nutzerDoc = new MockNutzerConnector().getNutzerByName("Karl");
        org.w3c.dom.Document doc = connector.getAlbenForNutzer(nutzerDoc);
        NodeList albenNodes = doc.getElementsByTagName("album");
        Assert.assertEquals(2,albenNodes.getLength());
        Element albumNode = (Element) albenNodes.item(0);
        NodeList idNodes = albumNode.getElementsByTagName("id");
        NodeList nameNodes = albumNode.getElementsByTagName("name");
        NodeList passwordNodes = albumNode.getElementsByTagName("password");
        NodeList descriptionNodes = albumNode.getElementsByTagName("description");
        NodeList bilderNodes = albumNode.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("0", idNodes.item(0).getTextContent());
        Assert.assertEquals("Görlitz", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die Stadt Görlitz.", descriptionNodes.item(0).getTextContent());
        String password = "goerlitz";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("1", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("2", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("3", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("4", bilderNodes.item(3).getAttributes().item(0).getTextContent());

        albumNode = (Element) albenNodes.item(1);
        idNodes = albumNode.getElementsByTagName("id");
        nameNodes = albumNode.getElementsByTagName("name");
        passwordNodes = albumNode.getElementsByTagName("password");
        descriptionNodes = albumNode.getElementsByTagName("description");
        bilderNodes = albumNode.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("1", idNodes.item(0).getTextContent());
        Assert.assertEquals("Zittau", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die Stadt Zittau.", descriptionNodes.item(0).getTextContent());
        password = "zittau";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("5", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("6", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("8", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("10", bilderNodes.item(3).getAttributes().item(0).getTextContent());

        nutzerDoc = new MockNutzerConnector().getNutzerByName("Günther");
        doc = connector.getAlbenForNutzer(nutzerDoc);
        albenNodes = doc.getElementsByTagName("album");
        Assert.assertEquals(1, albenNodes.getLength());
        albumNode = (Element) albenNodes.item(0);
        idNodes = albumNode.getElementsByTagName("id");
        nameNodes = albumNode.getElementsByTagName("name");
        passwordNodes = albumNode.getElementsByTagName("password");
        descriptionNodes = albumNode.getElementsByTagName("description");
        bilderNodes = albumNode.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("2", idNodes.item(0).getTextContent());
        Assert.assertEquals("Oberlausitz", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die schöne Oberlausitz.", descriptionNodes.item(0).getTextContent());
        password = "oberlausitz";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("7", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("9", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("11", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("12", bilderNodes.item(3).getAttributes().item(0).getTextContent());
    }

    @Test
    public void testGetAlbenWithNameContaining() throws Exception {
        org.w3c.dom.Document doc = connector.getAlbenWithNameContaining("tz");
        NodeList albenNodes = doc.getElementsByTagName("album");
        Assert.assertEquals(2,albenNodes.getLength());
        Element albumNode = (Element) albenNodes.item(1);
        NodeList idNodes = albumNode.getElementsByTagName("id");
        NodeList nameNodes = albumNode.getElementsByTagName("name");
        NodeList passwordNodes = albumNode.getElementsByTagName("password");
        NodeList descriptionNodes = albumNode.getElementsByTagName("description");
        NodeList bilderNodes = albumNode.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("0", idNodes.item(0).getTextContent());
        Assert.assertEquals("Görlitz", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die Stadt Görlitz.", descriptionNodes.item(0).getTextContent());
        String password = "goerlitz";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("1", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("2", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("3", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("4", bilderNodes.item(3).getAttributes().item(0).getTextContent());

        albumNode = (Element) albenNodes.item(0);
        idNodes = albumNode.getElementsByTagName("id");
        nameNodes = albumNode.getElementsByTagName("name");
        passwordNodes = albumNode.getElementsByTagName("password");
        descriptionNodes = albumNode.getElementsByTagName("description");
        bilderNodes = albumNode.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("2", idNodes.item(0).getTextContent());
        Assert.assertEquals("Oberlausitz", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die schöne Oberlausitz.", descriptionNodes.item(0).getTextContent());
        password = "oberlausitz";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("7", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("9", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("11", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("12", bilderNodes.item(3).getAttributes().item(0).getTextContent());

        connector = new MockAlbumConnector();
        doc = connector.getAlbenWithNameContaining("Ober");
        albenNodes = doc.getElementsByTagName("album");
        Assert.assertEquals(1, albenNodes.getLength()); 
        albumNode = (Element) albenNodes.item(0);
        idNodes = albumNode.getElementsByTagName("id");
        nameNodes = albumNode.getElementsByTagName("name");
        passwordNodes = albumNode.getElementsByTagName("password");
        descriptionNodes = albumNode.getElementsByTagName("description");
        bilderNodes = albumNode.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("2", idNodes.item(0).getTextContent());
        Assert.assertEquals("Oberlausitz", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die schöne Oberlausitz.", descriptionNodes.item(0).getTextContent());
        password = "oberlausitz";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("7", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("9", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("11", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("12", bilderNodes.item(3).getAttributes().item(0).getTextContent());
    }

    @Test
    public void testGetAlbenWithDescriptionContaining() throws Exception {
        org.w3c.dom.Document doc = connector.getAlbenWithDescriptionContaining("Bilder");
        NodeList albenNodes = doc.getElementsByTagName("album");
        Assert.assertEquals(3,albenNodes.getLength());
        Element albumNode = (Element) albenNodes.item(2);
        NodeList idNodes = albumNode.getElementsByTagName("id");
        NodeList nameNodes = albumNode.getElementsByTagName("name");
        NodeList passwordNodes = albumNode.getElementsByTagName("password");
        NodeList descriptionNodes = albumNode.getElementsByTagName("description");
        NodeList bilderNodes = albumNode.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("0", idNodes.item(0).getTextContent());
        Assert.assertEquals("Görlitz", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die Stadt Görlitz.", descriptionNodes.item(0).getTextContent());
        String password = "goerlitz";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("1", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("2", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("3", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("4", bilderNodes.item(3).getAttributes().item(0).getTextContent());

        albumNode = (Element) albenNodes.item(0);
        idNodes = albumNode.getElementsByTagName("id");
        nameNodes = albumNode.getElementsByTagName("name");
        passwordNodes = albumNode.getElementsByTagName("password");
        descriptionNodes = albumNode.getElementsByTagName("description");
        bilderNodes = albumNode.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("1", idNodes.item(0).getTextContent());
        Assert.assertEquals("Zittau", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die Stadt Zittau.", descriptionNodes.item(0).getTextContent());
        password = "zittau";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("5", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("6", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("8", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("10", bilderNodes.item(3).getAttributes().item(0).getTextContent());

        albumNode = (Element) albenNodes.item(1);
        idNodes = albumNode.getElementsByTagName("id");
        nameNodes = albumNode.getElementsByTagName("name");
        passwordNodes = albumNode.getElementsByTagName("password");
        descriptionNodes = albumNode.getElementsByTagName("description");
        bilderNodes = albumNode.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("2", idNodes.item(0).getTextContent());
        Assert.assertEquals("Oberlausitz", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die schöne Oberlausitz.", descriptionNodes.item(0).getTextContent());
        password = "oberlausitz";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("7", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("9", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("11", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("12", bilderNodes.item(3).getAttributes().item(0).getTextContent());

        connector = new MockAlbumConnector();
        doc = connector.getAlbenWithDescriptionContaining("Stadt");
        albenNodes = doc.getElementsByTagName("album");
        Assert.assertEquals(2, albenNodes.getLength());

        albumNode = (Element) albenNodes.item(1);
        idNodes = albumNode.getElementsByTagName("id");
        nameNodes = albumNode.getElementsByTagName("name");
        passwordNodes = albumNode.getElementsByTagName("password");
        descriptionNodes = albumNode.getElementsByTagName("description");
        bilderNodes = albumNode.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("0", idNodes.item(0).getTextContent());
        Assert.assertEquals("Görlitz", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die Stadt Görlitz.", descriptionNodes.item(0).getTextContent());
        password = "goerlitz";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("1", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("2", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("3", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("4", bilderNodes.item(3).getAttributes().item(0).getTextContent());

        albumNode = (Element) albenNodes.item(0);
        idNodes = albumNode.getElementsByTagName("id");
        nameNodes = albumNode.getElementsByTagName("name");
        passwordNodes = albumNode.getElementsByTagName("password");
        descriptionNodes = albumNode.getElementsByTagName("description");
        bilderNodes = albumNode.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("1", idNodes.item(0).getTextContent());
        Assert.assertEquals("Zittau", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die Stadt Zittau.", descriptionNodes.item(0).getTextContent());
        password = "zittau";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("5", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("6", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("8", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("10", bilderNodes.item(3).getAttributes().item(0).getTextContent());

        connector = new MockAlbumConnector();
        doc = connector.getAlbenWithDescriptionContaining("schön");
        albenNodes = doc.getElementsByTagName("album");
        Assert.assertEquals(1, albenNodes.getLength());

        albumNode = (Element) albenNodes.item(0);
        idNodes = albumNode.getElementsByTagName("id");
        nameNodes = albumNode.getElementsByTagName("name");
        passwordNodes = albumNode.getElementsByTagName("password");
        descriptionNodes = albumNode.getElementsByTagName("description");
        bilderNodes = albumNode.getElementsByTagName("bild");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,descriptionNodes.getLength());
        Assert.assertEquals(4,bilderNodes.getLength());
        Assert.assertEquals("2", idNodes.item(0).getTextContent());
        Assert.assertEquals("Oberlausitz", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Bilder über die schöne Oberlausitz.", descriptionNodes.item(0).getTextContent());
        password = "oberlausitz";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(password.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            password = hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(password, passwordNodes.item(0).getTextContent());
        Assert.assertEquals("7", bilderNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("9", bilderNodes.item(1).getAttributes().item(0).getTextContent());
        Assert.assertEquals("11", bilderNodes.item(2).getAttributes().item(0).getTextContent());
        Assert.assertEquals("12", bilderNodes.item(3).getAttributes().item(0).getTextContent());
    }
}
