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
 * Date: 27.04.11
 * Time: 09:21
 * To change this template use File | Settings | File Templates.
 */
public class MockNutzerConnectorTest {

    private MockNutzerConnector connector;

    @Before
    public void setUp() throws Exception {
        connector = new MockNutzerConnector();
    }

    @After
    public void tearDown() throws Exception {
        connector = null;
    }

    @Test
    public void testGetNutzerByID() throws Exception {
        org.w3c.dom.Document doc = connector.getNutzerByID(0);
        NodeList idNodes = doc.getElementsByTagName("id");
        NodeList nameNodes = doc.getElementsByTagName("name");
        NodeList passwordNodes = doc.getElementsByTagName("password");
        NodeList emailNodes = doc.getElementsByTagName("email");
        NodeList albumNodes = doc.getElementsByTagName("album");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,emailNodes.getLength());
        Assert.assertEquals(2,albumNodes.getLength());
        Assert.assertEquals("0", idNodes.item(0).getTextContent());
        Assert.assertEquals("Karl", nameNodes.item(0).getTextContent());
        Assert.assertEquals("karl@web.de", emailNodes.item(0).getTextContent());
        String password = "test";
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
        Assert.assertEquals("Görlitz", albumNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("Zittau", albumNodes.item(1).getAttributes().item(0).getTextContent());

        doc = connector.getNutzerByID(1);
        idNodes = doc.getElementsByTagName("id");
        nameNodes = doc.getElementsByTagName("name");
        passwordNodes = doc.getElementsByTagName("password");
        emailNodes = doc.getElementsByTagName("email");
        albumNodes = doc.getElementsByTagName("album");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,emailNodes.getLength());
        Assert.assertEquals(1,albumNodes.getLength());
        Assert.assertEquals("1", idNodes.item(0).getTextContent());
        Assert.assertEquals("Günther", nameNodes.item(0).getTextContent());
        Assert.assertEquals("guenther@web.de", emailNodes.item(0).getTextContent());
        password = "password";
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
        Assert.assertEquals("Oberlausitz", albumNodes.item(0).getAttributes().item(0).getTextContent());
    }

    @Test
    public void testGetNutzerByName() throws Exception {
        org.w3c.dom.Document doc = connector.getNutzerByName("Karl");
        NodeList idNodes = doc.getElementsByTagName("id");
        NodeList nameNodes = doc.getElementsByTagName("name");
        NodeList passwordNodes = doc.getElementsByTagName("password");
        NodeList emailNodes = doc.getElementsByTagName("email");
        NodeList albumNodes = doc.getElementsByTagName("album");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,emailNodes.getLength());
        Assert.assertEquals(2,albumNodes.getLength());
        Assert.assertEquals("0", idNodes.item(0).getTextContent());
        Assert.assertEquals("Karl", nameNodes.item(0).getTextContent());
        Assert.assertEquals("karl@web.de", emailNodes.item(0).getTextContent());
        String password = "test";
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
        Assert.assertEquals("Görlitz", albumNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("Zittau", albumNodes.item(1).getAttributes().item(0).getTextContent());

        doc = connector.getNutzerByName("Günther");
        idNodes = doc.getElementsByTagName("id");
        nameNodes = doc.getElementsByTagName("name");
        passwordNodes = doc.getElementsByTagName("password");
        emailNodes = doc.getElementsByTagName("email");
        albumNodes = doc.getElementsByTagName("album");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,emailNodes.getLength());
        Assert.assertEquals(1,albumNodes.getLength());
        Assert.assertEquals("1", idNodes.item(0).getTextContent());
        Assert.assertEquals("Günther", nameNodes.item(0).getTextContent());
        Assert.assertEquals("guenther@web.de", emailNodes.item(0).getTextContent());
        password = "password";
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
        Assert.assertEquals("Oberlausitz", albumNodes.item(0).getAttributes().item(0).getTextContent());
    }

    @Test
    public void testGetAllNutzer(){
        org.w3c.dom.Document doc = connector.getAllNutzer();
        NodeList nutzerNodes = doc.getElementsByTagName("benutzer");
        Assert.assertEquals(2,nutzerNodes.getLength());
        Element nutzerNode = (Element) nutzerNodes.item(0);
        NodeList idNodes = nutzerNode.getElementsByTagName("id");
        NodeList nameNodes = nutzerNode.getElementsByTagName("name");
        NodeList passwordNodes = nutzerNode.getElementsByTagName("password");
        NodeList emailNodes = nutzerNode.getElementsByTagName("email");
        NodeList albumNodes = nutzerNode.getElementsByTagName("album");
        Assert.assertEquals(1, idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,emailNodes.getLength());
        Assert.assertEquals(2,albumNodes.getLength());
        Assert.assertEquals("0", idNodes.item(0).getTextContent());
        Assert.assertEquals("Karl", nameNodes.item(0).getTextContent());
        Assert.assertEquals("karl@web.de", emailNodes.item(0).getTextContent());
        String password = "test";
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
        Assert.assertEquals("Görlitz", albumNodes.item(0).getAttributes().item(0).getTextContent());
        Assert.assertEquals("Zittau", albumNodes.item(1).getAttributes().item(0).getTextContent());

        nutzerNode = (Element) nutzerNodes.item(1);
        idNodes = nutzerNode.getElementsByTagName("id");
        nameNodes = nutzerNode.getElementsByTagName("name");
        passwordNodes = nutzerNode.getElementsByTagName("password");
        emailNodes = nutzerNode.getElementsByTagName("email");
        albumNodes = nutzerNode.getElementsByTagName("album");
        Assert.assertEquals(1,idNodes.getLength());
        Assert.assertEquals(1,nameNodes.getLength());
        Assert.assertEquals(1,passwordNodes.getLength());
        Assert.assertEquals(1,emailNodes.getLength());
        Assert.assertEquals(1,albumNodes.getLength());
        Assert.assertEquals("1", idNodes.item(0).getTextContent());
        Assert.assertEquals("Günther", nameNodes.item(0).getTextContent());
        Assert.assertEquals("guenther@web.de", emailNodes.item(0).getTextContent());
        password = "password";
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
        Assert.assertEquals("Oberlausitz", albumNodes.item(0).getAttributes().item(0).getTextContent());
    }
}
