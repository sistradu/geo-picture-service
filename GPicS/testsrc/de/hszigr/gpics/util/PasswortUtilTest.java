package de.hszigr.gpics.util;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan Radusch
 * Date: 03.05.11
 * Time: 17:25
 * To change this template use File | Settings | File Templates.
 */
public class PasswortUtilTest {
    @Test
    public void testErzeugeZufallsPasswort() throws Exception {
        PasswortUtil util = new PasswortUtil();
        String passwort1 = util.erzeugeZufallsPasswort(5);
        String passwort2 = util.erzeugeZufallsPasswort(5);

        Assert.assertNotSame(passwort1,passwort2);

        passwort1 = util.erzeugeZufallsPasswort(6);

        Assert.assertNotSame(passwort1.length(), passwort2.length());

        passwort1 = util.erzeugeZufallsPasswort(0);

        Assert.assertEquals("", passwort1);
    }

    @Test
    public void testEncryptWithMD5() throws Exception {
        PasswortUtil util = new PasswortUtil();
        String md5 = util.encryptWithMD5("password");

        Assert.assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", md5);

        md5 = util.encryptWithMD5("Stefa");

        Assert.assertNotSame("5f4dcc3b5aa765d61d8327deb882cf99", md5);

        Assert.assertEquals(32, md5.length());
    }
}
