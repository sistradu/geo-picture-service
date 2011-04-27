package de.hszigr.gpics.controller;

import de.hszigr.gpics.db.MockNutzerConnector;
import de.hszigr.gpics.db.interfaces.INutzerConnector;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.ConnectException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan Radusch
 * Date: 26.04.11
 * Time: 17:47
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
@SessionScoped
public class UserController {

    private int nutzerID;
    private String nutzerNamen;
    private String email;
    //TODO emailPasswort entfernen
    private String emailPasswort;
    private boolean eingeloggt = false;
    private String passwort;
    private INutzerConnector conn;

    public UserController(){
        conn = new MockNutzerConnector();
    }

    public String login() {
        String dbPasswort = "";
        Document doc = null;
        try {
            doc = conn.getNutzerByName(nutzerNamen);
            NodeList nodes = doc.getElementsByTagName("password");
            dbPasswort = nodes.item(0).getTextContent();
        } catch (ConnectException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(dbPasswort.equals(passwort)){
            eingeloggt = true;
            setNutzerID(Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent()));
            setEmail(doc.getElementsByTagName("email").item(0).getTextContent());
            return "showOwnAlbum";
        }
        return "index";
    }

    public String logout() {
        setNutzerID(-1);
        setNutzerNamen(null);
        setPasswort("");
        setEmail(null);
        setEingeloggt(false);
        return "index";
    }

    //TODO anderen Mail-Client nehmen
    public void sendPasswortEmail() {
        String tempPasswort = erzeugeZufallsPasswort();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.hs-zigr.de");
        props.put("mail.smtp.port", 25);
        props.setProperty("mail.smtp.ssl.trust", "smtp.hs-zigr.de");
        Authenticator aut = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sistradu", emailPasswort);    //To change body of overridden methods use File | Settings | File Templates.
            }
        };
        Session session = Session.getInstance(props, aut);
        try {
            Message mail = new MimeMessage(session);
            mail.setFrom(new InternetAddress("sistradu@stud.hs-zigr.de"));
            mail.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("sistradu@stud.hs-zigr.de"));
            mail.setSubject("Neues Passwort");
            mail.setText("Ihr neues Passwort lautet: " +  tempPasswort +
                    "\n\n Bitte ändern Sie es bei der nächsten Anwendung!");
            Transport.send(mail);
            System.out.println("Done");
        } catch (MessagingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        setPasswort(tempPasswort);
        try {
            conn.updateNutzer(nutzerID, nutzerNamen, passwort, email);
        } catch (ConnectException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public String erzeugeBenutzer() {
        try {
            conn.createNutzer(nutzerNamen, passwort, email);
            Document doc = conn.getNutzerByName(nutzerNamen);
            setNutzerID(Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent()));
            setEingeloggt(true);
        } catch (ConnectException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return "showOwnAlbum";
    }

    private String erzeugeZufallsPasswort() {
        String allowedChars = "0123456789abcdefghijklmnopqrstuvwxyz";
        Random rand = new Random();
        int max = allowedChars.length();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            int value = rand.nextInt(max);
            buffer.append(allowedChars.charAt(value));
        }
        return buffer.toString();
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] digest = md.digest(passwort.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            this.passwort = hexString.toString();
            //TODO entfernen
            this.emailPasswort = passwort;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public int getNutzerID() {
        return nutzerID;
    }

    public void setNutzerID(int nutzerID) {
        this.nutzerID = nutzerID;
    }

    public String getNutzerNamen() {
        return nutzerNamen;
    }

    public void setNutzerNamen(String nutzerNamen) {
        this.nutzerNamen = nutzerNamen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEingeloggt() {
        return eingeloggt;
    }

    public void setEingeloggt(boolean eingeloggt) {
        this.eingeloggt = eingeloggt;
    }
}
