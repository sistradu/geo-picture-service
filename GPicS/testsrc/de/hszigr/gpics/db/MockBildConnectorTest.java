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
 * Date: 04.05.11
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
public class MockBildConnectorTest {

    private MockBildConnector connector;

    @Before
    public void setUp() throws Exception {
        connector = new MockBildConnector();
    }

    @After
    public void tearDown() throws Exception {
        connector = null;
    }

    @Test
    public void testGetBildByID() throws Exception {
        try {
            connector.getBildByID(12);
            Assert.fail("Exception should have been thrown!");
        } catch (Exception ex) {
            //OK!
        }
        org.w3c.dom.Document doc = connector.getBildByID(0);
        NodeList idNodes = doc.getElementsByTagName("id");
        NodeList nameNodes = doc.getElementsByTagName("name");
        NodeList descriptionNodes = doc.getElementsByTagName("description");
        NodeList isPublicNodes = doc.getElementsByTagName("ispublic");
        NodeList timestampNodes = doc.getElementsByTagName("timestamp");
        NodeList filePositionNodes = doc.getElementsByTagName("fileposition");
        NodeList positionNodes = doc.getElementsByTagName("position");
        Assert.assertEquals(1, idNodes.getLength());
        Assert.assertEquals(1, nameNodes.getLength());
        Assert.assertEquals(1, descriptionNodes.getLength());
        Assert.assertEquals(1, isPublicNodes.getLength());
        Assert.assertEquals(1, timestampNodes.getLength());
        Assert.assertEquals(1, filePositionNodes.getLength());
        Assert.assertEquals(1, positionNodes.getLength());
        Assert.assertEquals("0", idNodes.item(0).getTextContent());
        Assert.assertEquals("Peterskirche", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Die Peterskirche", descriptionNodes.item(0).getTextContent());
        Assert.assertEquals("true", isPublicNodes.item(0).getTextContent());
        Assert.assertEquals("2011-02-21 19:00:57", timestampNodes.item(0).getTextContent());
        Assert.assertEquals("/pics/Peterskirche_1.jpg", filePositionNodes.item(0).getTextContent());
        Element position = (Element) positionNodes.item(0);
        NodeList latList = position.getElementsByTagName("latitude");
        NodeList longList = position.getElementsByTagName("longitude");
        NodeList latDecList = position.getElementsByTagName("latitudedecimal");
        NodeList longDecList = position.getElementsByTagName("longitudedecimal");
        NodeList altList = position.getElementsByTagName("altitude");
        NodeList dirList = position.getElementsByTagName("direction");
        Assert.assertEquals(1, latList.getLength());
        Assert.assertEquals(1, longList.getLength());
        Assert.assertEquals(1, latDecList.getLength());
        Assert.assertEquals(1, longDecList.getLength());
        Assert.assertEquals(1, altList.getLength());
        Assert.assertEquals(1, dirList.getLength());
        Assert.assertEquals("51\"8'55.8", latList.item(0).getTextContent());
        Assert.assertEquals("51.148833", latDecList.item(0).getTextContent());
        Assert.assertEquals("51\"8'55.8", longList.item(0).getTextContent());
        Assert.assertEquals("51.148833", longDecList.item(0).getTextContent());
        Assert.assertEquals("123", altList.item(0).getTextContent());
        Assert.assertEquals("120", dirList.item(0).getTextContent());

        doc = connector.getBildByID(4);
        idNodes = doc.getElementsByTagName("id");
        nameNodes = doc.getElementsByTagName("name");
        descriptionNodes = doc.getElementsByTagName("description");
        isPublicNodes = doc.getElementsByTagName("ispublic");
        timestampNodes = doc.getElementsByTagName("timestamp");
        filePositionNodes = doc.getElementsByTagName("fileposition");
        positionNodes = doc.getElementsByTagName("position");
        Assert.assertEquals(1, idNodes.getLength());
        Assert.assertEquals(1, nameNodes.getLength());
        Assert.assertEquals(1, descriptionNodes.getLength());
        Assert.assertEquals(1, isPublicNodes.getLength());
        Assert.assertEquals(1, timestampNodes.getLength());
        Assert.assertEquals(1, filePositionNodes.getLength());
        Assert.assertEquals(1, positionNodes.getLength());
        Assert.assertEquals("4", idNodes.item(0).getTextContent());
        Assert.assertEquals("Blumenuhr", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Die Blumenuhr", descriptionNodes.item(0).getTextContent());
        Assert.assertEquals("true", isPublicNodes.item(0).getTextContent());
        Assert.assertEquals("2011-03-21 19:00:57", timestampNodes.item(0).getTextContent());
        Assert.assertEquals("/pics/Blumenuhr_5.jpg", filePositionNodes.item(0).getTextContent());
        position = (Element) positionNodes.item(0);
        latList = position.getElementsByTagName("latitude");
        longList = position.getElementsByTagName("longitude");
        latDecList = position.getElementsByTagName("latitudedecimal");
        longDecList = position.getElementsByTagName("longitudedecimal");
        altList = position.getElementsByTagName("altitude");
        dirList = position.getElementsByTagName("direction");
        Assert.assertEquals(1, latList.getLength());
        Assert.assertEquals(1, longList.getLength());
        Assert.assertEquals(1, latDecList.getLength());
        Assert.assertEquals(1, longDecList.getLength());
        Assert.assertEquals(1, altList.getLength());
        Assert.assertEquals(1, dirList.getLength());
        Assert.assertEquals("51\"8'55.8", latList.item(0).getTextContent());
        Assert.assertEquals("51.148833", latDecList.item(0).getTextContent());
        Assert.assertEquals("51\"8'55.8", longList.item(0).getTextContent());
        Assert.assertEquals("51.148833", longDecList.item(0).getTextContent());
        Assert.assertEquals("123", altList.item(0).getTextContent());
        Assert.assertEquals("120", dirList.item(0).getTextContent());

        doc = connector.getBildByID(11);
        idNodes = doc.getElementsByTagName("id");
        nameNodes = doc.getElementsByTagName("name");
        descriptionNodes = doc.getElementsByTagName("description");
        isPublicNodes = doc.getElementsByTagName("ispublic");
        timestampNodes = doc.getElementsByTagName("timestamp");
        filePositionNodes = doc.getElementsByTagName("fileposition");
        positionNodes = doc.getElementsByTagName("position");
        Assert.assertEquals(1, idNodes.getLength());
        Assert.assertEquals(1, nameNodes.getLength());
        Assert.assertEquals(1, descriptionNodes.getLength());
        Assert.assertEquals(1, isPublicNodes.getLength());
        Assert.assertEquals(1, timestampNodes.getLength());
        Assert.assertEquals(1, filePositionNodes.getLength());
        Assert.assertEquals(1, positionNodes.getLength());
        Assert.assertEquals("11", idNodes.item(0).getTextContent());
        Assert.assertEquals("Lausche", nameNodes.item(0).getTextContent());
        Assert.assertEquals("Blick vom Nonnenfelsen", descriptionNodes.item(0).getTextContent());
        Assert.assertEquals("true", isPublicNodes.item(0).getTextContent());
        Assert.assertEquals("2011-03-19 13:36:02", timestampNodes.item(0).getTextContent());
        Assert.assertEquals("/pics/Lausche_12.jpg", filePositionNodes.item(0).getTextContent());
        position = (Element) positionNodes.item(0);
        latList = position.getElementsByTagName("latitude");
        longList = position.getElementsByTagName("longitude");
        latDecList = position.getElementsByTagName("latitudedecimal");
        longDecList = position.getElementsByTagName("longitudedecimal");
        altList = position.getElementsByTagName("altitude");
        dirList = position.getElementsByTagName("direction");
        Assert.assertEquals(1, latList.getLength());
        Assert.assertEquals(1, longList.getLength());
        Assert.assertEquals(1, latDecList.getLength());
        Assert.assertEquals(1, longDecList.getLength());
        Assert.assertEquals(1, altList.getLength());
        Assert.assertEquals(1, dirList.getLength());
        Assert.assertEquals("51\"8'55.8", latList.item(0).getTextContent());
        Assert.assertEquals("51.148833", latDecList.item(0).getTextContent());
        Assert.assertEquals("51\"8'55.8", longList.item(0).getTextContent());
        Assert.assertEquals("51.148833", longDecList.item(0).getTextContent());
        Assert.assertEquals("123", altList.item(0).getTextContent());
        Assert.assertEquals("120", dirList.item(0).getTextContent());
    }

    @Test
    public void testGetBilderByName() throws Exception {
        org.w3c.dom.Document doc = connector.getBilderByName("Vogtshof");
        NodeList bilderNodes = doc.getElementsByTagName("bild");
        Assert.assertEquals(3, bilderNodes.getLength());
        for (int i = 0; i < bilderNodes.getLength(); i++) {
            Element bildNode = (Element) bilderNodes.item(i);
            NodeList idNodes = bildNode.getElementsByTagName("id");
            NodeList nameNodes = bildNode.getElementsByTagName("name");
            NodeList descriptionNodes = bildNode.getElementsByTagName("description");
            NodeList isPublicNodes = bildNode.getElementsByTagName("ispublic");
            NodeList timestampNodes = bildNode.getElementsByTagName("timestamp");
            NodeList filePositionNodes = bildNode.getElementsByTagName("fileposition");
            NodeList positionNodes = bildNode.getElementsByTagName("position");
            Assert.assertEquals(1, idNodes.getLength());
            Assert.assertEquals(1, nameNodes.getLength());
            Assert.assertEquals(1, descriptionNodes.getLength());
            Assert.assertEquals(1, isPublicNodes.getLength());
            Assert.assertEquals(1, timestampNodes.getLength());
            Assert.assertEquals(1, filePositionNodes.getLength());
            Assert.assertEquals(1, positionNodes.getLength());
            Assert.assertTrue(idNodes.item(0).getTextContent().equals("1") || idNodes.item(0).getTextContent().equals("2") || idNodes.item(0).getTextContent().equals("3"));
            Assert.assertTrue(nameNodes.item(0).getTextContent().equals("Vogtshof") || nameNodes.item(0).getTextContent().equals("Vogtshof Innenhof") || nameNodes.item(0).getTextContent().equals("Wohnung Vogtshof"));
            Assert.assertTrue(descriptionNodes.item(0).getTextContent().equals("Der Vogtshof") || descriptionNodes.item(0).getTextContent().equals("Studenten beim Grillen") || descriptionNodes.item(0).getTextContent().equals("Klein aber fein..."));
            Assert.assertTrue(isPublicNodes.item(0).getTextContent().equals("true") || isPublicNodes.item(0).getTextContent().equals("false"));
            Assert.assertTrue(timestampNodes.item(0).getTextContent().equals("2011-02-21 19:01:22") || timestampNodes.item(0).getTextContent().equals("2011-02-21 19:05:01") || timestampNodes.item(0).getTextContent().equals("2011-02-21 21:37:01"));
            Assert.assertTrue(filePositionNodes.item(0).getTextContent().equals("/pics/Vogtshof_2.jpg") || filePositionNodes.item(0).getTextContent().equals("/pics/Vogtshof_Innenhof_3.jpg") || filePositionNodes.item(0).getTextContent().equals("/pics/Wohnung_Vogtshof_4.jpg"));
            Element position = (Element) positionNodes.item(0);
            NodeList latList = position.getElementsByTagName("latitude");
            NodeList longList = position.getElementsByTagName("longitude");
            NodeList latDecList = position.getElementsByTagName("latitudedecimal");
            NodeList longDecList = position.getElementsByTagName("longitudedecimal");
            NodeList altList = position.getElementsByTagName("altitude");
            NodeList dirList = position.getElementsByTagName("direction");
            Assert.assertEquals(1, latList.getLength());
            Assert.assertEquals(1, longList.getLength());
            Assert.assertEquals(1, latDecList.getLength());
            Assert.assertEquals(1, longDecList.getLength());
            Assert.assertEquals(1, altList.getLength());
            Assert.assertEquals(1, dirList.getLength());
            Assert.assertEquals("51\"8'55.8", latList.item(0).getTextContent());
            Assert.assertEquals("51.148833", latDecList.item(0).getTextContent());
            Assert.assertEquals("51\"8'55.8", longList.item(0).getTextContent());
            Assert.assertEquals("51.148833", longDecList.item(0).getTextContent());
            Assert.assertEquals("123", altList.item(0).getTextContent());
            Assert.assertEquals("120", dirList.item(0).getTextContent());
        }
    }

    @Test
    public void testGetBilderForAlbum() throws Exception {
        org.w3c.dom.Document albenDoc = new MockAlbumConnector().getAlbum("Zittau");
        org.w3c.dom.Document doc = connector.getBilderForAlbum(albenDoc);
        NodeList bilderNodes = doc.getElementsByTagName("bild");
        Assert.assertEquals(4, bilderNodes.getLength());
        for (int i = 0; i < bilderNodes.getLength(); i++) {
            Element bildNode = (Element) bilderNodes.item(i);
            NodeList idNodes = bildNode.getElementsByTagName("id");
            NodeList nameNodes = bildNode.getElementsByTagName("name");
            NodeList descriptionNodes = bildNode.getElementsByTagName("description");
            NodeList isPublicNodes = bildNode.getElementsByTagName("ispublic");
            NodeList timestampNodes = bildNode.getElementsByTagName("timestamp");
            NodeList filePositionNodes = bildNode.getElementsByTagName("fileposition");
            NodeList positionNodes = bildNode.getElementsByTagName("position");
            Assert.assertEquals(1, idNodes.getLength());
            Assert.assertEquals(1, nameNodes.getLength());
            Assert.assertEquals(1, descriptionNodes.getLength());
            Assert.assertEquals(1, isPublicNodes.getLength());
            Assert.assertEquals(1, timestampNodes.getLength());
            Assert.assertEquals(1, filePositionNodes.getLength());
            Assert.assertEquals(1, positionNodes.getLength());
            Assert.assertTrue(idNodes.item(0).getTextContent().equals("4") || idNodes.item(0).getTextContent().equals("5") || idNodes.item(0).getTextContent().equals("7") || idNodes.item(0).getTextContent().equals("9"));
            Assert.assertTrue(nameNodes.item(0).getTextContent().equals("Blumenuhr") || nameNodes.item(0).getTextContent().equals("Bahnhof") || nameNodes.item(0).getTextContent().equals("Die Hochschule") || nameNodes.item(0).getTextContent().equals("Die Mensa"));
            Assert.assertTrue(descriptionNodes.item(0).getTextContent().equals("Die Blumenuhr") || descriptionNodes.item(0).getTextContent().equals("Der Bahnhof") || descriptionNodes.item(0).getTextContent().equals("Eines von 9 Gebäuden...") || descriptionNodes.item(0).getTextContent().equals("Leider nicht mehr offen..."));
            Assert.assertTrue(isPublicNodes.item(0).getTextContent().equals("true"));
            Assert.assertTrue(timestampNodes.item(0).getTextContent().equals("2011-03-21 19:00:57") || timestampNodes.item(0).getTextContent().equals("2011-03-21 19:01:22") || timestampNodes.item(0).getTextContent().equals("2011-03-21 21:37:01") || timestampNodes.item(0).getTextContent().equals("2011-03-21 21:41:22"));
            Assert.assertTrue(filePositionNodes.item(0).getTextContent().equals("/pics/Blumenuhr_5.jpg") || filePositionNodes.item(0).getTextContent().equals("/pics/Bahnhof_6.jpg") || filePositionNodes.item(0).getTextContent().equals("/pics/Die_Hochschule_8.jpg") || filePositionNodes.item(0).getTextContent().equals("/pics/Die_Mensa_10.jpg"));
            Element position = (Element) positionNodes.item(0);
            NodeList latList = position.getElementsByTagName("latitude");
            NodeList longList = position.getElementsByTagName("longitude");
            NodeList latDecList = position.getElementsByTagName("latitudedecimal");
            NodeList longDecList = position.getElementsByTagName("longitudedecimal");
            NodeList altList = position.getElementsByTagName("altitude");
            NodeList dirList = position.getElementsByTagName("direction");
            Assert.assertEquals(1, latList.getLength());
            Assert.assertEquals(1, longList.getLength());
            Assert.assertEquals(1, latDecList.getLength());
            Assert.assertEquals(1, longDecList.getLength());
            Assert.assertEquals(1, altList.getLength());
            Assert.assertEquals(1, dirList.getLength());
            Assert.assertEquals("51\"8'55.8", latList.item(0).getTextContent());
            Assert.assertEquals("51.148833", latDecList.item(0).getTextContent());
            Assert.assertEquals("51\"8'55.8", longList.item(0).getTextContent());
            Assert.assertEquals("51.148833", longDecList.item(0).getTextContent());
            Assert.assertEquals("123", altList.item(0).getTextContent());
            Assert.assertEquals("120", dirList.item(0).getTextContent());
        }
    }
}
