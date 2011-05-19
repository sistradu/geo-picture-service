package de.hszigr.gpics.controller;

import de.hszigr.gpics.db.connect.NutzerConnector;
import de.hszigr.gpics.db.interfaces.INutzerConnector;
import de.hszigr.gpics.util.GPicSUtil;
import de.hszigr.gpics.util.MessagePropertiesBean;
import de.hszigr.gpics.util.PasswortUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.ConnectException;
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
    private String passwortRepeat;
    private INutzerConnector conn;

    public UserController() {
        conn = new NutzerConnector();
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
        }catch(NullPointerException e){
            System.err.println("null");
            MessagePropertiesBean msgPB = new MessagePropertiesBean();
            GPicSUtil.createFacesMessageForID("loginMask", msgPB.getPropertiesMessage("noUserAvailable"), true);
        } catch (Exception e) {
            e.printStackTrace();
            GPicSUtil.createFacesMessageForID("loginMask", e.getMessage(), true);
        }
        MessagePropertiesBean msgPB = new MessagePropertiesBean();
        GPicSUtil.createFacesMessageForID("loginMask", msgPB.getPropertiesMessage("wrongPassword"), true);
        return "index";
    }


    public String logout() {
        resetAll();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
            GPicSUtil.createFacesMessageForID("loginMask", e.getMessage(), true);
        } catch(NullPointerException e){
            System.err.println("null");
        }
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
                    return new PasswordAuthentication("mailer", "mailer");
                }
            };
            Session session = Session.getInstance(props, aut);
            MessagePropertiesBean msgPB = new MessagePropertiesBean();
            Message mail = new MimeMessage(session);
            mail.setFrom(new InternetAddress("mailer@ux-i-xml11a.inf.hs-zigr.de"));
            mail.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            mail.setSubject(msgPB.getPropertiesMessage("mailSubject"));
            mail.setText(msgPB.getPropertiesMessage("mailPart1") + tempPasswort +
                    msgPB.getPropertiesMessage("mailPart2"));
            Transport.send(mail);
            System.out.println("Done");
            setPasswort(tempPasswort);
            conn.updateNutzer(nutzerID, nutzerNamen, passwort, email);
//            resetAll();
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());  //Fängt NullPointerabException ab, die bei Unit-Tests auftritt, weil dort der FacesContext null ist.
        } catch (Exception e) {
            e.printStackTrace();
            GPicSUtil.createFacesMessageForID("sendPWMask", e.getMessage(), true);
            return "sendPW";
        }
        try {
            MessagePropertiesBean mPB = new MessagePropertiesBean();
            GPicSUtil.createFacesMessageForID("infoMessages", mPB.getPropertiesMessage("sendMailSuccess"), false);
        } catch (NullPointerException e) {
            System.err.println(e.getMessage()); //Fängt NullPointerabException ab, die bei Unit-Tests auftritt, weil dort der FacesContext null ist.
        }
        return "index";
    }

    public String erzeugeBenutzer() {
        try {
            this.passwort="password";
            conn.createNutzer(nutzerNamen, passwort, email);
            Document doc = conn.getNutzerByName(nutzerNamen);
            setNutzerID(Integer.parseInt(doc.getElementsByTagName("id").item(0).getTextContent()));
            setEingeloggt(true);
            sendPasswortEmail();
        } catch (Exception e) {
            e.printStackTrace();
            GPicSUtil.createFacesMessageForID("createUserMask", e.getMessage(), true);
            return "createUser";
        }
        return "showOwnAlbum";
    }

    public String updateBenutzer() {
        try {
            if (!passwort.equals(passwortRepeat)) {
                MessagePropertiesBean msgPB = new MessagePropertiesBean();
                GPicSUtil.createFacesMessageForID("createUserMask:createUserRepeatPasswort", msgPB.getPropertiesMessage("passwordDontEqual"), true);
                return null;
            }
            try {
                conn.updateNutzer(nutzerID, nutzerNamen, passwort, email);
            } catch (Exception e) {
                e.printStackTrace();
                GPicSUtil.createFacesMessageForID("createUserMask", e.getMessage(), true);
                resetNutzerInformation(nutzerID);
                return "createUser";
            }
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());  //Fängt NullPointerabException ab, die bei Unit-Tests auftritt, weil dort der FacesContext null ist.
        }
        return "showOwnAlbum";
    }

    private void resetNutzerInformation(int nutzerID) {
        try {
            Document doc = conn.getNutzerByID(nutzerID);
            setNutzerNamen(doc.getElementsByTagName("name").item(0).getTextContent());
            setEmail(doc.getElementsByTagName("email").item(0).getTextContent());
            setPasswort(doc.getElementsByTagName("password").item(0).getTextContent());
        } catch (ConnectException e) {
            e.printStackTrace();
            GPicSUtil.createFacesMessageForID("createUserMask", e.getMessage(), true);
        }
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

    public String getPasswortRepeat() {
        return passwortRepeat;
    }

    public void setPasswortRepeat(String passwortRepeat) {
        this.passwortRepeat = new PasswortUtil().encryptWithMD5(passwortRepeat);
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
