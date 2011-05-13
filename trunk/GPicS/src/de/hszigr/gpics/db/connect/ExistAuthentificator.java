package de.hszigr.gpics.db.connect;

import java.net.Authenticator;
import java.net.InetAddress;
import java.net.PasswordAuthentication;

/**
 * Created by IntelliJ IDEA.
 * User: Markus Ullrich
 * Date: 18.04.11
 * Time: 15:27
 * To change this template use File | Settings | File Templates.
 */
public class ExistAuthentificator extends Authenticator {

    protected PasswordAuthentication getPasswordAuthentication(){
        String promptString = getRequestingPrompt();
        String hostname = getRequestingHost();
        InetAddress ipaddr = getRequestingSite();
        int port = getRequestingPort();

        String username = "admin";

        String password = "admin";

        return new PasswordAuthentication(username, password.toCharArray());
    }

}
