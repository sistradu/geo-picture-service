package de.hszigr.gpics.controller;

import de.hszigr.gpics.db.MockNutzerConnector;
import de.hszigr.gpics.db.interfaces.INutzerConnector;
import de.hszigr.gpics.util.FacesMessageHandler;
import de.hszigr.gpics.util.MessagePropertiesBean;
import de.hszigr.gpics.util.PasswortUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

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
    private boolean eingeloggt = false;
    private String passwort;
    private INutzerConnector conn;

    public UserController() {
        conn = new MockNutzerConnector();
    }

    public String login() {
        try {
            String dbPasswort = "";
            Document doc = conn.getNutzerByName(nutzerNamen);
            NodeList nodes = doc.getElementsByTagName("password");
            dbPasswort = nodes.item(0).getTextContent();

            if (dbPasswort.equals(passwort)) {
                eingeloggt = true;
                getNutzerIDAndEmail(doc);
                return "showOwnAlbum";
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesMessageHandler.createFacesMessageForID("loginMask", e.getMessage());
        }
        MessagePropertiesBean msgPB = new MessagePropertiesBean();
        FacesMessageHandler.createFacesMessageForID("loginMask", msgPB.getPropertiesMessage("wrongPassword"));
        return "index";
    }


    public String logout() {
        resetAll();
        return "index";
    }

    public String sendPasswortEmail() {
        try {
            String tempPasswort = new PasswortUtil().erzeugeZufallsPasswort(16);

            Document doc = conn.getNutzerByName(nutzerNamen);
            getNutzerIDAndEmail(doc);

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "193.174.103.76");
            props.put("mail.smtp.port", 25);
            props.setProperty("mail.smtp.ssl.trust", "193.174.103.76");
            Authenticator aut = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("mailer", "mailer");    //To change body of overridden methods use File | Settings | File Templates.
                }
            };
            Session session = Session.getInstance(props, aut);
            MessagePropertiesBean msgPB = new MessagePropertiesBean();
            Message mail = new MimeMessage(session);
            mail.setFrom(new InternetAddress("mailer@gpics.de"));
//            mail.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse("sistradu@stud.hs-zigr.de"));
            mail.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            mail.setSubject(msgPB.getPropertiesMessage("mailSubject"));
            mail.setText(msgPB.getPropertiesMessage("mailPart1") + tempPasswort +
                    msgPB.getPropertiesMessage("mailPart2"));
            Transport.send(mail);
            System.out.println("Done");
            setPasswort(tempPasswort);
            conn.updateNutzer(nutzerID, nutzerNamen, passwort, email);
            resetAll();
        } catch (Exception e){
            e.printStackTrace();
            FacesMessageHandler.createFacesMessageForID("sendPWMask", e.getMessage());
            return "sendPW";
        }
        MessagePropertiesBean mPB = new MessagePropertiesBean();
        FacesMessageHandler.createFacesMessageForID("infoMessages", mPB.getPropertiesMessage("sendMailSuccess"));
        return "index";
    }

    public String erzeugeBenutzer() {
        try {
            conn.createNutzer(nutzerNamen, passwort, email);
            Document doc = conn.getNutzerByName(nutzerNamen);
            setNutzerID(Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent()));
            setEingeloggt(true);
        }catch (Exception e){
            e.printStackTrace();
            FacesMessageHandler.createFacesMessageForID("createUserMask", e.getMessage());
            return "createUser";
        }
        return "showOwnAlbum";
    }

    public String updateBenutzer() {
        try {
            conn.updateNutzer(nutzerID, nutzerNamen, passwort, email);
        }catch (Exception e){
            e.printStackTrace();
            FacesMessageHandler.createFacesMessageForID("createUserMask", e.getMessage());
            return "createUser";
        }
        return "showOwnAlbum";
    }

    private void resetAll() {
        setNutzerID(-1);
        setNutzerNamen(null);
        setPasswort("");
        setEmail(null);
        setEingeloggt(false);
    }

    private void getNutzerIDAndEmail(Document doc) {
        setNutzerID(Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent()));
        setEmail(doc.getElementsByTagName("email").item(0).getTextContent());
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
            this.passwort = new PasswortUtil().encryptWithMD5(passwort);
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
