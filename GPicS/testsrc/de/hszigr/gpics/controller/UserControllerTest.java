package de.hszigr.gpics.controller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.faces.context.FacesContext;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan Radusch
 * Date: 27.04.11
 * Time: 12:31
 * To change this template use File | Settings | File Templates.
 */
public class UserControllerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLogin() throws Exception {
        UserController controller = new UserController();
        controller.setNutzerNamen("Karl");
        controller.setPasswort("test");
        String back = controller.login();
        Assert.assertEquals("showOwnAlbum", back);
        Assert.assertNotSame("index", back);
        Assert.assertEquals(0, controller.getNutzerID());
        Assert.assertEquals("karl@web.de", controller.getEmail());
        Assert.assertEquals(true, controller.isEingeloggt());

        controller.setNutzerNamen("Günther");
        controller.setPasswort("password");
        String back2 = controller.login();
        Assert.assertEquals("showOwnAlbum", back2);
        Assert.assertNotSame("index", back2);
        Assert.assertEquals(1, controller.getNutzerID());
        Assert.assertEquals("guenther@web.de", controller.getEmail());
        Assert.assertEquals(true, controller.isEingeloggt());
    }

    @Test
    public void testLogout() throws Exception {
        UserController controller = new UserController();
        controller.setNutzerNamen("Karl");
        controller.setPasswort("test");
        controller.login();
        Assert.assertEquals(true, controller.isEingeloggt());

        controller.logout();
        Assert.assertEquals(false, controller.isEingeloggt());
        Assert.assertEquals(-1, controller.getNutzerID());
        Assert.assertEquals(null, controller.getNutzerNamen());
        Assert.assertEquals(null, controller.getEmail());
        Assert.assertEquals("d41d8cd98f0b24e980998ecf8427e", controller.getPasswort());
    }

    @Test
    public void testSendPasswortEmail() throws Exception {
//        UserController controller = new UserController();
//        controller.setNutzerNamen("Karl");
//        controller.login();
//        controller.sendPasswortEmail();
//        Assert.assertNotSame("98f6bcd4621d373cade4e832627b4f6", controller.getPasswort());
//        Assert.assertEquals(false, controller.isEingeloggt());
//        Assert.assertEquals(-1, controller.getNutzerID());
//        Assert.assertEquals(null, controller.getNutzerNamen());
//        Assert.assertEquals(null, controller.getEmail());
//        Assert.assertEquals("d41d8cd98f0b24e980998ecf8427e", controller.getPasswort());
    }

    @Test
    public void testErzeugeBenutzer() throws Exception {
        UserController controller = new UserController();
        controller.setNutzerNamen("Karl");
        controller.setPasswort("test");
        controller.setEmail("karl@web.de");
        controller.erzeugeBenutzer();

        Assert.assertEquals(true, controller.isEingeloggt());
        Assert.assertEquals("98f6bcd4621d373cade4e832627b4f6", controller.getPasswort());
    }

    @Test
    public void testSetPasswort() throws Exception {
        UserController controller = new UserController();
        controller.setPasswort("test");
        Assert.assertEquals("98f6bcd4621d373cade4e832627b4f6", controller.getPasswort());

        controller.setPasswort("password");
        Assert.assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", controller.getPasswort());

        controller.setPasswort("");
        Assert.assertEquals("d41d8cd98f0b24e980998ecf8427e", controller.getPasswort());
    }

    @Test
    public void testUpdateBenutzer() throws Exception{
        UserController controller = new UserController();
        controller.setNutzerNamen("Karl");
        controller.setPasswort("test");
        controller.login();

        controller.setEmail("sistradu@stud.hs-zigr.de");
        controller.updateBenutzer();

//        controller.logout();
//        controller.setNutzerNamen("Karl");
//        controller.setPasswort("test");
//        controller.login();
        Assert.assertNotSame("karl@web.de", controller.getEmail());
        Assert.assertEquals("sistradu@stud.hs-zigr.de", controller.getEmail());
    }
}
